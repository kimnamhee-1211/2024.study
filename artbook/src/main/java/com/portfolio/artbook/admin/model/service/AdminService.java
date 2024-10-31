package com.portfolio.artbook.admin.model.service;

import java.sql.Date;
import java.util.ArrayList;

import com.portfolio.artbook.admin.model.vo.Admin;
import com.portfolio.artbook.board.model.vo.Board;
import com.portfolio.artbook.support.model.vo.Event;
import com.portfolio.artbook.support.model.vo.Payment;

public interface AdminService {

	int enrollAdmin(Admin a);

	Admin loginAdmin(String name);

	String[] getAdName();

	Admin getMainAdmin();

	int eventWinnerConfirm(int eventNo);

	ArrayList<Event> EventWinnerList();

	ArrayList<Board> boardData();

	ArrayList<Payment> getAllPayment();

	ArrayList<Payment> getbestPaylist();

	int updateAdmin(Admin a, String oldName);

}
