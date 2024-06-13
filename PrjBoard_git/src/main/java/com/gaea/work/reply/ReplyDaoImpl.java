package com.gaea.work.reply;

import java.sql.SQLException;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDaoImpl implements ReplyDao {
	final String NAMESPACE = "com.gaea.work.reply.";
	
	@Autowired
	SqlSessionTemplate template;

	@Override
	public int updateLikeCnt(ReplyVO inVO) throws SQLException {
		return template.update(NAMESPACE+"updateLikeCnt", inVO);
	}

	@Override
	public int saveReply(ReplyVO inVO) throws SQLException {
		return template.insert(NAMESPACE+"saveReply", inVO);
	}

	@Override
	public ReplyVO selectOneReply(ReplyVO inVO) throws SQLException, EmptyResultDataAccessException {
		return template.selectOne(NAMESPACE+"selectOneReply", inVO);
	}

	@Override
	public List<ReplyVO> retrieveReply(ReplyVO inVO) throws SQLException {
		return template.selectList(NAMESPACE+"retrieveReply", inVO);
	}

	@Override
	public int updateReply(ReplyVO inVO) throws SQLException {
		return template.update(NAMESPACE+"updateReply", inVO);
	}

	@Override
	public int deleteReply(ReplyVO inVO) throws SQLException {
		return template.delete(NAMESPACE+"deleteReply", inVO);
	}

}
