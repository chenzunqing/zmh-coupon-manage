package com.zmh.coupon.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author 陈尊清
 * @create 2020-09-12-20:10
 */
@Component
public class MybatisFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        //默认保存
        this.setFieldValByName("available", false, metaObject);
        this.setFieldValByName("expired", false, metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("assignTime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

}
