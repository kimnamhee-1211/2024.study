package com.portfolio.artbook.common;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Pagination{
	
	private int currentPage;
	private int listCount;
	private int pageLimite;
	private int maxPage;
	private int startPage;
	private int endPage;
	private int boardLimite;
	
	
	public static Pagination getPageInfo(int pageLimit, int currentPage, int listCount, int boardLimit) {
		int maxPage;
		int startPage;
		int endPage;
		
		maxPage=(int)Math.ceil((double)listCount/boardLimit);
		startPage = (currentPage - 1)/pageLimit * pageLimit + 1;
		endPage = startPage + pageLimit - 1;
		if(maxPage<endPage) {
			endPage = maxPage;
		}
		
		Pagination pi = new Pagination(currentPage, listCount, pageLimit, maxPage, startPage, endPage, boardLimit);
		
		return pi;
	}


}
