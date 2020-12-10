package com.zmh.coupon.ext;

import com.zmh.coupon.model.CouponTemplate;
import com.zmh.coupon.util.enums.coupon.CouponCategory;
import com.zmh.coupon.util.enums.coupon.DistributeTarget;
import com.zmh.coupon.util.enums.coupon.ProductLine;
import lombok.Data;

/**
 * @author 陈尊清
 * @create 2020-09-12-20:38
 */
@Data
public class CouponTemplateExt extends CouponTemplate{

    /**
     * 优惠券分类
     */
    private CouponCategory categoryObj;

    /**
     * 产品线
     */
    private ProductLine productLineObj;

    /**
     * 目标用户
     */
    private DistributeTarget targetObj;

}
