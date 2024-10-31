package com.portfolio.artbook.board.model.service;

import java.util.ArrayList;

import com.portfolio.artbook.board.model.vo.Attm;
import com.portfolio.artbook.board.model.vo.Board;
import com.portfolio.artbook.board.model.vo.BoardAttm;
import com.portfolio.artbook.common.Pagination;

public interface BoardService {

	int insertBoard(Board b);

	int insertAttm(ArrayList<Attm> attmList);
	
	ArrayList<BoardAttm> getMainList();

	ArrayList<Board> getCList();

	ArrayList<BoardAttm> getAllList();

	BoardAttm getBoardAttm(int boardNo);
	
	int getlistCount(int boardNo);

	String[] getAttm2Pi(Pagination pi, int boardNo);

	ArrayList<Attm> getAttm(int boardNo);

	int updateBoard(Board b);

	int deleteAttmL1(String delsImg);

	int deleteAttmL2(String delImgNewName);

	int goodUp(int boardNo);

	int goodDown(int boardNo);

	ArrayList<Board> getTopList();

	ArrayList<BoardAttm> searchBoard(String searchTitle);


	

}
