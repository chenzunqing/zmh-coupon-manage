package com.zmh.coupon.util;

import java.util.List;
import java.util.Set;

/**
 * 
 * @Title: ClassUtil
 * @Package com.zmh.demo.util
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author
 * @date 2020年5月26日 上午10:42:53
 * @version V1.0
 *
 */
public class ClassUtil {
    public static String getter(String field) {
        String first = field.substring(0, 1);
        String replaceFirst = field.replaceFirst(first, first.toUpperCase());
        return "get" + replaceFirst;
    }

    public static String setter(String field) {
        String first = field.substring(0, 1);
        String replaceFirst = field.replaceFirst(first, first.toUpperCase());
        return "set" + replaceFirst;
    }

    public static boolean isPrimitiveNumber(Class<?> parameterType) {
        if (Number.class.isAssignableFrom(parameterType) || parameterType == Long.TYPE || parameterType == Double.TYPE
                || parameterType == Integer.TYPE || parameterType == Float.TYPE || parameterType == Short.TYPE) {

            return true;
        }
        return false;
    }

    public static boolean isPrimitive(Class<?> type) {
        if (String.class == type || Number.class.isAssignableFrom(type) || type.isPrimitive()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSupportArray(Class<?> type) {
        if (type.isArray() || List.class.isAssignableFrom(type) || Set.class.isAssignableFrom(type)) {
            return true;
        } else {
            return false;
        }
    }
}
