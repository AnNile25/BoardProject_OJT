package com.gaea.work.reply;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaea.work.cmn.SuccessMessageVO;
import com.gaea.work.member.MemberVO;

@Controller
@RequestMapping("reply")
public class ReplyController {

	@Autowired
	ReplyService service;

	@Autowired
	MessageSource messageSource;

	public ReplyController() {
	}

	@GetMapping(value = "/retrieveReply", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<ReplyVO> retrieveReply(ReplyVO inVO) throws SQLException {
		List<ReplyVO> list = new ArrayList<ReplyVO>();
		list = service.retrieveReply(inVO);
		return list;
	}

	@PostMapping(value = "/saveReply", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO saveReply(ReplyVO inVO, HttpSession session) throws SQLException {
		MemberVO member = (MemberVO) session.getAttribute("member");
		if (null == member) {
			return new SuccessMessageVO(String.valueOf("3"), "로그인 후 가능합니다.");
		}
		if ( null != member) {
			inVO.setMemberId(member.getMemberId());
		}		
		int flag = service.saveReply(inVO);
		
		String message = (flag == 1) ? "등록 되었습니다." : "등록 실패.";
		SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);
		return messageVO;
	}

}
