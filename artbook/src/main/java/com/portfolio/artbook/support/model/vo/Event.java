package com.portfolio.artbook.support.model.vo;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {
	private int eventNo;
	private String title;
	private String expectation;
	private String nickName;
	private String email;
	private Date createDate;
	private String status;
	private String count;
	private String url;
	
	
	

}
