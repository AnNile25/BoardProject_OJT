package com.gaea.work.main;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gaea.work.member.MemberVO;

@Controller
@RequestMapping("main")
public class MainController  {
	public MainController() {}
	
	@RequestMapping(value="/mainView.do")
	public String mainView() {
		return "main/main";
	}
	
	@GetMapping(value="/popup.do")
	public String pop(MemberVO inVO, HttpSession session) {
		return "main/changePwPop";
	}
}
