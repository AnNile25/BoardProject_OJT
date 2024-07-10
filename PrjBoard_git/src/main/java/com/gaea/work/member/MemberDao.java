package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;

public interface MemberDao {
	//중복체크
    int idDuplicateCheck(MemberVO inVO) throws SQLException;

    int nickNameDuplicateCheck(MemberVO inVO) throws SQLException;
  
    int emailDuplicateCheck(MemberVO inVO) throws SQLException;
	
	// CRUD	
	int updateMember(MemberVO inVO) throws SQLException;
	
	int updateMemberPassword(MemberVO inVO) throws SQLException;
	
	int deleteMember(MemberVO inVO) throws SQLException;
	
	MemberVO selectOneMember(MemberVO inVO) throws SQLException;
	
	int saveMember(MemberVO inVO) throws SQLException;
	
	List<MemberVO> retrieveMember(MemberVO inVO) throws SQLException;

}
