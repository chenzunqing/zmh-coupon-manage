package com.zmh.coupon.manage.web.controller;

import com.zmh.coupon.service.CouponTemplateService;
import com.zmh.coupon.service.dto.CouponTemplateDTO;
import com.zmh.coupon.util.exception.DataValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author 陈尊清
 * @create 2020-09-12-21:47
 */
@Slf4j
@RestController
@RequestMapping("/coupon/template")
public class CouponTemplateController {


    @Autowired
    private CouponTemplateService couponTemplateService;

    @PostMapping("/save")
    public void CouponTemplateSave(@RequestBody CouponTemplateDTO couponTemplateDTO) throws DataValidateException {

        couponTemplateService.buildCouponTemplate(couponTemplateDTO);
    }


}
