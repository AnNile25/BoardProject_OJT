package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gaea.work.validation.MemberValidationService;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDao dao;
	
	@Autowired
    MemberValidationService validationService;
	
	public MemberServiceImpl() {}

	@Override
	public int updateMemberInfo(MemberVO inVO) throws SQLException {
		return dao.updateMember(inVO);
	}

	@Override
	public int withdrawalMember(MemberVO inVO) throws SQLException {
		return dao.deleteMember(inVO);
	}

	@Override
	public MemberVO viewMemberDetail(MemberVO inVO) throws SQLException, EmptyResultDataAccessException {
		return dao.selectOneMember(inVO);
	}
	
	@Override
	public int joinMember(MemberVO inVO) throws SQLException {
		validationService.validateMember(inVO);
		return dao.saveMember(inVO);
	}

	@Override
	public List<MemberVO> retrieveMember(MemberVO inVO) throws SQLException {
		return dao.retrieveMember(inVO);
	}

}
