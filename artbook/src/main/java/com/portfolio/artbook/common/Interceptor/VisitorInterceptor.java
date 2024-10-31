package com.portfolio.artbook.common.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.portfolio.artbook.admin.model.vo.Admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VisitorInterceptor implements HandlerInterceptor{
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		log.info("Hellor, visitor");
				
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	

}
