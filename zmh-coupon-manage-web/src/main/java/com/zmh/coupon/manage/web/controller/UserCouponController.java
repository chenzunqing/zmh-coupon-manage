package com.zmh.coupon.manage.web.controller;

import com.alibaba.fastjson.JSON;
import com.zmh.coupon.service.UserCouponService;
import com.zmh.coupon.service.dto.CouponDTO;
import com.zmh.coupon.service.dto.CouponTemplateDTO;
import com.zmh.coupon.util.exception.DataValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenzunqing
 * @version V1.0
 * @title : UserCouponController
 * @Description: 用户操作优惠卷控制层
 * @date 2020/9/14 15:45
 **/
@RestController
@RequestMapping("/coupon")
public class UserCouponController {
    @Autowired
    private UserCouponService userService;

    /**
     * 根据用户id 和 状态查询优惠卷记录
     * @param couponDTO
     * @return
     * @throws DataValidateException
     */
    @PostMapping("/select_coupon_by_id_and_status")
    public List<CouponDTO> selectCouponByIdAndStatus(@RequestBody  CouponDTO couponDTO) throws DataValidateException{
        return userService.selectCouponByIdAndStatus(couponDTO.getUserId(),couponDTO.getStatus());
    }

    /**
     * 根据用户id 查找当前可以领取的优惠卷模板
     * @param couponDTO
     * @return
     * @throws DataValidateException
     */
    @PostMapping("/find_available_coupon_template")
    public String findAvailableCouponTemplate(@RequestBody  CouponDTO couponDTO) throws DataValidateException{
        return JSON.toJSONString(userService.findAvailableCouponTemplate(couponDTO.getUserId()));
    }

    /**
     * 用户领取优惠卷
     * @param couponTemplateDTO
     * @return
     * @throws DataValidateException
     */
    @PostMapping("/get_coupon")
    public  String getCoupon (@RequestBody CouponTemplateDTO couponTemplateDTO) throws DataValidateException{
        return JSON.toJSONString(userService.getCoupon(couponTemplateDTO));
    }
}
