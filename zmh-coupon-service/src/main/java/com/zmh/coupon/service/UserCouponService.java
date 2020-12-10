package com.zmh.coupon.service;

import com.zmh.coupon.model.Coupon;
import com.zmh.coupon.model.CouponTemplate;
import com.zmh.coupon.service.dto.CouponDTO;
import com.zmh.coupon.service.dto.CouponTemplateDTO;
import com.zmh.coupon.util.exception.DataValidateException;

import java.util.List;

/**
 * @author chenzunqing
 * @version V1.0
 * @title : UserService
 * @Description: 用户服务服务接口
 * @date 2020/9/14 11:21
 **/
public interface UserCouponService {

    /**
     * 根据用户id 和 状态查询优惠卷记录
     * @param userId
     * @param status
     * @return
     * @throws DataValidateException
     */
    List<CouponDTO> selectCouponByIdAndStatus(Long userId, Integer status) throws DataValidateException;

    /**
     * 根据用户id 查找当前可以领取的优惠卷模板
     * @return
     * @throws DataValidateException
     */
    List<CouponTemplateDTO> findAvailableCouponTemplate(Long userId) throws DataValidateException;

    /**
     * 用户领取优惠卷
     * @param couponTemplateDTO
     * @return
     */
    String getCoupon (CouponTemplateDTO couponTemplateDTO) throws DataValidateException;


}
