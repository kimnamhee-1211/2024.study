package com.portfolio.artbook.support.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.artbook.board.model.dao.BoardMapper;
import com.portfolio.artbook.support.model.dao.SupportMapper;
import com.portfolio.artbook.support.model.vo.Event;
import com.portfolio.artbook.support.model.vo.Payment;

@Service
public class SupportServiceImple implements SupportService{
	private SupportMapper sMapper;
	
	@Autowired
	public SupportServiceImple(SupportMapper sMapper) {
		this.sMapper = sMapper;
	}
	
	
	@Override
	public int payment(Payment payment) {
		return sMapper.payment(payment);
	}


	@Override
	public int eventInsert(Event event) {
		return sMapper.eventInsert(event);
	}


	@Override
	public ArrayList<Event> eventSelect() {
		return sMapper.eventSelect();
	}


	@Override
	public ArrayList<Event> bestEvent() {
		return sMapper.bestEvent();
	}

	
	
	
}
