package com.gaea.work.login;

import java.sql.SQLException;

import org.springframework.dao.EmptyResultDataAccessException;

import com.gaea.work.member.MemberVO;

public interface LoginDao {
	
	int idCheck(MemberVO inVO)throws SQLException;
	
	int idPassCheck(MemberVO inVO)throws SQLException;
	
	MemberVO selectOneMember(MemberVO inVO) throws SQLException, EmptyResultDataAccessException;
	
	MemberVO getMemberId(String memberId) throws SQLException;

}
