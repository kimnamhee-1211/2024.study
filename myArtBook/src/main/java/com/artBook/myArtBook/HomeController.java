package com.artBook.myArtBook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("HomeController")
public class HomeController {
	
	@GetMapping("home.do")
	public String goHome(){
		
		return "main";
	}

}
