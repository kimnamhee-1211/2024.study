package com.portfolio.artbook.common.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import com.portfolio.artbook.admin.model.vo.Admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

public class CheckLoginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		
		Admin loginUser = (Admin)session.getAttribute("loginUser");
		
		if(loginUser == null) {
			String msg = "You do not have permission to access this.";
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write("<script> alert('"+ msg +"'); location.href='goMain.bo';</script>");
			return false;						
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	

}
