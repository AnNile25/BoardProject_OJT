package com.gaea.work.board.dao;

import java.sql.SQLException;

import com.gaea.work.board.domain.BoardVO;
import com.gaea.work.cmn.WorkDiv;

public interface BoardDao extends WorkDiv<BoardVO> {
	
	/**
	 * board_seq 
	 * @return int
	 * @throws SQLException
	 */
	int getSeq()throws SQLException;
	
	/**
	 * 글제목으로 삭제: test only
	 * @param inVO
	 * @return int
	 * @throws SQLException
	 */
	int doDeleteAll(BoardVO inVO)throws SQLException;
	
	/**
	 * 조회건수 증가
	 * @param inVO
	 * @return int
	 * @throws SQLException
	 */
	int doUpdateReadCnt(BoardVO inVO)throws SQLException;  
	
	/**
	 * 추천수 증가
	 * @param inVO
	 * @return int
	 * @throws SQLException
	 */
	int doUpdateLikeCnt(BoardVO inVO)throws SQLException;  

}
