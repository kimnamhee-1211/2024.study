package com.portfolio.artbook.admin.model.service;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.artbook.admin.model.dao.AdminMapper;
import com.portfolio.artbook.admin.model.vo.Admin;
import com.portfolio.artbook.board.model.vo.Board;
import com.portfolio.artbook.support.model.vo.Event;
import com.portfolio.artbook.support.model.vo.Payment;

@Service
public class AdminServiceImpl implements AdminService {

	private AdminMapper aMapper;
	
	@Autowired
	public AdminServiceImpl(AdminMapper aMapper) {
		this.aMapper = aMapper;
	}
	
	@Override
	public int enrollAdmin(Admin a) {
		
		return aMapper.enrollAdmin(a);
	}

	@Override
	public Admin loginAdmin(String name) {
		// TODO Auto-generated method stub
		return aMapper.loginAdmin(name);
	}



	@Override
	public String[] getAdName() {
		// TODO Auto-generated method stub
		return aMapper.getAdName();
	}



	@Override
	public Admin getMainAdmin() {
		// TODO Auto-generated method stub
		return aMapper.getMainAdmin();
	}

	@Override
	public int eventWinnerConfirm(int eventNo) {
		// TODO Auto-generated method stub
		return aMapper.eventWinnerConfirm(eventNo);
	}

	@Override
	public ArrayList<Event> EventWinnerList() {
		// TODO Auto-generated method stub
		return aMapper.EventWinnerList();
	}

	@Override
	public ArrayList<Board> boardData() {
		// TODO Auto-generated method stub
		return aMapper.boardData();
	}

	@Override
	public ArrayList<Payment> getAllPayment() {
		// TODO Auto-generated method stub
		return aMapper.getAllPayment();
	}

	@Override
	public ArrayList<Payment> getbestPaylist() {
		// TODO Auto-generated method stub
		return aMapper.getbestPaylist();
	}

	@Override
	public int updateAdmin(Admin a, String oldName) {
		// TODO Auto-generated method stub
		return aMapper.updateAdmin(a, oldName);
	}



}
