package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.gaea.work.cmn.GLog;

@Repository
public class MemberDaoImpl implements MemberDao {

	Logger LOG = LogManager.getLogger(GLog.class);

	final String NAMESPACE = "com.gaea.work.member";
	final String DOT = ".";

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public MemberDaoImpl() {
	}

	@Override
	public int updateMember(MemberVO inVO) throws SQLException {
		LOG.debug("[updateMember]statement:"+NAMESPACE+DOT+"updateMember");
		
		return sqlSessionTemplate.update(NAMESPACE+DOT+"updateMember", inVO);
	}

	@Override
	public int deleteMember(MemberVO inVO) throws SQLException {
		LOG.debug("[deleteMember]statement:"+NAMESPACE+DOT+"deleteMember");
		
		return sqlSessionTemplate.delete(NAMESPACE+DOT+"deleteMember", inVO);
	}

	@Override
	public MemberVO selectOneMember(MemberVO inVO) throws SQLException {
		LOG.debug("[selectOneMember]statement:"+NAMESPACE+DOT+"selectOneMember");
		
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"selectOneMember", inVO);
	}

	@Override
	public int saveMember(MemberVO inVO) throws SQLException {
		LOG.debug("[saveMember]statement:"+NAMESPACE+DOT+"saveMember");
		
		return sqlSessionTemplate.insert(NAMESPACE+DOT+"saveMember", inVO);
	}

	@Override
	public List<MemberVO> retrieveMember(MemberVO inVO) throws SQLException {
		LOG.debug("[retrieveMember]statement:"+NAMESPACE+DOT+"retrieveMember");		
		
		return sqlSessionTemplate.selectList(NAMESPACE+DOT+"retrieveMember", inVO);
	}

	@Override
	public int idDuplicateCheck(MemberVO inVO) throws SQLException {
		LOG.debug("[idDuplicateCheck]statement:"+NAMESPACE+DOT+"idDuplicateCheck");		
		
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"idDuplicateCheck", inVO);
	}

	@Override
	public int nickNameDuplicateCheck(MemberVO inVO) throws SQLException {
		LOG.debug("[nickNameDuplicateCheck]statement:"+NAMESPACE+DOT+"nickNameDuplicateCheck");		
		
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"nickNameDuplicateCheck", inVO);
	}

	@Override
	public int emailDuplicateCheck(MemberVO inVO) throws SQLException {
		LOG.debug("[emailDuplicateCheck]statement:"+NAMESPACE+DOT+"emailDuplicateCheck");		
		
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"emailDuplicateCheck", inVO);
	}

}
