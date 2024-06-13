package com.gaea.work.reply;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

public interface ReplyService {
	
int updateLikeCnt(ReplyVO inVO) throws SQLException;
	
	int saveReply(ReplyVO inVO) throws SQLException;
	ReplyVO selectOneReply(ReplyVO inVO) throws SQLException, EmptyResultDataAccessException;
	List<ReplyVO> retrieveReply(ReplyVO inVO) throws SQLException;
	int updateReply(ReplyVO inVO) throws SQLException;
	int deleteReply(ReplyVO inVO) throws SQLException;

}
