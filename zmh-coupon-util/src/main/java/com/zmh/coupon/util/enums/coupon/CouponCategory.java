package com.zmh.coupon.util.enums.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 优惠卷类型
 * @author 陈尊清
 * @create 2020-09-12-19:04
 */
@Getter
@AllArgsConstructor
public enum CouponCategory {

    MANJIAN("满减券", "001"),
    ZHEKOU("折扣券", "002"),
    LIJIAN("立减券", "003");

    /**
     * 优惠券描述(分类)
     */
    private String description;

    /**
     * 优惠券分类编码
     */
    private String code;

    public static CouponCategory get(String code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(model -> model.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " 不能为空！！"));
    }
}
