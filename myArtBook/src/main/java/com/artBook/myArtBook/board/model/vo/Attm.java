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
public class Attm {

	private int attmNo;
	private String attmPath;
	private String originName;
	private String newName;
	private int attmLevel;
	private int boardNo;
	private Date createDate;

	
	
	
	
	
	
}
