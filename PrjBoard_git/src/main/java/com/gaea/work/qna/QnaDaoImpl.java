package com.gaea.work.qna;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.gaea.work.cmn.PagingVO;

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
	public List<QnaVO> retrieveArticle(PagingVO pagingVO) throws SQLException {
		return sqlSessionTemplate.selectList(NAMESPACE+DOT+"retrieveArticle", pagingVO);
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
	public int qnaCount(PagingVO pagingVO) throws SQLException {
		return sqlSessionTemplate.selectOne(NAMESPACE+DOT+"qnaCount", pagingVO);
	}

	@Override
	public int deleteByCreatedDateBefore(Date date) {
		return sqlSessionTemplate.delete(NAMESPACE+DOT+"deleteByCreatedDateBefore", date);
		
	}

	@Override
	public int deleteArticleByMemberId(String memerId) {
		return sqlSessionTemplate.delete(NAMESPACE+DOT+"deleteArticleByMemberId", memerId);
	}

	@Override
	public List<QnaVO> getAllAtricleByMemberId(String memberId) throws SQLException {
		return sqlSessionTemplate.selectList(NAMESPACE+DOT+"getAllAtricleByMemberId", memberId);
	}

}
