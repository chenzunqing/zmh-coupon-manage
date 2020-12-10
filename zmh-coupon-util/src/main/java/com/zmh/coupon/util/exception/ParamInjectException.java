package com.zmh.coupon.util.exception;

public class ParamInjectException extends Exception {
    private static final long serialVersionUID = -2073655108403204819L;

    public ParamInjectException(Throwable throwable, String argument) {
        super(String.format("parameter inject failed: '%s' ,input format error or other reason", argument), throwable);
    }
}
