package com.zmh.coupon.service.dto;

import com.alibaba.fastjson.JSON;
import com.zmh.coupon.ext.CouponExt;
import com.zmh.coupon.model.Coupon;
import com.zmh.coupon.util.enums.coupon.CouponStatus;
import com.zmh.coupon.util.enums.coupon.PeriodType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenzunqing
 * @version V1.0
 * @title : CouponRule
 * @Description: 用户优惠卷分类，根据优惠卷状态
 * @date 2020/9/14 13:56
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponRuleDTO {

    /** 可以使用的 */
    private List<CouponExt> usable;

    /** 已使用的 */
    private List<CouponExt> used;

    /** 已过期的 */
    private List<CouponExt> expired;



    /**
     * 对当前优惠卷分类
     * @param coupons
     * @return
     */
    public static CouponRuleDTO classify(List<CouponExt> coupons) {

        List<CouponExt> usable = new ArrayList<>(coupons.size());
        List<CouponExt> used = new ArrayList<>(coupons.size());
        List<CouponExt> expired = new ArrayList<>(coupons.size());

        coupons.forEach(coupon -> {
            boolean isTimeExpire;
            long curTime = new Date().getTime();
            if (JSON.parseObject(coupon.getCouponTemplate().getRule(),TemplateRuleDTO.class).getExpiration().getPeriod().equals(PeriodType.REGUAL.getCode())) {
                isTimeExpire = JSON.parseObject(coupon.getCouponTemplate().getRule(),TemplateRuleDTO.class).getExpiration().getDeadline() <= curTime;
            } else {
                isTimeExpire = DateUtils.addDays(coupon.getAssignTime(), JSON.parseObject(coupon.getCouponTemplate().getRule(),TemplateRuleDTO.class).getExpiration().getGap()).getTime() <= curTime;
            }
            if (coupon.getStatus() == CouponStatus.USED.getCode()) {
                used.add(coupon);
            } else if (coupon.getStatus() == CouponStatus.EXPIRED.getCode() || isTimeExpire) {
                expired.add(coupon);
            } else {
                usable.add(coupon);
            }
        });

        return new CouponRuleDTO(usable, used, expired);
    }
}
