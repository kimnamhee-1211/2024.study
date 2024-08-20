package com.artBook.myArtBook.board.model.vo;

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

	private String title;
	private String intro;
	private String content;
	private String category;

	
	private String showTitle;
	private Date startDate;
	private Date endDate;
	private String artist;
	
	private String status;
	
}
