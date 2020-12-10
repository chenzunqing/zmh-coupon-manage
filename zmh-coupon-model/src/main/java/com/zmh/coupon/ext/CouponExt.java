package com.zmh.coupon.ext;

import com.zmh.coupon.model.Coupon;
import com.zmh.coupon.model.CouponTemplate;
import lombok.Data;

/**
 * @author chenzunqing
 * @version V1.0
 * @title : CouponExt
 * @Description: 优惠卷扩展类
 * @date 2020/9/14 14:28
 **/
@Data
public class CouponExt extends Coupon {

    /**
     * 优惠卷对应的模板
     */
    private CouponTemplate couponTemplate;
}
