package com.zmh.coupon.util.exception;


import com.zmh.coupon.util.enums.ErrorMessageEnum;

/**
 * 
 * @ClassName: DataValidateException
 * @Description: 校验信息
 * @author wangling
 * @data 2020-08-14 09:28
 *
 */
public class DataValidateException extends Exception {

    private static final long serialVersionUID = 1741078348564045051L;
    private  String desc;
    //默认1000
    private String errorCode;

    public DataValidateException() {
	this.desc = "";
    }

    public DataValidateException(String desc) {
	this.desc = desc;
    }


    public DataValidateException(String desc, String errorCode) {
	this.desc = desc;
        this.errorCode = errorCode;
    }

    public DataValidateException(ErrorMessageEnum errorMessageEnum) {
	this.desc = errorMessageEnum.getDesc();
	this.errorCode = errorMessageEnum.getCode();
    }


    public DataValidateException(ErrorMessageEnum errorMessageEnum, String appendMessage) {
	this.desc = appendMessage + errorMessageEnum.getDesc();
	this.errorCode = errorMessageEnum.getCode();
    }

    public DataValidateException(ErrorMessageEnum errorMessageEnum, String... messageParam) {
        this.desc = String.format(errorMessageEnum.getDesc(), messageParam);
	this.errorCode = errorMessageEnum.getCode();
    }


    public DataValidateException(String desc, Throwable t) {
	super(desc, t);
	this.desc = desc;
    }


    public String getDesc() {
        return desc;
    }


    public String getErrorCode() {
        return errorCode;
    }
}
