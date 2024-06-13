package com.gaea.work.reply;

import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gaea.work.cmn.GLog;

@Service
public class ReplyServiceImpl implements ReplyService {	
	Logger LOG = LogManager.getLogger(GLog.class);
	
	@Autowired
	ReplyDao dao;
	
	public ReplyServiceImpl() {	}

	@Override
	public int updateLikeCnt(ReplyVO inVO) throws SQLException {
		return dao.updateLikeCnt(inVO);
	}

	@Override
	public int saveReply(ReplyVO inVO) throws SQLException {
		return dao.saveReply(inVO);
	}

	@Override
	public ReplyVO selectOneReply(ReplyVO inVO) throws SQLException, EmptyResultDataAccessException {
		return dao.selectOneReply(inVO);
	}

	@Override
	public List<ReplyVO> retrieveReply(ReplyVO inVO) throws SQLException {
		return dao.retrieveReply(inVO);
	}

	@Override
	public int updateReply(ReplyVO inVO) throws SQLException {
		return dao.updateReply(inVO);
	}

	@Override
	public int deleteReply(ReplyVO inVO) throws SQLException {
		return dao.deleteReply(inVO);
	}

}
