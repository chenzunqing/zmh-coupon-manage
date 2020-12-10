package com.zmh.coupon.service.scheduled;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zmh.coupon.dao.mapper.CouponTemplateMapper;
import com.zmh.coupon.model.CouponTemplate;
import com.zmh.coupon.service.dto.TemplateRuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 陈尊清
 * @create 2020-09-13-16:26
 */
@Component
public class CouponTask {
    @Autowired
    private CouponTemplateMapper couponTemplateMapper;

    @Scheduled(fixedRate = 1000)
    public void offlineCouponTemplate() {

        List<CouponTemplate> templates = couponTemplateMapper.selectList(Wrappers.<CouponTemplate>lambdaQuery().eq(CouponTemplate::getExpired,false ));
        if (CollectionUtils.isEmpty(templates)) {
            return;
        }
        //有过期的优惠卷
        Date current = new Date();
        List<CouponTemplate> expiredTemplates = new ArrayList<>(templates.size());
        templates.forEach(t -> {
            TemplateRuleDTO rule = JSON.parseObject(t.getRule(),TemplateRuleDTO.class);
            if (rule.getExpiration().getDeadline() < current.getTime()) {
                t.setExpired(true);
                expiredTemplates.add(t);
            }
        });
        //修改模板的过期状态
        if (CollectionUtils.isNotEmpty(expiredTemplates)) {
            for(CouponTemplate expiredTemplate : expiredTemplates){
                couponTemplateMapper.update(expiredTemplate, Wrappers.<CouponTemplate>lambdaQuery().eq(CouponTemplate::getName, expiredTemplate.getName()));
            }
        }
    }
}
