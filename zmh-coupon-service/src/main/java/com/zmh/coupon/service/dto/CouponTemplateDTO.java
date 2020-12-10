package com.zmh.coupon.service.dto;

import com.zmh.coupon.model.CouponTemplate;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author 陈尊清
 * @create 2020-09-12-22:06
 */
@Data
public class CouponTemplateDTO {

    /**
     * 优惠券模板主键
     */
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
     * 创建用户
     * */
    private Long userId;

    /**
     *  优惠券分类
     */
    private String category;

    /**
     *  优惠券模板的编码
     *  */
    private String templateKey;

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

    /**
     * 领取优惠卷数量
     */
    private Integer limitCount;

    /**
     * 优惠卷模板集合
     */
    private List<CouponTemplate>  couponTemplates;

}
