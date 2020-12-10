package com.zmh.coupon.service.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zmh.coupon.model.CouponTemplate;
import lombok.Data;

import java.util.Date;

/**
 * @author chenzunqing
 * @version V1.0
 * @title : CouponDTO
 * @Description: 优惠卷DTO
 * @date 2020/9/14 14:52
 **/
@Data
public class CouponDTO {

    /**
     * 主键
     */
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
    private Date assignTime;

    /**
     * 优惠券状态
     */
    private Integer status;
    /**
     * 优惠卷对应的模板
     */
    private CouponTemplate couponTemplate;

    /**
     * 优惠卷对应模板dto
     */
    private CouponTemplateDTO couponTemplateDTO;

}
