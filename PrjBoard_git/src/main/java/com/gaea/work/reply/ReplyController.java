package com.gaea.work.reply;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaea.work.cmn.SuccessMessageVO;
import com.gaea.work.login.SessionCheckService;

@Controller
@RequestMapping("reply")
public class ReplyController {
	@Autowired
	ReplyService service;	
	@Autowired
	SessionCheckService sessionService;
	@Autowired
	MessageSource messageSource;

	public ReplyController() {
	}
	
	@PostMapping("/updateReply")
	@ResponseBody
	public SuccessMessageVO updateReply(ReplyVO inVO, HttpSession session) throws SQLException {
		ReplyVO outVO = service.viewReplyDetail(inVO);
		if(!sessionService.isSessionMatched(session, outVO.getMemberId())) {
			String errorMessage = messageSource.getMessage("login.permission.error", null, Locale.getDefault());
	        return new SuccessMessageVO("0", errorMessage);
		}
		int flag = service.updateReply(inVO);
		String message = (flag == 1) ?
				messageSource.getMessage("update.success", null, Locale.getDefault()) :
				messageSource.getMessage("update.error", null, Locale.getDefault());
		return new SuccessMessageVO(String.valueOf(flag), message);
	}
	
	@GetMapping("/deleteReply")
	@ResponseBody
	public SuccessMessageVO deleteReply(ReplyVO inVO, HttpSession session)  throws SQLException {
		ReplyVO outVO = service.viewReplyDetail(inVO);
		if(!sessionService.isSessionMatched(session, outVO.getMemberId())) {
			String errorMessage = messageSource.getMessage("login.permission.error", null, Locale.getDefault());
	        return new SuccessMessageVO("0", errorMessage);
		}
		int flag = service.deleteReply(inVO);
		String message = (flag == 1) ?
				messageSource.getMessage("delete.success", null, Locale.getDefault()) :
				messageSource.getMessage("delete.error", null, Locale.getDefault());
		return new SuccessMessageVO(String.valueOf(flag), message);
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
		inVO.setMemberId(session.getAttribute("memberId").toString());
		int flag = service.saveReply(inVO);
		String message = (flag == 1) ? 
				messageSource.getMessage("save.success", null, Locale.getDefault()):
				messageSource.getMessage("save.error", null, Locale.getDefault());

		return  new SuccessMessageVO(String.valueOf(flag), message);
	}

}
