package com.portfolio.artbook.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Board {

	private int boardNo;
	
	private String title;
	private String intro;
	private String content;
	private String category;

	private String showTitle;
	private String artist;
	private Date startDate;
	private Date endDate;
	
	private String status;
	private Date createDate;
	private Date updateDate;
	
}
