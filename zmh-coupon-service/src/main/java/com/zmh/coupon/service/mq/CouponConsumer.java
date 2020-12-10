package com.zmh.coupon.service.mq;

import com.alibaba.fastjson.JSON;
import com.zmh.coupon.dao.mapper.CouponMapper;
import com.zmh.coupon.model.Coupon;
import com.zmh.coupon.service.RedisCouponService;
import com.zmh.coupon.service.dto.CouponDTO;
import com.zmh.coupon.service.dto.CouponTemplateDTO;
import com.zmh.coupon.util.enums.coupon.CouponStatus;
import com.zmh.coupon.util.exception.DataValidateException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import java.util.Collections;

/**
 * @author 陈尊清
 * @create 2020-09-17-22:29
 */
@Component
public class CouponConsumer {
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private RedisCouponService redisCouponService;

    /**
     * destination 表示监听的队列名称
     */
    @JmsListener(destination = "COUPON_KILL",containerFactory = "jmsQueueListener")
    public void consumerPaymentResult(MapMessage mapMessage) throws JMSException,DataValidateException{
        String couponCode = mapMessage.getString("couponCode");
        CouponTemplateDTO couponTemplateDTO = JSON.parseObject(mapMessage.getString("couponTemplateDTO"), CouponTemplateDTO.class);
        // 通过mapMessage获取
        Coupon coupon = new Coupon();
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
                CouponStatus.USABLE.getCode());
    }
}
