package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gaea.work.cmn.GLog;

@Service
public class MemberServiceImpl implements MemberService {
	
	Logger LOG = LogManager.getLogger(GLog.class);
	
	@Autowired
	MemberDao dao;
	
	public MemberServiceImpl() {}

	@Override
	public int updateMember(MemberVO inVO) throws SQLException {
		return dao.updateMember(inVO);
	}

	@Override
	public int withdrawalMember(MemberVO inVO) throws SQLException {
		return dao.deleteMember(inVO);
	}

	@Override
	public MemberVO selectOneMember(MemberVO inVO) throws SQLException, EmptyResultDataAccessException {
		return dao.selectOneMember(inVO);
	}
	
	@Override
	public int joinMember(MemberVO inVO) throws SQLException {
		return dao.saveMember(inVO);
	}

	@Override
	public List<MemberVO> retrieveMember(MemberVO inVO) throws SQLException {
		return dao.retrieveMember(inVO);
	}
	
	// 중복 체크
	@Override
    public boolean isIdDuplicate(String memberId) throws SQLException {
        MemberVO vo = new MemberVO();
        vo.setMemberId(memberId);
        return dao.idDuplicateCheck(vo) > 0;
    }

    @Override
    public boolean isNickNameDuplicate(String nickName) throws SQLException {
        MemberVO vo = new MemberVO();
        vo.setNickName(nickName);
        return dao.nickNameDuplicateCheck(vo) > 0;
    }

    @Override
    public boolean isEmailDuplicate(String email) throws SQLException {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);
        return dao.emailDuplicateCheck(vo) > 0;
    }

}
