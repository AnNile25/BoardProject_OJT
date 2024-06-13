package com.gaea.work.qna;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;

public interface QnaDao {
	
	int qnaCount() throws SQLException;
	
	List<QnaVO> pagingList(Map<String, Integer> pagingParams) throws SQLException;
	
	int getSeq()throws SQLException;
	
	/**
	 * 글제목으로 삭제: test only
	 * @param inVO
	 * @return int
	 * @throws SQLException
	 */
	int deleteAllQna(QnaVO inVO)throws SQLException;
	
	int updateReadCnt(QnaVO inVO)throws SQLException;  
	
	int updateLikeCnt(QnaVO inVO)throws SQLException;
	
	// 기본 CRUD	
	int updateArticle(QnaVO inVO) throws SQLException;
	
	int deleteArticle(QnaVO inVO) throws SQLException;
	
	QnaVO selectOneArticle(QnaVO inVO) throws SQLException, EmptyResultDataAccessException;
	
	int saveArticle(QnaVO inVO) throws SQLException;
	
	List<QnaVO> retrieveArticle(QnaVO inVO) throws SQLException;

}
