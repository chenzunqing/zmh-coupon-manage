package com.zmh.coupon.util;



import com.zmh.coupon.util.enums.ApiCallResult;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Title: Response.java
 * @Package com.zmh.demo.util
 * @Description: TODO(API返回的数据格式模版)
 * @author wuque.hua
 * @date 2020年5月11日 下午5:33:56
 * @version V1.0
 */
public class ResponseModel implements Serializable {

    private static final long serialVersionUID = -2177650628109118809L;
    private String accessToken = "XXXXXXXX";// 权限Token
    private String code; // 返回结果码
    private String desc; // 返回结果描述
    private String sign = "XXXXXXXX"; // 签名
    private Object content; // 返回数据
    private long timestamp; // 调用时间戳

    public ResponseModel() {
        super();
    }

    public ResponseModel(Object content) {
        super();
        this.content = content;
        this.timestamp = new Date().getTime();
    }

    // 增加一个differ参数区分 Response(String code, String desc)构造方法
    public ResponseModel(String key, Object value, int differ) {
        super();
        Map<String, Object> content = new HashMap<String, Object>();
        content.put(key, value);
        this.content = content;
        this.timestamp = new Date().getTime();
    }

    public ResponseModel(String code, String desc) {
        super();
        this.code = code;
        this.desc = desc;
        this.timestamp = new Date().getTime();
    }

    public ResponseModel(String code, String desc, Object content) {
        super();
        this.code = code;
        this.desc = desc;
        this.content = content;
        this.timestamp = new Date().getTime();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public ResponseModel setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ResponseModel setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ResponseModel setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public String getSign() {
        return sign;
    }

    public ResponseModel setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public Object getContent() {
        return content;
    }

    public ResponseModel setContent(Object content) {
        this.content = content;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public ResponseModel setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ResponseModel warpSuccess() {
        return this.setCode(ApiCallResult.SUCCESS.getCode()).setDesc(ApiCallResult.SUCCESS.getDesc());
    }

}
