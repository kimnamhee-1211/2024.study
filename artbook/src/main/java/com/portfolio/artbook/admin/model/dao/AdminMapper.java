package com.portfolio.artbook.admin.model.dao;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.portfolio.artbook.admin.model.vo.Admin;
import com.portfolio.artbook.board.model.vo.Board;
import com.portfolio.artbook.support.model.vo.Event;
import com.portfolio.artbook.support.model.vo.Payment;

@Mapper
public interface AdminMapper {

	public int enrollAdmin(Admin a);

	public Admin loginAdmin(String name);

	public String[] getAdName();

	public Admin getMainAdmin();

	public int eventWinnerConfirm(int eventNo);

	public ArrayList<Event> EventWinnerList();

	public ArrayList<Board> boardData();

	public ArrayList<Payment> getAllPayment();

	public ArrayList<Payment> getbestPaylist();

	public int updateAdmin(@Param("a") Admin a, @Param("oldName") String oldName);

}
