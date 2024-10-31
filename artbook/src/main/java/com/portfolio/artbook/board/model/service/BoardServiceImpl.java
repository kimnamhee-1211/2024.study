package com.portfolio.artbook.board.model.service;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.artbook.board.model.dao.BoardMapper;
import com.portfolio.artbook.board.model.vo.Attm;
import com.portfolio.artbook.board.model.vo.Board;
import com.portfolio.artbook.board.model.vo.BoardAttm;
import com.portfolio.artbook.common.Pagination;

@Service
public class BoardServiceImpl implements BoardService{

	private BoardMapper bMapper;
	
	@Autowired
	public BoardServiceImpl(BoardMapper bMapper) {
		this.bMapper = bMapper;
	}
	
	@Override
	public int insertBoard(Board b){
		return bMapper.insertBoard(b);
	}

	@Override
	public int insertAttm(ArrayList<Attm> attmList){
		return bMapper.insertAttm(attmList);
	}

	@Override
	public ArrayList<BoardAttm> getMainList(){
		return bMapper.getMainList();
	}

	@Override
	public ArrayList<Board> getCList() {
		return bMapper.getCList();
	}

	@Override
	public ArrayList<BoardAttm> getAllList() {
		return bMapper.getAllList();
	}

	@Override
	public BoardAttm getBoardAttm(int boardNo) {
		return bMapper.getBoardAttm(boardNo);
	}
	
	
	@Override
	public int getlistCount(int boardNo) {
		return bMapper.getlistCount(boardNo);
	}
	
	@Override
	public String[] getAttm2Pi(Pagination pi, int boardNo) {
		int offset = (pi.getCurrentPage() - 1)*pi.getBoardLimite();
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimite());		
		return bMapper.getAttm2Pi(rowBounds, boardNo);
	}

	@Override
	public ArrayList<Attm> getAttm(int boardNo) {
		return bMapper.getAttm(boardNo);
	}

	@Override
	public int updateBoard(Board b) {
		return bMapper.updateBoard(b);
	}

	@Override
	public int deleteAttmL1(String delsImg) {
		return bMapper.deleteAttmL1(delsImg);
	}

	@Override
	public int deleteAttmL2(String delImgNewName) {
		return bMapper.deleteAttmL1(delImgNewName);
	}

	@Override
	public int goodUp(int boardNo) {
		return bMapper.goodUp(boardNo);
	}

	@Override
	public int goodDown(int boardNo) {
		return bMapper.goodDown(boardNo);
	}

	@Override
	public ArrayList<Board> getTopList() {
		// TODO Auto-generated method stub
		return bMapper.getTopList();
	}

	@Override
	public ArrayList<BoardAttm> searchBoard(String searchTitle) {
		// TODO Auto-generated method stub
		return bMapper.searchBoard(searchTitle);
	}




	
	
}
