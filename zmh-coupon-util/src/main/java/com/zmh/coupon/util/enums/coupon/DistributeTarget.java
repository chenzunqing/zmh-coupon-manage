package com.zmh.coupon.util.enums.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * 分发目标
 * @author 陈尊清
 * @create 2020-09-12-18:30
 */
@Getter
@AllArgsConstructor
public enum DistributeTarget {

    SINGLE("单用户", 1),
    MULTI("多用户", 2);

    /**
     * 分发目标人群
     */
    private String description;

    /**
     * 分发目标编码
     */
    private Integer code;

    public static DistributeTarget get(Integer code){
        Objects.requireNonNull(code);
        return Stream.of(values())
                .filter(model -> model.code.equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(code + " 不能为空！！"));
    }
}
