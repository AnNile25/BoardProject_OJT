package com.gaea.work.qna;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;

import com.gaea.work.cmn.PagingVO;

public interface QnaDao {
	List<QnaVO> getAllAtricleByMemberId(String memberId) throws SQLException;
	int deleteArticleByMemberId(String memerId);	
	int deleteByCreatedDateBefore(Date date);	
	int qnaCount(PagingVO pagingVO) throws SQLException;
	int getSeq()throws SQLException;
	
	List<QnaVO> pagingList(Map<String, Integer> pagingParams) throws SQLException;	
	
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
	List<QnaVO> retrieveArticle(PagingVO pagingVO) throws SQLException;

}
