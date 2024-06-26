package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

public interface MemberService {	
		
	int updateMemberInfo(MemberVO inVO) throws SQLException;
	
	int changeMemberPassword(MemberVO inVO) throws SQLException;
	
	int withdrawalMember(MemberVO inVO) throws SQLException;
	
	MemberVO viewMemberDetail(MemberVO inVO) throws SQLException, EmptyResultDataAccessException;
	
	int joinMember(MemberVO inVO) throws SQLException;
	
	List<MemberVO> retrieveMember(MemberVO inVO) throws SQLException;
	
}
