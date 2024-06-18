package com.gaea.work.login;

import java.sql.SQLException;

import com.gaea.work.member.MemberVO;

public interface LoginDao {
	
	int idCheck(MemberVO inVO)throws SQLException;
	
	int idPassCheck(MemberVO inVO)throws SQLException;

}
