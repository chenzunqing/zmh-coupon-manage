package com.zmh.coupon.util.enums.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 产品线
 * @author 陈尊清
 * @create 2020-09-12-18:51
 */
@Getter
@AllArgsConstructor
public enum ProductLine {

    ZMH("妆美汇", 1),
    TMZ("淘美妆", 2);

    /**
     * 产品线描述
     */
    private String description;

    /**
     * 产品线编码
     */
    private Integer code;

    public static ProductLine get(Integer code) {
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(model -> model.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " 不能为空！！"));
    }
}
