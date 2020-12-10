package com.zmh.coupon.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 处理POST请求中的JSON</br>
 * 默认处理方法中的所有参数
 * 
 * @author 2020年5月27日下午1:33:15
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {

    /**
     * 处理方式选择</br>
     * 默认 false 例外参数处理,对于 params 中的参数名称，不进行处理</br>
     * true 指定参数处理，仅对 params 中包含的参数名称进行处理 </br>
     * JiangLongKun
     * 
     * @return 2020年5月27日下午1:30:42
     */
    boolean option() default false;

    /**
     * 处理的参数名称 </br>
     * JiangLongKun
     * 
     * @return 2020年5月27日下午1:33:53
     */
    String[] params() default {};
}
