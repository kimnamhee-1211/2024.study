package com.portfolio.artbook.admin.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
	
	private String name;
	private String password;
	private String profilOriginName;
	private String profilNewName;
	private String profilPath;
	private String email;
	private Date updateDate;
	private Date createDate;
	private int adLevel;
	

}
