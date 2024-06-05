package com.gaea.work.board.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.gaea.work.board.domain.BoardVO;

public interface BoardService {
	
	public BoardVO moveToMod(BoardVO inVO) throws SQLException, EmptyResultDataAccessException;
	
	// board_seq 조회
	public int getSeq()throws SQLException;
	
	public int doUpdate(BoardVO inVO) throws SQLException;
	
	public int doDelete(BoardVO inVO) throws SQLException;
	
	// 단건 조회
	public BoardVO doSelectOne(BoardVO inVO) throws SQLException, EmptyResultDataAccessException;
	
	public int doSave(BoardVO inVO) throws SQLException;
	
	public int doUpdateLikeCnt(BoardVO inVO) throws SQLException;
	
	public List<BoardVO> doRetrieve(BoardVO inVO) throws SQLException;

}
