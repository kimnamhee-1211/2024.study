package com.portfolio.artbook.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.portfolio.artbook.board.model.vo.Attm;
import com.portfolio.artbook.board.model.vo.Board;
import com.portfolio.artbook.board.model.vo.BoardAttm;
import com.portfolio.artbook.common.Pagination;

@Mapper
public interface BoardMapper {

	int insertBoard(Board b);

	int insertAttm(ArrayList<Attm> attmList);

	ArrayList<BoardAttm> getMainList();

	ArrayList<Board> getCList();

	ArrayList<BoardAttm> getAllList();

	BoardAttm getBoardAttm(int boardNo);
	
	int getlistCount(int boardNo);

	String[] getAttm2Pi(RowBounds rowBounds, int boardNo);

	ArrayList<Attm> getAttm(int boardNo);

	int updateBoard(Board b);

	int deleteAttmL1(String delsImg);

	int goodUp(int boardNo);

	int goodDown(int boardNo);

	ArrayList<Board> getTopList();

	ArrayList<BoardAttm> searchBoard(String searchTitle);


	
}
