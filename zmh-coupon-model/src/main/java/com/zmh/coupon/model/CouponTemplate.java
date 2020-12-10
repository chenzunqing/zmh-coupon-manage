package com.zmh.coupon.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 陈尊清
 * @create 2020-09-12-19:58
 */
@Data
public class CouponTemplate implements Serializable{

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 优惠卷名称
     */
    private String name;

    /**
     * 优惠卷logo
     */
    private String logo;

    /**
     * 优惠卷描述
     */
    private String intro;

    /**
     * 总数
     * */
    private Integer couponCount;


    /**
     * 创建时间
     * */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建用户
     * */
    private Long userId;

    /**
     *  优惠券模板的编码
     *  */
    private String templateKey;

    /**
     * 是否可用
     */
    @TableField(fill = FieldFill.INSERT)
    private Boolean available;

    /**
     * 是否过期
     */
    @TableField(fill = FieldFill.INSERT)
    private Boolean expired;

    /**
     * 优惠券分类
     */
    private String category;

    /**
     * 产品线
     */
    private Integer productLine;

    /**
     * 目标用户
     */
    private Integer target;

    /**
     * 优惠券规则
     */
    private String rule;

}

















