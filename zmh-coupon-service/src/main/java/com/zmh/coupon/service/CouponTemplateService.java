package com.zmh.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taomz.sha.util.response.BaseResponseModel;
import com.zmh.coupon.model.CouponTemplate;
import com.zmh.coupon.service.dto.CouponTemplateDTO;
import com.zmh.coupon.util.exception.DataValidateException;

import java.util.Collection;
import java.util.Map;

/**
 * @author 陈尊清
 * @create 2020-09-12-21:49
 */
public interface CouponTemplateService extends IService<CouponTemplate> {

    /**
     *  构建优惠卷模板
    * @param couponTemplateDTO
     * @return
     */
    void buildCouponTemplate(CouponTemplateDTO couponTemplateDTO) throws DataValidateException;


    /**
     * 根据ids查找说有的优惠卷模板
     * @param ids
     * @return
     */
    Map<Integer, CouponTemplate> findCouponTemplateByIds(Collection<Integer> ids);
}
