package com.artBook.myArtBook.board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.artBook.myArtBook.board.model.vo.Attm;
import com.artBook.myArtBook.board.model.vo.Board;
import com.artBook.myArtBook.board.model.vo.BoardAttm;

@Mapper
public interface BoardMapper {

	int insertBoard(Board b);

	int insertAttm(ArrayList<Attm> attmList);

	ArrayList<BoardAttm> getMainList();


	
	
}
