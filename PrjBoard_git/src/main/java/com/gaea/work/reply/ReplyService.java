package com.gaea.work.reply;

import java.sql.SQLException;
import java.util.List;

public interface ReplyService {
	int deleteReplyByMemberId(String memberId) throws SQLException;
	int deleteReplyByBoardSeq(int boardSeq) throws SQLException;
	int countReplyByBoardSeq(int boardSeq) throws SQLException;
	
	int saveReply(ReplyVO inVO) throws SQLException;
	ReplyVO viewReplyDetail(ReplyVO inVO) throws SQLException;
	List<ReplyVO> retrieveReply(ReplyVO inVO) throws SQLException;
	int updateReply(ReplyVO inVO) throws SQLException;
	int deleteReply(ReplyVO inVO) throws SQLException;

}
