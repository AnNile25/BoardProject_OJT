package com.gaea.work.reply;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyServiceImpl implements ReplyService {
	@Autowired
	ReplyDao dao;
	
	public ReplyServiceImpl() {	}

	@Override
	public int saveReply(ReplyVO inVO) throws SQLException {
		return dao.saveReply(inVO);
	}

	@Override
	public ReplyVO viewReplyDetail(ReplyVO inVO) throws SQLException {
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

	@Override
	public int countReplyByBoardSeq(int boardSeq) throws SQLException {
		return dao.countReplyByBoardSeq(boardSeq);
	}

	@Override
	@Transactional
	public int deleteReplyByBoardSeq(int boardSeq) throws SQLException {
		return dao.deleteReplyByBoardSeq(boardSeq);
	}

	@Override
	public int deleteReplyByMemberId(String memberId) throws SQLException {
		return dao.deleteReplyByMemberId(memberId);
	}

}
