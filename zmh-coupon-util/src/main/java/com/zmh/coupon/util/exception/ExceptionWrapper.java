package com.zmh.coupon.util.exception;


import com.zmh.coupon.util.enums.ApiCallResult;

/**
 * @Title: ExceptionWrapper.java
 * @Package com.taomz.code.util.exception
 * @Description: TODO(类的描述)
 * @author wuque.hua
 * @date 2020年5月11日 下午5:40:27
 * @version V1.0
 */
public class ExceptionWrapper extends Exception {

    private static final long serialVersionUID = -3954547921665737839L;
    private Exception exception; // 异常对象
    private String originalErrMsg; // 真实异常信息
    private String customErrMsg; // 自定义异常信息
    private String code = ApiCallResult.FAILURE.getCode(); // 异常编码

    public ExceptionWrapper() {
        super();
    }

    public ExceptionWrapper(String customErrMsg) {
        super();
        this.customErrMsg = customErrMsg;
    }

    public ExceptionWrapper(String code, String customErrMsg) {
        super();
        this.customErrMsg = customErrMsg;
        this.code = code;
    }

    public ExceptionWrapper(String customErrMsg, Exception exception) {
        super();
        this.originalErrMsg = exception.getMessage();
        this.customErrMsg = customErrMsg;
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    public ExceptionWrapper setException(Exception exception) {
        this.exception = exception;
        return this;
    }

    public String getOriginalErrMsg() {
        return originalErrMsg;
    }

    public ExceptionWrapper setOriginalErrMsg(String originalErrMsg) {
        this.originalErrMsg = originalErrMsg;
        return this;
    }

    public String getCustomErrMsg() {
        return customErrMsg;
    }

    public ExceptionWrapper setCustomErrMsg(String customErrMsg) {
        this.customErrMsg = customErrMsg;
        return this;
    }

    @Override
    public String getMessage() {
        return getOriginalErrMsg();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
