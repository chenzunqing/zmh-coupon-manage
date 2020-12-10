package com.zmh.coupon.util.constan;

/**
 * 优惠卷常量
 * @author 陈尊清
 * @create 2020-09-12-18:56
 */
public class RedisPrefix {

    /**
     * 优惠卷码 key 前缀
     */
    public static final String COUPON_TEMPLATE_CODE = "coupon:template:code:";

    /**
     *  用户当前所有【可用】的优惠券 key 前缀
     */
    public static final String COUPON_USER_USABLE = "coupon:user:usable:";

    /**
     *  用户当前所有【已使用】的优惠券 key 前缀
     */
    public static final String  COUPON_USER_USED = "coupon:user:used:";

    /**
     * 用户当前所有【已过期】的优惠券 key 前缀
     * */
    public static final String COUPON_USER_EXPIRED = "coupon:user:expired:";
}
