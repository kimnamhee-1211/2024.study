package com.portfolio.artbook.support.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.artbook.support.model.vo.Event;
import com.portfolio.artbook.support.model.vo.Payment;

@Mapper
public interface SupportMapper {

	int payment(Payment payment);

	int eventInsert(Event event);

	ArrayList<Event> eventSelect();

	ArrayList<Event> bestEvent();

}
