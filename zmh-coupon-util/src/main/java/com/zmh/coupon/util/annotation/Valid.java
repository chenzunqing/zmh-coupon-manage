package com.zmh.coupon.util.annotation;

import java.lang.annotation.*;

/**
 * 
 * @Title: Valid
 * @Package com.zmh.demo.util.annotation
 * @Description: 自定义注解(验证使用)
 * @author wuque.hua
 * @date 2020年5月26日 上午10:58:41
 * @version V1.0
 *
 */
@Documented
@Target(ElementType.ANNOTATION_TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Valid {
    /** 是否必填 默认为false */
    boolean required() default false;

    /** 数值类型支持 最大值 */
    double maxValue() default Double.MAX_VALUE;

    /** 数值类型支持 最小值 */
    double minValue() default Double.MIN_VALUE;

    /** String类型支持 正则匹配 */
    String regex() default "";

    /** String类型支持 特定长度 */
    long length() default -1;

    /** String类型支持 最大长度 */
    long maxLength() default -1;

    /** String类型支持 最小长度 */
    long minLength() default -1;

    /** 验证参数名 */
    String name();
}
