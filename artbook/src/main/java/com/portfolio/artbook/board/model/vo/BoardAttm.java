package com.portfolio.artbook.board.model.vo;

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
public class BoardAttm extends Board{
	
	private int attmNo;
	private String attmPath;
	private String originName;
	private String newName;
	private int attmLevel;

	
	
}
