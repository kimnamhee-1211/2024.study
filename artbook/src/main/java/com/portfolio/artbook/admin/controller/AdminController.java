package com.portfolio.artbook.admin.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.portfolio.artbook.admin.model.exception.AdminException;
import com.portfolio.artbook.admin.model.service.AdminService;
import com.portfolio.artbook.admin.model.vo.Admin;
import com.portfolio.artbook.board.model.vo.Board;
import com.portfolio.artbook.support.model.vo.Event;
import com.portfolio.artbook.support.model.vo.Payment;
import com.portfolio.artbook.support.service.SupportService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	
	
	private AdminService aService;
	private SupportService sService;
	
	@Autowired
	public AdminController(AdminService aService, SupportService sService) {
		this.aService = aService;
		this.sService = sService;
	}
	
    
	@GetMapping("goAdmin.bo")
	public String goAdmin(@RequestParam(value="enrollMsg",  required=false) String enrollMsg, Model model){
		
		if(enrollMsg != null) {
			model.addAttribute("enrollMsg", enrollMsg);
		}
		
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.DAY_OF_MONTH, 1);
    	Date monthfirst = new Date(c.getTimeInMillis());
    	c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
    	Date monthlast = new Date(c.getTimeInMillis());
		
    	
    	ArrayList<Event> elist = aService.EventWinnerList();
    	Event thisMonth = null; 
    	
    	for(Event e : elist) {
    		if(e.getCreateDate().after(monthfirst) && e.getCreateDate().before(monthlast)) {
    			thisMonth = e;
    		}
    	}
    	model.addAttribute("elist", elist);
    	model.addAttribute("thisMonth", thisMonth);
    	
    	ArrayList<Board> boardData = aService.boardData();
    	model.addAttribute("boardData", boardData);
    	   	
    	ArrayList<Payment> paylist = aService.getAllPayment();
    	ArrayList<Payment> bestPaylist = aService.getbestPaylist();
    	model.addAttribute("paylist", paylist);
    	model.addAttribute("bestPaylist", bestPaylist);
    	
    	
    	StringBuilder[] visitorLog = getVisitorLog();
    	model.addAttribute("visitorLogAll", visitorLog[0]);
    	model.addAttribute("visitorLogAdmin", visitorLog[1]);
    	
		return "admin";
	}
	
	
	
	
	@PostMapping("enroll.ad")
	public String enrollAdmin(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("adfile") MultipartFile adfile, RedirectAttributes re) {
		
		Admin check = aService.loginAdmin(name);
		
		if(check == null) {
			String pass = BCrypt.hashpw(password, BCrypt.gensalt());
			
			Admin a = new Admin();
			a.setName(name);
			a.setPassword(pass);
			a.setEmail(email);
			
			if(adfile != null && !adfile.isEmpty()) {
				String[] profilArr = saveFile(adfile);
				
				a.setProfilPath(profilArr[0]);
				a.setProfilNewName(profilArr[1]);
				a.setProfilOriginName(adfile.getOriginalFilename());			
			}
			
			int result = aService.enrollAdmin(a);
			
			if(result > 0 ) {
				return "redirect:goAdmin.bo";
			}else throw new AdminException("관리자 등록 실패");			
			
		}else {
			String enrollMsg = "This username is already in use. Please try again with a different username.";
			re.addAttribute("enrollMsg", enrollMsg);
			return "redirect:goAdmin.bo";
		}		
	}
	
	
	@PostMapping("changeInfo.ad")
	public String changeInfo(@RequestParam(value="name", required=false) String name, @RequestParam("password") String password, 
			@RequestParam(value="email", required=false) String email,
			@RequestParam(value="adfile", required=false) MultipartFile adfile, RedirectAttributes re, HttpSession session) {	
		
		Admin a = (Admin)session.getAttribute("loginUser");
		
		String oldName = a.getName();
		
		String pass = BCrypt.hashpw(password, BCrypt.gensalt());			
		a.setPassword(pass);
		
		if(name != null) {
			a.setName(name);
		}
		if(email != null) {
			a.setEmail(email);
		}
					
		if(adfile != null && !adfile.isEmpty()) {
			String[] profilArr = saveFile(adfile);
			
			a.setProfilPath(profilArr[0]);
			a.setProfilNewName(profilArr[1]);
			a.setProfilOriginName(adfile.getOriginalFilename());			
		}

		int result = aService.updateAdmin(a, oldName);
		
		if(result > 0 ) {
			session.setAttribute("loginUser", a);
			return "redirect:goAdmin.bo";
		}else throw new AdminException("관리자 정보 수정 실패");						

	}

	
	
	
	
	private String[] saveFile(MultipartFile file) {
			
			String savePath = "D:\\artBook\\artBook\\profil";
			
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
	
	
	@PostMapping("login.ad")
	public String loginAdmin(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
		
		Admin a = aService.loginAdmin(name);
		
		if(a != null) {
			boolean b = BCrypt.checkpw(password, a.getPassword());
			System.out.println(b);
			if(b) {
				session.setAttribute("loginUser", a);
				return "redirect:goAdmin.bo";				
			}else {
				return "redirect:goMain.bo";
			}			
		}else {
			return "redirect:goMain.bo";
		}
		
				
	}
	
	@GetMapping("logout.ad")
	public String logoutAdmin(HttpSession session) {
		
		session.removeAttribute("loginUser");
		
		return "redirect:goMain.bo";
	}
	
	
	
	
	
	
    @GetMapping("eventWinner.ad")
    @ResponseBody
    public void eventWinner(HttpServletResponse response){
    	
    	ArrayList<Event> eventlist = sService.eventSelect();
    	ArrayList<Event> eventWinnerlist = new ArrayList<Event>();
    	
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.DAY_OF_MONTH, 1);
    	Date month = new Date(c.getTimeInMillis());
    	
    	
    	for(Event e : eventlist) {
    		Date createDate = e.getCreateDate();
    		if(createDate.compareTo(month) > 0) {
    			eventWinnerlist.add(e);
    		}
    	}
    	
    	Random random = new Random();
    	int randomNum =  random.nextInt(eventWinnerlist.size());
    	
    	Event winner = eventWinnerlist.get(randomNum);
    	
    	
		response.setContentType("application/json; charset=UTF-8");
		GsonBuilder gb = new GsonBuilder().setDateFormat("yyyy-MM-dd");
		Gson gson = gb.create();
		
		try {
			gson.toJson(winner, response.getWriter());
		} catch (IOException e) {
			e.printStackTrace();
		}    	
    }
	
    @PostMapping("eventWinnerConfirm.ad")
    @ResponseBody
    public int eventWinnerConfirm(@RequestParam("eventNo") int eventNo){   	
    	int result = aService.eventWinnerConfirm(eventNo);
    	return result;
    }
    
    
    private StringBuilder[] getVisitorLog() {
    	
    	String logPath= "D:\\artBook\\artbook\\log\\visitor";
    	
    	StringBuilder visitorAll = new StringBuilder();
    	StringBuilder visitorAdmin = new StringBuilder();
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(logPath + "\\all"));
    		String str;
			
			while((str = br.readLine()) != null) {
				visitorAll.append(str + "\n");
			}
			br.close();
			
    		BufferedReader br2 = new BufferedReader(new FileReader(logPath + "\\admin"));
    		str = "";
			while((str = br2.readLine()) != null) {
				visitorAdmin.append(str + "\n");
			}
			br2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	StringBuilder[] visitorLog = {visitorAll, visitorAdmin};
    	    	
    	return visitorLog;   	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	

}
