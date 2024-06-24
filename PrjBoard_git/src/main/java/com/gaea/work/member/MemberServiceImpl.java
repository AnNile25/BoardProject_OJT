package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaea.work.validation.MemberValidationService;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDao dao;	
	@Autowired
    MemberValidationService validationService;
	@Autowired
    BCryptPasswordEncoder passwordEncoder;
	
	public MemberServiceImpl() {}

	@Override
	public int updateMemberInfo(MemberVO inVO) throws SQLException {
		return dao.updateMember(inVO);
	}

	@Override
	@Transactional
	public int changeMemberPassword(MemberVO inVO) throws SQLException {
		String encodedPassword = passwordEncoder.encode(inVO.getPassword());
        inVO.setPassword(encodedPassword);
		return dao.updateMemberPassword(inVO);
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
	@Transactional
	public int joinMember(MemberVO inVO) throws SQLException {
		validationService.validateMember(inVO);
		String encodedPassword = passwordEncoder.encode(inVO.getPassword());
        inVO.setPassword(encodedPassword);
		return dao.saveMember(inVO);
	}

	@Override
	public List<MemberVO> retrieveMember(MemberVO inVO) throws SQLException {
		return dao.retrieveMember(inVO);
	}


}
