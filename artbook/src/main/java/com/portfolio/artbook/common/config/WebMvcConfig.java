package com.portfolio.artbook.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.portfolio.artbook.common.Interceptor.AdminLoginInterceptor;
import com.portfolio.artbook.common.Interceptor.CheckLoginInterceptor;
import com.portfolio.artbook.common.Interceptor.VisitorInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("file:/D:/artBook/artBook/gallery/")
				.addResourceLocations("file:/D:/artBook/artBook/profil/")
				.addResourceLocations("classpath:/static/image/");
	
	}
	
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor( new CheckLoginInterceptor())
			.addPathPatterns("/goAdmin.bo");
		
		registry.addInterceptor( new AdminLoginInterceptor())
			.addPathPatterns("/login.ad");
		
		registry.addInterceptor( new VisitorInterceptor())
			.addPathPatterns("/welcom");
		
				
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	
	
	
	
	
	
	
}
