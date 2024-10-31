package com.portfolio.artbook.support.controller;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.portfolio.artbook.support.exception.SupportException;
import com.portfolio.artbook.support.model.vo.Event;
import com.portfolio.artbook.support.model.vo.Payment; 
 import com.portfolio.artbook.support.service.SupportService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class SupportController {
	
	private SupportService sService;
	
	@Autowired
	public SupportController(SupportService sService) {
		this.sService = sService;
	}
	
	
	
	@PostMapping("payment")
	@ResponseBody
	public int completePayment(@ModelAttribute Payment payment) {
		int result = sService.payment(payment);
		
		if(result>0) {
			return result;
		}else throw new SupportException("후원 실패");
		
	}
	
	
	@PostMapping("eventInsert")
	@ResponseBody
	public int eventInsert(@ModelAttribute Event event) {
		
		int result = sService.eventInsert(event);
		
		if(result > 0) {
			return result;
		}else throw new SupportException("이벤트 등록 실패");
		
	}
	
	@PostMapping("eventSelect")
	@ResponseBody
	public void eventSelect(HttpServletResponse response) {
			
		ArrayList<Event> eventlist = sService.eventSelect();
		
		for(Event e : eventlist) {
			String oriNickName = e.getNickName();
			String newNickNameArr = oriNickName.substring(0, 2);
			
			for(int i = 0 ; i < oriNickName.length()-2; i++) {
				newNickNameArr += "*";
			}
			e.setNickName(newNickNameArr);
		}			
		
		response.setContentType("application/json; charset=UTF-8");
		GsonBuilder gb = new GsonBuilder().setDateFormat("yyyy-MM-dd");
		Gson gson = gb.create();

		try {
			gson.toJson(eventlist, response.getWriter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	@PostMapping("bestEvent")
	@ResponseBody
	public void bestEvent(HttpServletResponse response) {
			
		ArrayList<Event> bestEvent = sService.bestEvent();

		response.setContentType("application/json; charset=UTF-8");
		GsonBuilder gb = new GsonBuilder().setDateFormat("yyyy-MM-dd");
		Gson gson = gb.create();

		try {
			gson.toJson(bestEvent, response.getWriter());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}


}
