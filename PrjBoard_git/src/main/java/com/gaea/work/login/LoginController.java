package com.gaea.work.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaea.work.member.MemberVO;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginService service;

    public LoginController() {}

    @RequestMapping(value="/loginView")
	public String loginView() {
        return "login/login";
    }
  
    @PostMapping(value="/login.do", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String doLogin(MemberVO member, HttpSession session) {
        return service.doLogin(member, session);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login/loginView";
    }
}
