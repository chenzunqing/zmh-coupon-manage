package com.zmh.coupon.util.enums.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 有效时间类型
 * @author 陈尊清
 * @create 2020-09-12-18:39
 */
@Getter
@AllArgsConstructor
public enum PeriodType {

    REGUAL("固定日期(固定过期时间)",1),
    CHANGE("变动日期(领取当日开始计时)",2);

    /**
     * 有效期类型描述
     */
    private String description;

    /**
     * 有效期编码
     */
    private Integer code;

    public static PeriodType get(Integer code){
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(model -> model.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " 不能为空！！"));
    }
}

