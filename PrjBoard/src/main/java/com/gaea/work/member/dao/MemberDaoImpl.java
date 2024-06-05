package com.gaea.work.member.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.gaea.work.cmn.GLog;
import com.gaea.work.member.domain.MemberVO;

public class MemberDaoImpl implements MemberDao, GLog {
	
	final String NAMESPACE = "com.gaea.work.member";
	final String DOT       = ".";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public MemberDaoImpl() {}

	@Override
	public int doUpdate(MemberVO inVO) throws SQLException {
		int flag = 0;

		LOG.debug("1.param \n" + inVO.toString());
		String statement = NAMESPACE+DOT+"doUpdate";
		
		LOG.debug("2.statement \n" + statement);
		flag = this.sqlSessionTemplate.update(statement, inVO);
		
		LOG.debug("3.flag \n" + flag);
		return flag;
	}

	@Override
	public int doDelete(MemberVO inVO) throws SQLException {
		int flag = 0;

		LOG.debug("1.param \n" + inVO.toString());
		String statement = this.NAMESPACE+this.DOT+"doDelete";
		
		LOG.debug("2.statement \n" + statement);
		flag = this.sqlSessionTemplate.delete(statement, inVO);
		
		LOG.debug("3.flag \n" + flag);		
		return flag;
	}

	@Override
	public MemberVO doSelectOne(MemberVO inVO) throws SQLException, EmptyResultDataAccessException {
		MemberVO outVO = null;
		
		LOG.debug("1.param \n" + inVO.toString());
		String statement = NAMESPACE+DOT+"doSelectOne";
		
		LOG.debug("2.statement \n" + statement);		
		outVO= this.sqlSessionTemplate.selectOne(statement, inVO);
		
		if(null != outVO) {
			LOG.debug("3.outVO \n" + outVO.toString());
		}
		
		return outVO;
	}

	@Override
	public int doSave(MemberVO inVO) throws SQLException {
		int flag = 0;
		
		LOG.debug("1.param \n" + inVO.toString());		
		String statement = this.NAMESPACE+DOT+"doSave";
		
		LOG.debug("2.statement \n" + statement);
		flag = this.sqlSessionTemplate.insert(statement, inVO);
		
		LOG.debug("3.flag \n" + flag);		
		return flag;
	}

	@Override
	public List<MemberVO> doRetrieve(MemberVO inVO) throws SQLException {
		List<MemberVO> outList=new ArrayList<MemberVO>();
		
		LOG.debug("1.param \n" + inVO.toString());
		String statement = NAMESPACE+DOT +"doRetrieve";
		
		LOG.debug("2.statement \n" + statement);		
		outList=this.sqlSessionTemplate.selectList(statement, inVO);
		
		for(MemberVO vo :outList) {
			LOG.debug(vo);
		}		
		return outList;
	}

	@Override
	public List<MemberVO> getAll(MemberVO inVO) throws SQLException {
		List<MemberVO> outList=new ArrayList<MemberVO>();
		LOG.debug("1.param \n" + inVO.toString());
				
		String statement = NAMESPACE+DOT +"getAll";
		LOG.debug("2.statement \n" + statement);
		
		outList=this.sqlSessionTemplate.selectList(statement, inVO);
		
		for(MemberVO vo :outList) {
			LOG.debug(vo);
		}
		return outList;
	}

	@Override
	public int getCount(MemberVO inVO) throws SQLException {
		int count = 0;

		LOG.debug("1.param \n" + inVO.toString());
		String statement = NAMESPACE+DOT+"getCount";
		
		LOG.debug("2.statement \n" + statement);		
		count=this.sqlSessionTemplate.selectOne(statement, inVO);
		
		LOG.debug("3.count \n" + count);		
		
		return count;
	}

	@Override
	public int idDuplicateCheck(MemberVO inVO) throws SQLException {
		int flag = 0;
		LOG.debug("1.param :" + inVO.toString());
		
		flag = sqlSessionTemplate.selectOne(NAMESPACE+DOT+"idDuplicateCheck", inVO);
		LOG.debug("2.flag :" + flag);
		return flag;
	}

}
