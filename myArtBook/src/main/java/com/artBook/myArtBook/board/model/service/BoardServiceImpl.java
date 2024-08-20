package com.artBook.myArtBook.board.model.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.artBook.myArtBook.board.model.dao.BoardMapper;
import com.artBook.myArtBook.board.model.vo.Attm;
import com.artBook.myArtBook.board.model.vo.Board;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardMapper bMapper;
	
	@Override
	public int insertBoard(Board b) {
		return bMapper.insertBoard(b);
	}

	@Override
	public int insertAttm(ArrayList<Attm> attmList) {
		return bMapper.insertAttm(attmList);
	}

	
	
}
