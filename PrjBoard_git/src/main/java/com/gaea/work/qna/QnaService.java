package com.gaea.work.qna;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.gaea.work.cmn.PagingVO;

public interface QnaService {
	void deleteOldQnaArticle();
	
	public QnaVO viewQnaArticleMod(QnaVO inVO) throws SQLException, EmptyResultDataAccessException;
	
	// CRUD	
	public int updateQnaArticle(QnaVO inVO) throws SQLException;	 
	public int deleteQnaArticle(QnaVO inVO) throws SQLException;	 
	public QnaVO viewQnaArticleDetail(QnaVO inVO) throws SQLException, EmptyResultDataAccessException;	 
	public int saveQnaArticle(QnaVO inVO) throws SQLException;	 
	public List<QnaVO> retrieveQnaArticle(PagingVO pagingVO) throws SQLException;

}
