package com.gaea.work.login;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.dao.EmptyResultDataAccessException;

import com.gaea.work.member.MemberVO;

public interface LoginService {
	
	MemberVO doSelectOne(MemberVO inVO) throws SQLException, EmptyResultDataAccessException;
	
	// 데이터베이스 id/password 체크
	int loginCheck(MemberVO inVO)throws SQLException;
	
	String doLogin(MemberVO member, HttpSession session);

}
