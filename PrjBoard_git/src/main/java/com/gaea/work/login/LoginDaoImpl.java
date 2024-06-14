package com.gaea.work.login;

import java.sql.SQLException;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.gaea.work.member.MemberVO;

@Repository
public class LoginDaoImpl implements LoginDao {	
	final String NAMESPACE = "com.gaea.work.login";
	final String DOT       = ".";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public MemberVO selectOneMember(MemberVO inVO) throws SQLException, EmptyResultDataAccessException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"selectOneMember", inVO);
	}
	
	@Override
	public MemberVO getMemberId(String memberId) throws SQLException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"getMemberId", memberId);
	}

	@Override
	public int idCheck(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"idCheck", inVO);
	}

	@Override
	public int idPassCheck(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"idPassCheck", inVO);
	}
	
}
