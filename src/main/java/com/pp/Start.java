package com.pp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import tk.mybatis.spring.annotation.MapperScan;
@EnableRedisHttpSession
@EnableEurekaClient 
@EnableCaching
@EnableScheduling
@EnableAsync
@MapperScan(basePackages = "com.pp.dao")
@ServletComponentScan("com.pp.listener")	
@SpringBootApplication
public class Start {
	public static void main(String[] args) {
		SpringApplication.run(Start.class, args);
	}
}
