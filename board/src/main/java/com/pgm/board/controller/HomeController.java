package com.pgm.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pgm.board.model.User;
import com.pgm.board.service.UserService;

@Controller
@RequestMapping("/")
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value={"","index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("join")
	public String joinForm() {
		return "member/join";
	}
	
	//아이디 중복 검사 작업중 checkId
	@PostMapping("checkId")
	@ResponseBody
	public String checkId(String username) {
		//입력란에 가져오는 username 예시 : lsy
		User user = userService.findByUsername(username);
		boolean usernameYes = userService.existsByUsername(username);
		String result = "success";
		
		if(user !=null) { //중복된 아이디임. 
			return "fail";
		} else if(user == null && "".equals(username) ) {
			return "no";
		}
		return result;
	}
	
	@PostMapping("join")
	@ResponseBody
	public String register(@RequestBody User user) {
		
		if(userService.findByUsername(user.getUsername())!=null) {
			return "fail";
		}
		userService.register(user);
		return "success";
	}
	
	@GetMapping("login")
	public String loginForm() {
		return "member/login";
	}
	
	//로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sUser");
		return "redirect:/board/list";
		
	}
	
	@PostMapping("login")
	@ResponseBody
	public String loginPro(String username, String password,HttpSession session) {
		User user=userService.findByUsername(username);
		String result="no";
		if(user!=null) {
			if(user.getPassword().equals(password)) {
				session.setAttribute("sUser", user);
				result="success";
			}else {
				result="fail";
			}
		}
		return result;
	}

}
