package com.zmh.coupon.util.enums.coupon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author chenzunqing
 * @version V1.0
 * @title : CouponStatus
 * @Description: 用户优惠卷的状态
 * @date 2020/9/14 13:46
 **/
@Getter
@AllArgsConstructor
public enum CouponStatus {
    USABLE("可用的", 1),
    USED("已使用的", 2),
    EXPIRED("过期的(未被使用的)", 3);

    /**
     * 优惠券状态描述信息
     */
    private String description;

    /**
     *  优惠券状态编码
     */
    private Integer code;

    public static CouponStatus get(Integer code){
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(model -> model.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " 不能为空！！"));
    }
}
