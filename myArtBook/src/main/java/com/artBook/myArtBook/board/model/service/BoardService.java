package com.artBook.myArtBook.board.model.service;

import java.util.ArrayList;

import com.artBook.myArtBook.board.model.vo.Attm;
import com.artBook.myArtBook.board.model.vo.Board;
import com.artBook.myArtBook.board.model.vo.BoardAttm;

public interface BoardService {

	int insertBoard(Board b);

	int insertAttm(ArrayList<Attm> attmList);
	
	ArrayList<Attm> getMainSlidList();

	ArrayList<BoardAttm> getMainList();

	

}
