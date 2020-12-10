package com.zmh.coupon.util.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.taomz.sha.util.exception.ExceptionWrapper;
import com.taomz.sha.util.exception.IllegalParamException;
import com.taomz.sha.util.exception.ParamAbsentException;
import com.taomz.sha.util.response.BaseResponseModel;

import com.zmh.coupon.util.enums.ApiCallResult;
import com.zmh.coupon.util.exception.DataValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLException;

/**
 * @ClassName: GlobalExceptionHandler
 * @Description: 捕获全局异常信息
 * @author huawuque@taomz.com
 * @date 2020-06-17 11:24
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public BaseResponseModel defaultErrorHandler(Exception e) {
        LOGGER.error("", e);
        BaseResponseModel response = new BaseResponseModel(ApiCallResult.EXCEPTION.getCode(),
                ApiCallResult.EXCEPTION.getDesc());
        return response;
    }

    @ExceptionHandler(value = DataValidateException.class)
    public BaseResponseModel dataValidateException(DataValidateException e) {
	LOGGER.error("", e.getDesc());
	BaseResponseModel response = new BaseResponseModel(e.getErrorCode(),
		e.getDesc());
	return response;
    }

    @ExceptionHandler(value = SQLException.class)
    public BaseResponseModel sqlErrorHandler(SQLException e) {
        LOGGER.error("", e);
        BaseResponseModel response = new BaseResponseModel(ApiCallResult.DBERROR.getCode(),
                ApiCallResult.DBERROR.getDesc());
        return response;
    }

    @ExceptionHandler(value = RestClientException.class)
    public BaseResponseModel restClientErrorHandler(RestClientException e) {
        LOGGER.error("", e);
        BaseResponseModel response = new BaseResponseModel(ApiCallResult.RESTCLIENTERROR.getCode(),
                ApiCallResult.RESTCLIENTERROR.getDesc());
        return response;
    }

    @ExceptionHandler(value = ExceptionWrapper.class)
    public BaseResponseModel customErrorHandler(ExceptionWrapper e) {
        BaseResponseModel response = new BaseResponseModel(e.getCode(), e.getCustomErrMsg());
        return response;
    }

    @ExceptionHandler(value = { IllegalParamException.class, ParamAbsentException.class,
            MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class })
    public BaseResponseModel paramValidErrorHandler(Exception e) {
        BaseResponseModel response = new BaseResponseModel();
        if (e instanceof ParamAbsentException || e instanceof MissingServletRequestParameterException) {
            if (e instanceof ParamAbsentException) {
                e = (ParamAbsentException) e;
                response.setCode(ApiCallResult.EMPTY.getCode()).setDesc(String.format("%s : %s",
                        ApiCallResult.EMPTY.getDesc(), ((ParamAbsentException) e).getFaildField()));
            } else {
                response.setCode(ApiCallResult.EMPTY.getCode()).setDesc(ApiCallResult.EMPTY.getDesc());
            }
        } else if (e instanceof HttpMessageNotReadableException) {
            if (e.getCause() instanceof JsonProcessingException) {
                response.setCode(ApiCallResult.UNSUPPORTED.getCode()).setDesc(
                        "json parameter parse failed,please check your json format:  " + e.getCause().getMessage());
            } else {
                response.setCode(ApiCallResult.EXCEPTION.getCode()).setDesc(ApiCallResult.EXCEPTION.getDesc());
                LOGGER.error("", e);
                return response;
            }
        } else {
            response.setCode(ApiCallResult.UNSUPPORTED.getCode()).setDesc(e.getMessage());
        }
        LOGGER.info(response.getDesc());
        return response;
    }

    @ExceptionHandler(value = org.springframework.validation.BindException.class)
    public BaseResponseModel validationBindExcepitonHandler(org.springframework.validation.BindException e) {
        FieldError fe = e.getFieldError();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(fe.getDefaultMessage(), e);
        }
        return new BaseResponseModel().setCode(ApiCallResult.ILLEGAL_ARGUMENT.getCode())
                .setDesc(fe.getDefaultMessage());
    }

    @ExceptionHandler(value = org.springframework.web.bind.MethodArgumentNotValidException.class)
    public BaseResponseModel methodArgumentNotValidException(
            org.springframework.web.bind.MethodArgumentNotValidException e) {
        FieldError fe = e.getBindingResult().getFieldError();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(fe.getDefaultMessage(), e);
        }
        return new BaseResponseModel().setCode(ApiCallResult.ILLEGAL_ARGUMENT.getCode())
                .setDesc(fe.getDefaultMessage());
    }

}
