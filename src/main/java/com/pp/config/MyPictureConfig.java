package com.pp.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pp.utils.ConfigUtils;

@Configuration
public class MyPictureConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		String path="file:/D:/software/eclipse/moon-boot-woniupp/moon-app-cake/src/main/resources/static/pro_imgs/";
		String realpath = ConfigUtils.getVal("application.properties", "product.img");
		registry.addResourceHandler("/Path/**").addResourceLocations(realpath);
		super.addResourceHandlers(registry);
	}

}
