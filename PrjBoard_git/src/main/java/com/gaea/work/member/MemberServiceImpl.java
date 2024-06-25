package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaea.work.qna.QnaService;
import com.gaea.work.qna.QnaVO;
import com.gaea.work.reply.ReplyService;
import com.gaea.work.validation.MemberValidationService;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDao dao;
	@Autowired
	QnaService qnaService;
	@Autowired
	ReplyService replyService;
	@Autowired
    MemberValidationService validationService;
	@Autowired
    BCryptPasswordEncoder passwordEncoder;
	
	public MemberServiceImpl() {}

	@Override
	public int updateMemberInfo(MemberVO inVO) throws SQLException {
		return dao.updateMember(inVO);
	}

	@Override
	@Transactional
	public int changeMemberPassword(MemberVO inVO) throws SQLException {
		String encodedPassword = passwordEncoder.encode(inVO.getPassword());
        inVO.setPassword(encodedPassword);
		return dao.updateMemberPassword(inVO);
	}

	@Override
	@Transactional
	public int withdrawalMember(MemberVO inVO) throws SQLException {
		// 회원이 작성한 모든 게시글의 댓글 삭제
		List<QnaVO> qnaList = qnaService.getAllAtricleByMemberId(inVO.getMemberId());
		for (QnaVO qna : qnaList) {
			replyService.deleteReplyByBoardSeq(qna.getBoardSeq());
		}
		qnaService.deleteArticleByMemberId(inVO.getMemberId()); //회원 작성한 게시글 삭제
		replyService.deleteReplyByMemberId(inVO.getMemberId()); // 회원 작성한 댓글 삭제
		return dao.deleteMember(inVO);
	}

	@Override
	public MemberVO viewMemberDetail(MemberVO inVO) throws SQLException, EmptyResultDataAccessException {
		return dao.selectOneMember(inVO);
	}
	
	@Override
	@Transactional
	public int joinMember(MemberVO inVO) throws SQLException {
		validationService.validateMember(inVO);
		String encodedPassword = passwordEncoder.encode(inVO.getPassword());
        inVO.setPassword(encodedPassword);
		return dao.saveMember(inVO);
	}

	@Override
	public List<MemberVO> retrieveMember(MemberVO inVO) throws SQLException {
		return dao.retrieveMember(inVO);
	}


}
