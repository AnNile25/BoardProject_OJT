package com.gaea.work.board.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gaea.work.board.dao.BoardDao;
import com.gaea.work.board.domain.BoardVO;
import com.gaea.work.cmn.GLog;

@Service
public class BoardServiceImpl implements BoardService, GLog {
	
	@Autowired
	BoardDao dao;
	
	public BoardServiceImpl() {}

	@Override
	public int doUpdate(BoardVO inVO) throws SQLException {
		return dao.doUpdate(inVO);
	}

	@Override
	public int doDelete(BoardVO inVO) throws SQLException {
		return dao.doDelete(inVO);
	}

	@Override
	public BoardVO doSelectOne(BoardVO inVO) throws SQLException, EmptyResultDataAccessException {
		//1. 단건조회
		BoardVO outVO = dao.doSelectOne(inVO);
		
		//2. 조회count증가
		if(null != outVO) {
			int doUpdateReadCnt = dao.doUpdateReadCnt(inVO);
			LOG.debug("┌───────────────────────────────────┐");
			LOG.debug("│ doUpdateReadCnt                   │"+doUpdateReadCnt);
			LOG.debug("└───────────────────────────────────┘");				
		}
		
		return outVO;
	}

	@Override
	public int doSave(BoardVO inVO) throws SQLException {
		return dao.doSave(inVO);
	}

	@Override
	public List<BoardVO> doRetrieve(BoardVO inVO) throws SQLException {
		return dao.doRetrieve(inVO);
	}

	@Override
	public int doUpdateLikeCnt(BoardVO inVO) throws SQLException {
		return dao.doUpdateLikeCnt(inVO);
	}
	
	@Override
	public int getSeq() throws SQLException {
		return dao.getSeq();
	}

	@Override
	public BoardVO moveToMod(BoardVO inVO) throws SQLException, EmptyResultDataAccessException {
		return dao.doSelectOne(inVO);
	}

}
