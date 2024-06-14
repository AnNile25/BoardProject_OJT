package com.gaea.work.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gaea.work.cmn.GLog;

@Controller
@RequestMapping("main")
public class MainController implements GLog {
	public MainController() {}
	
	@RequestMapping(value="/mainView.do")
	public String mainView() {
		String view ="main/main";
		LOG.debug("┌───────────────────────────────────────────┐");
		LOG.debug("│ mainView                                  │");
		LOG.debug("└───────────────────────────────────────────┘");	
		
		return view;
	}
}
