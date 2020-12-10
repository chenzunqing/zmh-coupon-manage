package com.zmh.coupon.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author chenzunqing
 * @version V1.0
 * @title : Coupon
 * @Description: 优惠卷（用户领取记录）实体类
 * @date 2020/9/14 11:25
 **/
@Data
public class Coupon {
    /**
     * 自增主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 与优惠卷模板id关联
     * */
    private Integer templateId;

    /**
     * 领取用户Id
     */
    private Long userId;

    /**
     * 优惠券码
     */
    private String couponCode;

    /**
     * 领取时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date assignTime;

    /**
     * 优惠券状态
     */
    private Integer status;


}
