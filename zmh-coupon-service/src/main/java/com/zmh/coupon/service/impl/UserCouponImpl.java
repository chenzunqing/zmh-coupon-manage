package com.zmh.coupon.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zmh.coupon.dao.mapper.CouponMapper;
import com.zmh.coupon.dao.mapper.CouponTemplateMapper;
import com.zmh.coupon.model.Coupon;
import com.zmh.coupon.model.CouponTemplate;
import com.zmh.coupon.service.CouponTemplateService;
import com.zmh.coupon.service.RedisCouponService;
import com.zmh.coupon.service.UserCouponService;
import com.zmh.coupon.service.config.ActiveMQUtil;
import com.zmh.coupon.service.dto.CouponDTO;
import com.zmh.coupon.service.dto.CouponTemplateDTO;
import com.zmh.coupon.service.dto.TemplateRuleDTO;
import com.zmh.coupon.util.StringUtil;
import com.zmh.coupon.util.enums.coupon.CouponStatus;
import com.zmh.coupon.util.exception.DataValidateException;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.util.JSONUtils;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.jms.Queue;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author chenzunqing
 * @version V1.0
 * @title : UserServiceImpl
 * @Description: 用户服务服务接口实现
 * @date 2020/9/14 11:22
 **/
@Slf4j
@Service
public class UserCouponImpl implements UserCouponService {
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private RedisCouponService redisCouponService;
    @Autowired
    private CouponTemplateService couponTemplateService;
    @Autowired
    private ActiveMQUtil activeMQUtil;


