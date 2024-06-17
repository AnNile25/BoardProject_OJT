package com.gaea.work.qna;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

public interface QnaService {
	
	void deleteOldQna();
	
	public PageVO pagingParam(int page) throws SQLException;
	
	public List<QnaVO> pagingList(int page) throws SQLException;
	
	public QnaVO moveToMod(QnaVO inVO) throws SQLException, EmptyResultDataAccessException;
	
	// 기본 CRUD	
	public int updateQnaArticle(QnaVO inVO) throws SQLException;
	 
	public int deleteQnaArticle(QnaVO inVO) throws SQLException;
	 
	public QnaVO selectOneQnaArticle(QnaVO inVO) throws SQLException, EmptyResultDataAccessException;
	 
	public int saveQnaArticle(QnaVO inVO) throws SQLException;
	 
	public List<QnaVO> retrieveQnaArticle(QnaVO inVO) throws SQLException;

}
