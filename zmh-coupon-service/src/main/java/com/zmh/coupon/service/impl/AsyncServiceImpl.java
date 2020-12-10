package com.zmh.coupon.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zmh.coupon.dao.mapper.CouponTemplateMapper;
import com.zmh.coupon.model.CouponTemplate;
import com.zmh.coupon.service.AsyncService;
import com.zmh.coupon.util.constan.RedisPrefix;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * @author 陈尊清
 * @create 2020-09-13-12:17
 */
@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService{


    @Autowired
    private CouponTemplateMapper couponTemplateMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Async("getAsyncExecutor")
    @Override
    public void asyncConstructCouponByTemplate(CouponTemplate couponTemplate) {

        //创建优惠卷模板编码
        Set<String> stringSet = buildCouponCode(couponTemplate.getCouponCount());
        String key = String.format("%s%s", RedisPrefix.COUPON_TEMPLATE_CODE, couponTemplate.getId().toString());
        //保存到redis
        redisTemplate.opsForList().rightPushAll(key,stringSet);

        //修改数据库优惠卷模板状态
        couponTemplate.setAvailable(true);
        couponTemplateMapper.update(couponTemplate, Wrappers.<CouponTemplate>lambdaQuery().eq(CouponTemplate::getName, couponTemplate.getName()));
    }

    //创建优惠卷模板编码
    private Set<String> buildCouponCode(Integer count){

        if(null != count){
            Set<String> result = new HashSet<>(count);

            for (int i = 0 ; i != count;i++){
                result.add(UUID.randomUUID().toString().substring(0, 18));
            }
            while (result.size() < count){
                result.add(UUID.randomUUID().toString().substring(0, 18));
            }
            return result;
        }
        return null;
    }
}