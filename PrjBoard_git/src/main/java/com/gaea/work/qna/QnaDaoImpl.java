package com.gaea.work.qna;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.gaea.work.cmn.GLog;

@Repository
public class QnaDaoImpl implements QnaDao {
	final String NAMESPACE = "com.gaea.work.qna";
	final String DOT       = ".";
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int updateArticle(QnaVO inVO) throws SQLException {
		return sqlSessionTemplate.update(NAMESPACE+DOT+"updateArticle", inVO);
	}

	@Override
	public int deleteArticle(QnaVO inVO) throws SQLException {
		return sqlSessionTemplate.delete(NAMESPACE+DOT+"deleteArticle", inVO);
	}

	@Override
	public QnaVO selectOneArticle(QnaVO inVO) throws SQLException, EmptyResultDataAccessException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"selectOneArticle", inVO);
	}

	@Override
	public int saveArticle(QnaVO inVO) throws SQLException {
		return sqlSessionTemplate.insert(NAMESPACE+DOT+"saveArticle", inVO);
	}

	@Override
	public List<QnaVO> retrieveArticle(QnaVO inVO) throws SQLException {
		return sqlSessionTemplate.selectList(NAMESPACE+DOT+"retrieveArticle", inVO);
	}

	@Override
	public int getSeq() throws SQLException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"getSeq");
	}

	@Override
	public int deleteAllQna(QnaVO inVO) throws SQLException {
		return sqlSessionTemplate.delete(NAMESPACE+DOT+"deleteAllQna", inVO);
	}

	@Override
	public int updateReadCnt(QnaVO inVO) throws SQLException {
		return sqlSessionTemplate.update(NAMESPACE+DOT+"updateReadCnt", inVO);
	}

	@Override
	public int updateLikeCnt(QnaVO inVO) throws SQLException {
		return sqlSessionTemplate.update(NAMESPACE+DOT+"updateLikeCnt", inVO);
	}

	@Override
	public List<QnaVO> pagingList(Map<String, Integer> pagingParams) throws SQLException {
		return sqlSessionTemplate.selectList(NAMESPACE+DOT+"pagingList", pagingParams);
	}

	@Override
	public int qnaCount() throws SQLException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"qnaCount");
	}

}
