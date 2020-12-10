package com.zmh.coupon.service;

import com.zmh.coupon.model.Coupon;
import com.zmh.coupon.service.dto.CouponDTO;
import com.zmh.coupon.util.exception.DataValidateException;

import java.util.List;

/**
 * @author chenzunqing
 * @version V1.0
 * @title : redisService
 * @Description: redis优惠卷操作相关的接口
 * @date 2020/9/14 13:34
 **/
public interface RedisCouponService {

    /**
     * 根据 userId 和状态找到redis的优惠券列表数据
     * @param userId
     * @param status
     * @return
     */
    List<CouponDTO> getRedisCoupons(Long userId, Integer status);

    /**
     * 保存空的优惠券列表到缓存中
     * @param userId
     * @param status
     */
    void saveEmptyCouponListToRedis(Long userId, List<Integer> status);

    /**
     * 从 redis 中获取一个优惠券码
     * @param templateId
     * @return
     */
    String getRedisCouponCode(Integer templateId);

    /**
     * 将优惠券保存到 redis 中
     * @param userId
     * @param coupons
     * @param status
     * @return
     * @throws DataValidateException
     */
    Integer addCouponToRedis(Long userId, List<CouponDTO> coupons, Integer status) throws DataValidateException;
}
