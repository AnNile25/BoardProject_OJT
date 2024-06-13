package com.gaea.work.login;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.gaea.work.cmn.GLog;
import com.gaea.work.member.MemberVO;

@Repository
public class LoginDaoImpl implements LoginDao {
	
	Logger LOG = LogManager.getLogger(GLog.class);
	
	final String NAMESPACE = "com.gaea.work.login";
	final String DOT       = ".";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public MemberVO selectOneMember(MemberVO inVO) throws SQLException, EmptyResultDataAccessException {
		MemberVO outVO = null;
		String statement = NAMESPACE + DOT + "selectOneMember";
		outVO = sqlSessionTemplate.selectOne(statement, inVO);
		if(null != outVO) {
			LOG.debug("outVO\n" + outVO.toString());
		}
		return outVO;
	}
	
	@Override
	public MemberVO getMemberId(String memberId) throws SQLException {
		String statement = NAMESPACE + DOT + "getMemberId";
		MemberVO member = sqlSessionTemplate.selectOne(statement, memberId);
		return member;
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
