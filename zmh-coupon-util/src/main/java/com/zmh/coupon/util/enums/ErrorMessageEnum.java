package com.zmh.coupon.util.enums;

/**
 * 
 * @ClassName: ErrorMessageEnum
 * @Description: 错误信息枚举类
 * @author wangling
 * @data 2020-08-14 09:21
 *
 */
public enum ErrorMessageEnum {
    SUCCESS("0", "请求成功"), FAILURE("999", "请求失败"), LOGIN_FAILURE("006", "登录失效"), PARAM_ERROR("99999", "错误"),
    USER_REGISTER_FAILED("10003", "用户新增失败"),USER_NOT_EMPTY("10003", "用户不存在"),
    WXLOGIN_ERRCODE_1("-1", "系统繁忙，此时请开发者稍候再试"), WXLOGIN_ERRCODE_SUCCESS("0", "请求成功"),
    WXLOGIN_ERRCODE_40029("40029", "code 无效"), WXLOGIN_ERRCODE_45011("45011", "操作频繁，请稍后再试"),
    USER_PHONE_REGISTERED("10001", "该手机号已注册"), WX_LOGIN_FAILED("10002", "微信认证失败"), CODE_NULL("10003", "code不能为空"),
    OPENID_NULL("10004", "openId获取失败"), OPENID_HAVEND("10005", "openId已校验，不能重复新增"),
    PARAMETER_NULL("10006", "参数为空"), ALIPAY_LOGIN_FAILED("10010", "获取access_token - 调用失败"),
	MENU_NAME_NULL("10003", "菜单名不能为空"),MENU_NAME_TYPE_NULL("10003", "菜单类型不能为空"),
    USERBASEIN_ALIPAY_FAILED("10011", "获取用户信息失败"), ALIPAY_GETUSERID_FAILED("10012", "调用支付宝授权接口失败"),
	IS_PARENT_MENU("10013", "存在子菜单、目录或按钮"),ROLE_EXIST("10014", "角色已存在，不可重复！"),
    ROLE_NOT_EXIST("10015", "角色不存在！"), ROLE_TO_USER("10016","角色存在相关用户请先删除相关角色的用户"),
    SUPER_ADMINISTRATOR_DELETE("10017","不能删除管理员信息"),
    MENU_NOT_DELETE("10019","角色删除失败"),ROLE_TO_USER_ADD_FAILED("10020", "用户配置角色失败！"), NO_USER("401", "无法获取登录用户信息"),
	MENU_EXIST("10030","菜单已存在"),MENU_EMPTY("10030","菜单不存在"),USER_ROLE_EMPTY("10040","用户角色表id不存在") ;

    private String desc;
    private String code;

    ErrorMessageEnum(String code, String desc) {
	this.desc = desc;
	this.code = code;
    }

    public String getDesc() {
	return desc;
    }

    public String getCode() {
	return code;
    }

    public static ErrorMessageEnum getEnum(String code) {
        for (ErrorMessageEnum em : values()) {
	    if (em.getCode() == code) {
                return em;
            }
        }
	return PARAM_ERROR;
    }

    public String getResult() {
	return "结果码：" + this.code + "，结果描述：" + this.desc;
    }
}
