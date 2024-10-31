package com.portfolio.artbook.support.service;

import java.util.ArrayList;

import com.portfolio.artbook.support.model.vo.Event;
import com.portfolio.artbook.support.model.vo.Payment;

public interface SupportService {

	int payment(Payment payment);

	int eventInsert(Event event);

	ArrayList<Event> eventSelect();

	ArrayList<Event> bestEvent();

}
