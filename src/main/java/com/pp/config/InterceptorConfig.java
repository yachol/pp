package com.pp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.pp.interceptor.AuthentificationInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration interceptorRegistration = registry.addInterceptor(new AuthentificationInterceptor());
		interceptorRegistration.addPathPatterns("/**");
		interceptorRegistration.excludePathPatterns("/**/*register*/**", "/**/*login*/**","/","/**/*welcome*/**","/**/*list2*/**",
				"/pp/User/getcode","/pp/Product/getproducts","/pp/Product/getcircusee",
				"/pp/User/getusers","/pp/User/frozen","/pp/Product/getproducts2","/pp/Product/updateState",
				"/pp/History/gethistory2","/pp/Product/getdetail","/pp/User/alipay_callback");
		super.addInterceptors(registry);
	}
}
