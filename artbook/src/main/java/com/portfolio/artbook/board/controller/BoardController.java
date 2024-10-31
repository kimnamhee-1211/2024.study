package com.portfolio.artbook.board.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.portfolio.artbook.admin.model.service.AdminService;
import com.portfolio.artbook.admin.model.vo.Admin;
import com.portfolio.artbook.board.model.exception.BoardException;
import com.portfolio.artbook.board.model.service.BoardService;
import com.portfolio.artbook.board.model.vo.Attm;
import com.portfolio.artbook.board.model.vo.Board;
import com.portfolio.artbook.board.model.vo.BoardAttm;
import com.portfolio.artbook.common.Pagination;
import com.portfolio.artbook.support.model.vo.Event;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller
public class BoardController {

	private BoardService bService;
	private AdminService aService;
	
	@Autowired
	public BoardController(BoardService bService, AdminService aService) {
		this.bService = bService;
		this.aService = aService;
	}

	@GetMapping("welcom")
	public String welcom(Model model){
		
		ArrayList<BoardAttm> list = bService.getMainList();		
		
		
		Admin mainAd = aService.getMainAdmin();
		
		if(list != null) {
			model.addAttribute("mainAd", mainAd);
			model.addAttribute("list", list);
			return "main";
		}else throw new BoardException("메인 화면의 리스트 조회 실패(welcom)"); 
	}
	
	
	@GetMapping("goMain.bo")
	public String goMain(Model model){
		
		ArrayList<BoardAttm> list = bService.getMainList();		
		
		
		Admin mainAd = aService.getMainAdmin();
		
		if(list != null) {
			model.addAttribute("mainAd", mainAd);
			model.addAttribute("list", list);
			return "main";
		}else throw new BoardException("메인 화면의 리스트 조회 실패"); 
	}
	
	
	
	@GetMapping("goMenu1.bo")
	public String goMenu1(Model model){
		ArrayList<BoardAttm> list = bService.getAllList();	
		
		ArrayList<BoardAttm> slist = new ArrayList<BoardAttm>();
		ArrayList<BoardAttm> phlist = new ArrayList<BoardAttm>();	
		
		//썸네일과 일반 사진 구분
		for(BoardAttm l : list) {
			if(l.getAttmLevel() == 1) {
				slist.add(l);
			}else {
				phlist.add(l);
			}				
		}
		
		//기본 4개와 무한스크롤 구분
		ArrayList<BoardAttm> mlist = new ArrayList<BoardAttm>();
		ArrayList<BoardAttm> plist = new ArrayList<BoardAttm>();
		for(int i = 0; i < slist.size(); i++) {
			if(i < 4) {
				mlist.add(slist.get(i));
			}else {
				plist.add(slist.get(i));
			}
		}		
		
		model.addAttribute("mlist", mlist);
		model.addAttribute("plist", plist);
		model.addAttribute("phlist", phlist);
		
		//top3
		ArrayList<Board> toplist = bService.getTopList();
		
		model.addAttribute("toplist", toplist);
		
		
		
		if(list != null) {
			return "menu1";
		}else throw new BoardException("메뉴1 화면의 리스트 조회 실패");
	}
	
	
	
