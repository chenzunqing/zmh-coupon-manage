package com.zmh.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zmh.coupon.dao.mapper.CouponTemplateMapper;
import com.zmh.coupon.model.CouponTemplate;
import com.zmh.coupon.service.AsyncService;
import com.zmh.coupon.service.CouponTemplateService;
import com.zmh.coupon.service.dto.CouponTemplateDTO;
import com.zmh.coupon.service.dto.TemplateRuleDTO;
import com.zmh.coupon.util.enums.ErrorMessageEnum;
import com.zmh.coupon.util.enums.coupon.CouponCategory;
import com.zmh.coupon.util.enums.coupon.DistributeTarget;
import com.zmh.coupon.util.enums.coupon.PeriodType;
import com.zmh.coupon.util.enums.coupon.ProductLine;
import com.zmh.coupon.util.exception.DataValidateException;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 陈尊清
 * @create 2020-09-12-21:51
 */
@Service
public class CouponTemplateServiceImpl extends ServiceImpl<CouponTemplateMapper, CouponTemplate> implements CouponTemplateService{

    @Autowired
    private CouponTemplateMapper couponTemplateMapper;
    @Autowired
    private  AsyncService asyncService;

    /**
     * 构建优惠卷模板
     * @param couponTemplateDTO
     * @throws DataValidateException
     */
    @Override
    public void buildCouponTemplate(CouponTemplateDTO couponTemplateDTO) throws DataValidateException {

        if(null == couponTemplateDTO.getName()){
            throw new DataValidateException("优惠卷名称不能为空！");
        }
        CouponTemplateDTO couponTemplateDTO1 = getTemplateRule(couponTemplateDTO);
        CouponTemplate couponTemplate = new CouponTemplate();
        BeanUtils.copyProperties(couponTemplateDTO1, couponTemplate);
        couponTemplateMapper.insert(couponTemplate);

        // 根据优惠券模板异步生成优惠券码
       asyncService.asyncConstructCouponByTemplate(couponTemplate);


    }

    /**
     * 根据ids查找说有的优惠卷模板
     * @param ids
     * @return
     */
    @Override
    public Map<Integer, CouponTemplate> findCouponTemplateByIds(Collection<Integer> ids) {
        List<CouponTemplate> templates = couponTemplateMapper.selectBatchIds(ids);
        return templates.stream().collect(Collectors.toMap( CouponTemplate::getId, Function.identity()));

    }

    // 获取模板规则
    private CouponTemplateDTO getTemplateRule(CouponTemplateDTO couponTemplateDTO){
        // 一、优惠卷类型  优惠展品线 分发目标
        couponTemplateDTO.setCategory(CouponCategory.MANJIAN.getCode());
        couponTemplateDTO.setProductLine(ProductLine.TMZ.getCode());
        couponTemplateDTO.setTarget(DistributeTarget.SINGLE.getCode());

        // 二、优惠卷规则
        TemplateRuleDTO rule = new TemplateRuleDTO();
        rule.setExpiration(new TemplateRuleDTO.Expiration(PeriodType.CHANGE.getCode(),1, DateUtils.addMinutes(new Date(), 60).getTime()));
        rule.setDiscount(new TemplateRuleDTO.Discount(5, 100));
        rule.setLimitation(2);
        rule.setUsage(new TemplateRuleDTO.Usage( "福建省", "厦门市",JSON.toJSONString(Arrays.asList("电脑", "鼠标"))));
        rule.setWeight(JSON.toJSONString(Collections.EMPTY_LIST));

        // 三、优惠卷模板的 为一标识
        couponTemplateDTO.setTemplateKey(UUID.randomUUID().toString().substring(1, 14));

        couponTemplateDTO.setRule(JSON.toJSONString(rule));
        return couponTemplateDTO;
    }
}
