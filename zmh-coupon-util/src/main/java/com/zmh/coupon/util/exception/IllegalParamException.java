package com.zmh.coupon.util.exception;

/**
 * 
 * @Title: IllegalParamException
 * @Package com.zmh.demo.util.exception
 * @Description: 自定义异常(参数格式错误)
 * @author wuque.hua
 * @date 2020年5月26日 上午10:43:37
 * @version V1.0
 *
 */
public class IllegalParamException extends RuntimeException {

    private static final long serialVersionUID = 6659275682706729478L;

    public IllegalParamException(String msg) {
        super("illegal param: " + msg);
    }
}
