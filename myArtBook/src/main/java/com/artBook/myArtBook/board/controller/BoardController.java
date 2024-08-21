package com.artBook.myArtBook.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.artBook.myArtBook.board.model.vo.Attm;
import com.artBook.myArtBook.board.model.vo.Board;
import com.artBook.myArtBook.board.model.vo.BoardAttm;
import com.artBook.myArtBook.board.model.exception.BoardException;
import com.artBook.myArtBook.board.model.service.BoardService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService bService;
	
	
	@GetMapping("goMain.bo")
	public String goMain(Model model){
		
		ArrayList<BoardAttm> list = bService.getMainList();
			
		model.addAttribute("list", list);
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
	
	
	@PostMapping("writeBoard.bo")
	public String writeBoard(@ModelAttribute Board b, @RequestParam("sfile") MultipartFile sfile,  @RequestParam("files") ArrayList<MultipartFile> files){
		
		int result = bService.insertBoard(b);
	
		ArrayList<Attm> attmList = new ArrayList<Attm>();	
		
		if(sfile != null && !sfile.isEmpty()){
			Attm sattm = new Attm();
			
			String[] retrunArr = saveFile(sfile);
			
			sattm.setOriginName(sfile.getOriginalFilename());
			sattm.setAttmPath(retrunArr[0]);
			sattm.setNewName(retrunArr[1]);
			sattm.setAttmLevel(1);
			sattm.setBoardNo(b.getBoardNo());
			
			attmList.add(sattm);
		}	
		
		if(files != null && !files.isEmpty()){
			for(MultipartFile file : files) {
			
				String[] retrunArr = saveFile(file);
				
				Attm attm = new Attm();
				
				attm.setOriginName(sfile.getOriginalFilename());
				attm.setAttmPath(retrunArr[0]);
				attm.setNewName(retrunArr[1]);
				attm.setAttmLevel(2);
				attm.setBoardNo(b.getBoardNo());
				
				attmList.add(attm);
			}
		}
		
		if(attmList != null && !attmList.isEmpty()) {
			int result2 = bService.insertAttm(attmList);
		}

		if(result > 0) {
			return "redirect:goMain.bo";
		} else {
			throw new BoardException();
		}
		
	}


	private String[] saveFile(MultipartFile file) {
		
		String savePath = "C:\\Users\\namhe\\myAtrBook\\uploadFile";
		
		File folder = new File(savePath);
		if(!folder.exists()) {
			folder.mkdir();
		}
				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date today = new Date();
		int randomNum = ((int)(Math.random()*10000));
		String renewName = sdf.format(today) + randomNum + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
			
		String renamePath = folder + "\\" + renewName;
		
		try {
			file.transferTo(new File(renamePath));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}		
		
		String[] retrunArr = new String[2];
		
		retrunArr[0] = savePath;
		retrunArr[1] = renewName;
		
		return retrunArr;
	}
	
	
	
	
	
	
	
	
	
}
