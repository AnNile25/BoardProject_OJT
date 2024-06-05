package com.gaea.work.member.dao;

import java.sql.SQLException;
import java.util.List;

import com.gaea.work.cmn.WorkDiv;
import com.gaea.work.member.domain.MemberVO;

public interface MemberDao extends WorkDiv<MemberVO> {
	
	List<MemberVO> getAll(MemberVO inVO) throws SQLException;

	int getCount(MemberVO inVO) throws SQLException;
	
	int idDuplicateCheck(MemberVO inVO) throws SQLException;

}
