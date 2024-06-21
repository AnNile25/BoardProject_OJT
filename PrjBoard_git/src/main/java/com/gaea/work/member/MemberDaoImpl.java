package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDaoImpl implements MemberDao {
	final String NAMESPACE = "com.gaea.work.member";
	final String DOT = ".";

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public MemberDaoImpl() {
	}

	@Override
	public int updateMember(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.update(NAMESPACE+DOT+"updateMember", inVO);
	}
	
	@Override
	public int updateMemberPassword(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.update(NAMESPACE+DOT+"updateMemberPassword", inVO);
	}

	@Override
	public int deleteMember(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.delete(NAMESPACE+DOT+"deleteMember", inVO);
	}

	@Override
	public MemberVO selectOneMember(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"selectOneMember", inVO);
	}

	@Override
	public int saveMember(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.insert(NAMESPACE+DOT+"saveMember", inVO);
	}

	@Override
	public List<MemberVO> retrieveMember(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.selectList(NAMESPACE+DOT+"retrieveMember", inVO);
	}

	@Override
	public int idDuplicateCheck(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"idDuplicateCheck", inVO);
	}

	@Override
	public int nickNameDuplicateCheck(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"nickNameDuplicateCheck", inVO);
	}

	@Override
	public int emailDuplicateCheck(MemberVO inVO) throws SQLException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"emailDuplicateCheck", inVO);
	}

}