    /**
     * 根据用户id 和 状态查询优惠卷记录
     * @param userId
     * @param status
     * @return
     * @throws DataValidateException
     */
    @Override
    public List<CouponDTO> selectCouponByIdAndStatus(Long userId, Integer status) throws DataValidateException {
        // redis 获取
        List<CouponDTO> curRedis = redisCouponService.getRedisCoupons(userId,status);
        List<CouponDTO> preTarget ;

        if (CollectionUtils.isNotEmpty(curRedis)) {
            preTarget = curRedis;
        } else {
            //redis查询不到数据，数据库查找
            List<Coupon> dbCoupons = couponMapper.selectList(Wrappers.<Coupon>lambdaQuery()
                    .eq(Coupon::getUserId,userId)
                    .eq(Coupon::getStatus,CouponStatus.get(status)));
            List<CouponDTO> couponDTOS = dbCoupons.stream()
                    .map(coupon -> JSONUtil.toBean(JSONUtil.toJsonStr(coupon), CouponDTO.class))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(dbCoupons)) {
                return couponDTOS;
            }
            // 获取优惠卷对应的模板ids
            List<Integer> TemplateIds = dbCoupons.stream().map(Coupon::getTemplateId).collect(Collectors.toList());
            // 通过模板ids批量查询优惠卷模板
            Map<Integer,CouponTemplate> couponTemplate = couponTemplateService.findCouponTemplateByIds(TemplateIds);

            couponDTOS.forEach(couponDTO -> couponDTO.setCouponTemplate(couponTemplate.get(couponDTO.getId())));
            preTarget = couponDTOS;
            // 将记录写入 redis
            redisCouponService.addCouponToRedis(userId, preTarget, status);

        }
        return preTarget;
    }

    /**
     * 根据用户id 查找当前可以领取的优惠卷模板
     * .1. 从 数据库 拿到对应的优惠券, 并检查是否过期
     *  2. 根据 最多可以领取数 判断用户是否可以领取
     *  3. 保存到数据库
     *  4. 转换成dto,设置
     *  5. save to cache
     * @return
     * @throws DataValidateException
     */
    @Override
    public List<CouponTemplateDTO> findAvailableCouponTemplate(Long userId) throws DataValidateException {
        long curTime = new Date().getTime();
        // 数据库查找可用和未过期的优惠卷模板
        List<CouponTemplate> couponTemplates =couponTemplateService.list(Wrappers.<CouponTemplate>lambdaQuery()
                        .eq(CouponTemplate::getAvailable,true ).eq(CouponTemplate::getExpired,false ));

        // 过滤过期的优惠券模板
        couponTemplates = couponTemplates.stream()
                .filter(couponTemplate -> JSON.parseObject(couponTemplate.getRule(), TemplateRuleDTO.class).getExpiration().getDeadline() > curTime)
                .collect(Collectors.toList());
        // 查找每个模板每个人可以领取多少张优惠卷
        // key 是 模板id
        //value 是 map<领取优惠卷数，优惠卷模板>
        Map<Integer, Pair<Integer, CouponTemplate>> limit2Template = new HashMap<>(couponTemplates.size());
        couponTemplates.forEach(couponTemplate -> limit2Template.put(couponTemplate.getId(),
                Pair.of(JSON.parseObject(couponTemplate.getRule(), TemplateRuleDTO.class).getLimitation(), couponTemplate)));

        log.info("limit2Template={}",limit2Template);

        List<CouponTemplate> result =new ArrayList<>(limit2Template.size());
        List<CouponDTO> userUsableCoupons = selectCouponByIdAndStatus(userId, CouponStatus.USABLE.getCode());

        log.info("userUsableCoupons={}",userUsableCoupons);

        // key 是 模板Id
        Map<Integer, List<CouponDTO>> templateId2Coupons = userUsableCoupons
                .stream()
                .filter(item-> item.getTemplateId() != null)
                .collect(Collectors.groupingBy(CouponDTO::getTemplateId));

        // 根据 Template 的 Rule 判断是否可以领取优惠券模板
        limit2Template.forEach((key, value) -> {

            int limitation = value.getLeft();
            CouponTemplate template = value.getRight();
            if (templateId2Coupons.containsKey(key) && templateId2Coupons.get(key).size() >= limitation) {
                return;
            }
            result.add(template);
        });

        return result.stream()
                .map(couponTemplate ->
                        JSONUtil.toBean(JSONUtil.toJsonStr(couponTemplate),CouponTemplateDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * 用户领取优惠卷
     * @param couponTemplateDTO
     * @return
     */
    @Override
    public String getCoupon(CouponTemplateDTO couponTemplateDTO) throws DataValidateException{
        // 1.查询优惠卷模板
        log.info("优惠卷模板id={}",couponTemplateDTO.getId());
        CouponTemplate template = couponTemplateService.getById(couponTemplateDTO.getId());
        // 优惠券模板是需要存在的
        log.info("优惠卷模板={}",template);
        if(template == null){
            throw new DataValidateException("查询优惠卷模板不存在！");
        }

        // 用户是否可以领取这张优惠券
        List<CouponDTO> userUsableCoupons = selectCouponByIdAndStatus(couponTemplateDTO.getUserId(), CouponStatus.USABLE.getCode());
        Map<Integer, List<CouponDTO>> templateId2Coupons = userUsableCoupons.stream()
                .filter(item-> item.getTemplateId() != null)
                .collect(Collectors.groupingBy(CouponDTO::getTemplateId));
        log.info("templateId2Coupons={}", templateId2Coupons);
        if (templateId2Coupons.containsKey(couponTemplateDTO.getId()) &&
                templateId2Coupons.get(couponTemplateDTO.getId()).size() >= couponTemplateDTO.getLimitCount()) {
            throw new DataValidateException("不能再领取这张优惠卷！");
        }

        // 尝试去获取优惠券码
        String couponCode = redisCouponService.getRedisCouponCode(couponTemplateDTO.getId());
        if (StringUtils.isEmpty(couponCode)) {
            throw new DataValidateException("不能获取优惠卷码！");
        }
        sendCouponCode(couponCode,couponTemplateDTO);
        // 保存领取的优惠卷到数据库
       /* Coupon coupon = new Coupon();
        coupon.setTemplateId(couponTemplateDTO.getId());
        coupon.setUserId(couponTemplateDTO.getUserId());
        coupon.setCouponCode(couponCode);
        coupon.setStatus(CouponStatus.USABLE.getCode());
        couponMapper.insert(coupon);

        // 填充 Coupon 对象的 传入的dto
        CouponDTO couponDTO = new CouponDTO();
        BeanUtils.copyProperties(coupon,couponDTO);
        couponDTO.setCouponTemplateDTO(couponTemplateDTO);
        // 放入缓存中
        redisCouponService.addCouponToRedis( couponTemplateDTO.getUserId(),
                Collections.singletonList(couponDTO),
                CouponStatus.USABLE.getCode()
        );*/

        return "领取成功！";
    }


    public void sendCouponCode(String couponCode,CouponTemplateDTO couponTemplateDTO) {

        // 创建消息的工厂
        Connection connection = activeMQUtil.getConnection();
        try {
            connection.start();
            Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
            // 创建队列
            Queue coupon_kill_queue = session.createQueue("COUPON_KILL");
            // 创建消息提供者
            MessageProducer producer = session.createProducer(coupon_kill_queue);

            // 创建消息对象
            ActiveMQMapMessage activeMQMapMessage = new ActiveMQMapMessage();
            activeMQMapMessage.setString("couponCode", couponCode);
            activeMQMapMessage.setString("couponTemplateDTO", JSON.toJSONString(couponTemplateDTO));

            producer.send(activeMQMapMessage);
            // 提交
            session.commit();

            producer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
