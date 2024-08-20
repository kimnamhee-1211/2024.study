package com.artBook.myArtBook.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class BoardController {
	
	@GetMapping("goMain.bo")
	public String goMain(){
		return "main";
	}
	
	
	@GetMapping("goMenu1.bo")
	public String goMenu1(){
		return "menu1";
	}
	
	
	@GetMapping("goMenu2.bo")
	public String goMenu2(){
		return "menu2";
	}
	
	@GetMapping("goWriteBoard.bo")
	public String goWriteBoard(){
		return "writeBoard";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
