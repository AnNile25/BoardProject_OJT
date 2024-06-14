package com.gaea.work.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main")
public class MainController  {
	public MainController() {}
	
	@RequestMapping(value="/mainView.do")
	public String mainView() {
		return "main/main";
	}
}
