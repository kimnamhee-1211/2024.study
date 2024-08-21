package com.artBook.myArtBook.board.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class BoardAttm {
	
	private int boardNo;
	
	private String title;
	private String intro;
	private String content;
	private int category;

	
	private String showTitle;
	private String artist;
	private Date startDate;
	private Date endDate;
	
	private String status;
	private Date createDate;
	
	private int attmNo;
	private String attmPath;
	private String originName;
	private String newName;
	private int attmLevel;

	
	
}
