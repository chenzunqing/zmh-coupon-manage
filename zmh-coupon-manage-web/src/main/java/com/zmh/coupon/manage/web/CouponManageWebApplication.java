package com.zmh.coupon.manage.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
@ComponentScan("com.zmh.coupon")
@MapperScan("com.zmh.coupon.dao.mapper")
public class CouponManageWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponManageWebApplication.class, args);
	}

}
