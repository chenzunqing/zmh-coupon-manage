package com.zmh.coupon.service;

import com.zmh.coupon.model.CouponTemplate;


/**
 * @author 陈尊清
 * @create 2020-09-13-0:32
 */
public interface AsyncService {

    /**
     * 异步在reids存储生成的优惠卷码
     * @param template
     */
    void asyncConstructCouponByTemplate(CouponTemplate template);
}