	@GetMapping("goMenu2.bo")
	public String goMenu2(){
				
		return "menu2_copy";
	}
	
	
	@GetMapping("goWriteBoard.bo")
	public String goWriteBoard(){
		
		return "writeBoard";
	}
		
	
	@PostMapping("writeBoard.bo")
	public String writeBoard(@ModelAttribute Board b,
			@RequestParam("start") String start, @RequestParam("end") String end,
			@RequestParam(value="sfile",  required=false) MultipartFile sfile,  
			@RequestParam(value="files", required=false) ArrayList<MultipartFile> files){
		
		//날짜 포맷
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date startUtilDate = null;
		java.util.Date endUtilDate = null;
		try {
			startUtilDate = sdf.parse(start);
			endUtilDate = sdf.parse(end);
			Date startSqlDate = new Date(startUtilDate.getTime());
	        Date endSqlDate = new Date(endUtilDate.getTime());    
			
			b.setStartDate(startSqlDate);
			b.setEndDate(endSqlDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int result = bService.insertBoard(b);
		
		int boardNo = b.getBoardNo();
		
		//썸넬 이미지 저장
		ArrayList<Attm> attmList = new ArrayList<Attm>();			
		if(sfile != null && !sfile.isEmpty()){
			Attm sattm = new Attm();
			
			String[] retrunArr = saveFile(sfile);
			
			sattm.setOriginName(sfile.getOriginalFilename());
			sattm.setAttmPath(retrunArr[0]);
			sattm.setNewName(retrunArr[1]);
			sattm.setAttmLevel(1);
			sattm.setBoardNo(boardNo);
			
			attmList.add(sattm);
		}	
		
		//이미지 묶음 저장
		if(files != null && !files.isEmpty()){
			for(MultipartFile file : files) {
			
				String[] retrunArr = saveFile(file);
				
				Attm attm = new Attm();
				
				attm.setOriginName(file.getOriginalFilename());
				attm.setAttmPath(retrunArr[0]);
				attm.setNewName(retrunArr[1]);
				attm.setAttmLevel(2);
				attm.setBoardNo(boardNo);
				
				attmList.add(attm);
			}
		}
		
		if(attmList != null && !attmList.isEmpty()) {
			int result2 = bService.insertAttm(attmList);
		}

		if(result > 0) {
			return "redirect:goMain.bo";
		} else {
			throw new BoardException("게시물 저장 실패");
		}
	}


	private String[] saveFile(MultipartFile file) {
		
		String savePath = "D:\\artBook\\artBook\\gallery";
		
		File folder = new File(savePath);
		if(!folder.exists()) {
			folder.mkdir();
		}
				
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		java.util.Date today = new java.util.Date();
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
	
	
	@PostMapping("myCalendar.bo")
	@ResponseBody
	public void calendar(HttpServletResponse response){
		
		ArrayList<Board> cList = bService.getCList();
		
		response.setContentType("application/json; charset=UTF-8");
		GsonBuilder gb = new GsonBuilder().setDateFormat("yyyy-MM-dd");
		Gson gson = gb.create();
		
		try {
			gson.toJson(cList, response.getWriter());
		} catch (JsonIOException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@GetMapping("goDetailBoard.bo")
	public String goDetailBoard(@RequestParam("boardNo") int boardNo,
			Model model){
		
		BoardAttm b = bService.getBoardAttm(boardNo);
		model.addAttribute("b", b);		
		
		if(b != null) {
			return "detailBoard";
		}else throw new BoardException("게시글 상세 조회 실패");
		
	}
	
	@PostMapping("goDetailBoardPhoto.bo")
	@ResponseBody
	public void goDetailBoardPhoto(@RequestParam("boardNo") int boardNo,
			@RequestParam(value="page", defaultValue="1") int currentPage,
			HttpServletResponse response){
		
		int listCount = bService.getlistCount(boardNo);
		Pagination pi = Pagination.getPageInfo(1, currentPage, listCount, 2);		
		String[] alist = bService.getAttm2Pi(pi, boardNo);	
		
        Map<String, Object> item = new HashMap<>();
        item.put("pi", pi);
        item.put("alist", alist);
		
		response.setContentType("application/json; charset=UTF-8");
		GsonBuilder gb = new GsonBuilder().setDateFormat("yyyy-MM-dd");
		Gson gson = gb.create();
		try {
			gson.toJson(item, response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@GetMapping("goUpdateBoard.bo")
	public String goUpdateBoard(@RequestParam("boardNo") int boardNo,
			Model model){
		
		BoardAttm b = bService.getBoardAttm(boardNo);
		String startDate = b.getStartDate().toString().replace("/", "-");
		String endDate = b.getEndDate().toString().replace("/", "-");
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("b", b);
		
		ArrayList<Attm> alist = bService.getAttm(boardNo);
		
		Attm sattm = new Attm();
		ArrayList<Attm> plist = new ArrayList<Attm>();
		
		for(Attm a : alist){
			if(a.getAttmLevel() == 1) {
				sattm = a;
			}else {
				plist.add(a);
			}			
		}
		
		model.addAttribute("sattm", sattm);
		model.addAttribute("plist", plist);
		
		if(b != null && alist != null) {
			return "updateBoard";
			
		}else throw new BoardException("게시글 수정 조회 실패");
		
	}
	
	
	@PostMapping("updateBoard.bo")
	public String updateBoard(@ModelAttribute Board b,
			@RequestParam("start") String start, @RequestParam("end") String end,
			@RequestParam("delsImg") String delsImg,
			@RequestParam(value="delImg",  required=false) String[] delImg,
			@RequestParam(value="sfile",  required=false) MultipartFile sfile,  
			@RequestParam(value="files", required=false) ArrayList<MultipartFile> files,
			RedirectAttributes re){

		int boardNo = b.getBoardNo();
				
		//날짜 포맷
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date startUtilDate = null;
		java.util.Date endUtilDate = null;
		try {
			startUtilDate = sdf.parse(start);
			endUtilDate = sdf.parse(end);
			Date startSqlDate = new Date(startUtilDate.getTime());
	        Date endSqlDate = new Date(endUtilDate.getTime());    
			
			b.setStartDate(startSqlDate);
			b.setEndDate(endSqlDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int result = bService.updateBoard(b);
		
		//썸넬 이미지 삭제-저장
		ArrayList<Attm> attmList = new ArrayList<Attm>();			
		if(sfile != null && !sfile.isEmpty()){
			
			//기존 썸넬 이미지 삭제
			int result1 = deleteFile(delsImg);
			if(result1 == 1) {
				int result2 = bService.deleteAttmL1(delsImg);
				if(result2 < 1) throw new BoardException("쎔네일 이미지 DB 삭제 실패");
			}else throw new BoardException("쎔네일 이미지 파일 삭제 실패");
			
			
			//저장
			Attm sattm = new Attm();			
			String[] retrunArr = saveFile(sfile);			
			sattm.setOriginName(sfile.getOriginalFilename());
			sattm.setAttmPath(retrunArr[0]);
			sattm.setNewName(retrunArr[1]);
			sattm.setAttmLevel(1);
			sattm.setBoardNo(boardNo);
			
			attmList.add(sattm);
		}	
		
		//이미지 묶음 삭제
		if(delImg != null) {
			for(String delImgNewName : delImg) {
				int result3 = deleteFile(delImgNewName);
				if(result3 == 1) {
					int result4 = bService.deleteAttmL2(delImgNewName);
					if(result4 < 1) throw new BoardException("이미지 DB 삭제 실패");
				}else throw new BoardException("게시물 이미지 파일 삭제 실패");
			}
		}
				
		//이미지 묶음 저장
		if(files != null && !files.isEmpty()){
			for(MultipartFile file : files) {
			
				String[] retrunArr = saveFile(file);
				
				Attm attm = new Attm();
				
				attm.setOriginName(file.getOriginalFilename());
				attm.setAttmPath(retrunArr[0]);
				attm.setNewName(retrunArr[1]);
				attm.setAttmLevel(2);
				attm.setBoardNo(boardNo);
				
				attmList.add(attm);
			}
		}
		
		if(attmList != null && !attmList.isEmpty()) {
			int result3 = bService.insertAttm(attmList);
		}


		if(result > 0) {
			re.addAttribute("boardNo", boardNo);
			return "redirect:goDetailBoard.bo";
		} else {
			throw new BoardException("게시물 수정 실패");
		}
	}

	
	private int deleteFile(String delImgNewName) {
		
		String savePath = "D:\\artBook\\gallery";		
		File folder = new File(savePath);
		
		if (folder.exists() && folder.isDirectory()) {
			File delfolder = new File(savePath, delImgNewName);
			if(delfolder.delete()) {
				return 1;
			}
		}					
		return 0;		
	}
	
	@PostMapping("goodUp.bo")
	@ResponseBody
	public void goodUp(@RequestParam("boardNo") int boardNo,
			HttpServletResponse response){
		int result = bService.goodUp(boardNo);
		response.setContentType("application/json; charset=UTF-8");
		GsonBuilder gb = new GsonBuilder().setDateFormat("yyyy-MM-dd");
		Gson gson = gb.create();
		try {
			gson.toJson(result, response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("goodDown.bo")
	@ResponseBody
	public void goodDown(@RequestParam("boardNo") int boardNo,
			HttpServletResponse response){
		int result = bService.goodDown(boardNo);
		
		response.setContentType("application/json; charset=UTF-8");
		GsonBuilder gb = new GsonBuilder().setDateFormat("yyyy-MM-dd");
		Gson gson = gb.create();
		try {
			gson.toJson(result, response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@PostMapping("searchBoard.bo")
	@ResponseBody
	public HashMap<String, Object> searchBoard(@RequestParam("searchTitle") String searchTitle,
			HttpServletResponse response){
		
		ArrayList<BoardAttm> searchlist = bService.searchBoard(searchTitle);
		ArrayList<BoardAttm> searchlists = new ArrayList<BoardAttm>();
		ArrayList<BoardAttm> searchlistph = new ArrayList<BoardAttm>();
		
		for(BoardAttm b : searchlist) {
			if(b.getAttmLevel() == 1) {
				searchlists.add(b);
			}else {
				searchlistph.add(b);			
			}
		}
		
		HashMap<String, Object> searchdata = new HashMap<String, Object>();
		
		searchdata.put("searchlists", searchlists);
		searchdata.put("searchlistph", searchlistph);
		
		return searchdata;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
