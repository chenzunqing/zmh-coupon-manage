package com.zmh.coupon.manage.web.controller.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.taomz.sha.util.exception.ExceptionWrapper;
import com.taomz.sha.util.validation.BeanValidateInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 
 * @Title: BaseController
 * @Package com.zmh.demo.web.controller.base
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author wuque.hua
 * @date 2020年5月24日 下午3:52:19
 * @version V1.0
 *
 */
public class BaseController {

    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 获取request对象
     * 
     * @return
     */
    public HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest();
    }

    /**
     * 默认序列化输出规则
     */
    protected SerializerFeature[] DEFAULT_FEATURES = new SerializerFeature[] { SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteNullBooleanAsFalse, SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteDateUseDateFormat };

    /**
     * 参数合法性校验
     * 
     * @param str
     *            json格式待校验字符串
     * @return boolean 是否合法
     */
    public boolean legalCheck(String str) {
        return (str != null && !StringUtils.isEmpty(str.trim()) && !"{}".equals(str.trim()));
    }

    /**
     * 参数合法性校验
     * 
     * @param str
     *            json格式待校验字符串
     * @param ignore
     *            是否忽略 {} 字符串
     * @return boolean 是否合法
     */
    public boolean legalCheck(String str, boolean ignore) {
        if (ignore) {
            return (str != null && !StringUtils.isEmpty(str.trim()));
        } else {
            return (str != null && !StringUtils.isEmpty(str.trim()) && !"{}".equals(str.trim()));
        }
    }

    /**
     * 对象属性校验
     */
    protected Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public <T> BeanValidateInfo<T> beanValidate(T object, Class<?>... groups) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, groups);
        if (constraintViolations.isEmpty()) {
            return new BeanValidateInfo<T>(object);
        } else {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                sb.append(constraintViolation.getMessage()).append("<br>");
            }
            return new BeanValidateInfo<T>().setValid(false).setInvalidMsg(sb.toString());
        }
    }

    public <T> BeanValidateInfo<T> validate(String source, Class<T> clazz) throws ExceptionWrapper {
        try {
            if (legalCheck(source)) {
                T bean = JSON.parseObject(source, clazz);
                return beanValidate(bean);
            } else {
                return new BeanValidateInfo<T>(false, "invalid parameter: null or empty");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ExceptionWrapper("json parse exception", e);
        }
    }

    public <T> BeanValidateInfo<T> validate(String source, boolean ignore, Class<T> clazz) throws ExceptionWrapper {
        try {
            if (legalCheck(source, ignore)) {
                T bean = JSON.parseObject(source, clazz);
                return beanValidate(bean);
            } else {
                return new BeanValidateInfo<T>(false, "invalid parameter: null or empty");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ExceptionWrapper("json parse exception", e);
        }
    }

}
