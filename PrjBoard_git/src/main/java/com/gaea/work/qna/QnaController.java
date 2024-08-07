package com.gaea.work.qna;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaea.work.cmn.PagingVO;
import com.gaea.work.cmn.ResultVO;
import com.gaea.work.login.SessionCheckService;
import com.gaea.work.member.MemberVO;
import com.gaea.work.reply.ReplyService;
import com.gaea.work.validation.QnaValidationService;

@Controller
@RequestMapping("qna")
public class QnaController  {
	@Autowired
	QnaService service;
	@Autowired
	ReplyService replyService;
	@Autowired
	SessionCheckService sessionService;
	@Autowired
	QnaValidationService validationService;
	@Autowired
	MessageSource messageSource;

	public QnaController() {
	}
	
	@GetMapping("/retrieveQnaArticle")
	public String retrieveQnaArticle(PagingVO pagingVO, Model model,
			@RequestParam(value = "nowPage", required = false, defaultValue = "1") int nowPage,
			@RequestParam(value = "cntPerPage", required = false, defaultValue = "10") int cntPerPage,
			@RequestParam(value = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate) throws SQLException {
		pagingVO.setNowPage(nowPage);
        pagingVO.setCntPerPage(cntPerPage);
        pagingVO.setSearchKeyword(searchKeyword);
        pagingVO.setStartDate(startDate);
        pagingVO.setEndDate(endDate);
        
        try {
		// 댓글 수 보기
			List<QnaVO> qnaList = service.retrieveQnaArticle(pagingVO);
			for (QnaVO qna : qnaList) {
				int replyCount = replyService.countReplyByBoardSeq(qna.getBoardSeq()); 
				qna.setReplyCnt(replyCount);
			}		
		    model.addAttribute("paging", pagingVO);
		    model.addAttribute("list", qnaList);
        } catch (IllegalArgumentException e) {
			model.addAttribute("errorMessage", e.getMessage());
		}
	    return "qna/qna_list";
	}

	@GetMapping(value = "/viewQnaArticleMod")
	public String viewQnaArticleMod(QnaVO inVO, Model model, HttpSession session) throws SQLException, EmptyResultDataAccessException {
		QnaVO outVO = service.viewQnaArticleDetail(inVO);
		model.addAttribute("vo", outVO);
		model.addAttribute("memberId", outVO.getMemberId());
		if (!sessionService.isSessionMatched(session, outVO.getMemberId())) {
			model.addAttribute("errorMessage",  messageSource.getMessage("login.permission.error", null, Locale.getDefault()));
			return "qna/qna_mng";
		}
		return "qna/qna_mod";
	}

	@GetMapping(value = "/viewQnaArticleReg")
	public String viewQnaArticleReg(Model model, MemberVO inVO, HttpSession session) throws SQLException {
		if (!sessionService.isLoggedIn(session)) {
			model.addAttribute("errorMessage", messageSource.getMessage("login.required", null, Locale.getDefault()));
			return "login/login";
		}
		
		model.addAttribute("paramVO", inVO);
		return "qna/qna_reg";
	}

	@PostMapping(value = "/updateQnaArticle", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultVO updateQnaArticle(QnaVO inVO) throws SQLException {
		ResultVO validationMessage = validationService.validateQna(inVO);
		if ("0".equals(validationMessage.getMsgId())) {
	        return validationMessage;
	    }
		int flag = service.updateQnaArticle(inVO);
		String message = (flag == 1) ? 
				messageSource.getMessage("update.success", null, Locale.getDefault()):
			    messageSource.getMessage("update.error", null, Locale.getDefault());
		return new ResultVO(String.valueOf(flag), message);
	}

	@GetMapping(value = "/viewQnaArticleDetail")
	public String viewQnaArticleDetail(QnaVO inVO, Model model, HttpSession session) throws SQLException {
		if (!sessionService.checkAndSetMemberId(session, inVO)) {
			model.addAttribute("errorMessage", messageSource.getMessage("login.required", null, Locale.getDefault()));
			return "login/login";
		}
		
		QnaVO outVO = service.viewQnaArticleDetail(inVO);
		model.addAttribute("vo", outVO);
		return"qna/qna_mng";
	}

	@PostMapping(value = "/saveQnaArticle", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultVO saveQnaArticle(QnaVO inVO, @RequestParam String memberId) throws SQLException {
		inVO.setMemberId(memberId);
		
		ResultVO validationResult = validationService.validateQna(inVO);
		if (validationResult != null && "0".equals(validationResult.getMsgId())) {
	        return validationResult;
	    }

		int flag = service.saveQnaArticle(inVO);
		String message = (flag == 1) ? 
				messageSource.getMessage("save.success", null, Locale.getDefault()):
				messageSource.getMessage("save.error", null, Locale.getDefault());

		return  new ResultVO(String.valueOf(flag), message);
	}

	@GetMapping(value ="/deleteQnaArticle",produces = "application/json;charset=UTF-8" )
	@ResponseBody
	public ResultVO deleteQnaArticle(QnaVO inVO, HttpSession session) throws SQLException {
		QnaVO outVO = service.viewQnaArticleDetail(inVO);
		if (!sessionService.isSessionMatched(session, outVO.getMemberId())) {
			String errorMessage = messageSource.getMessage("login.permission.error", null, Locale.getDefault());
	        return new ResultVO("0", errorMessage);
		}

		int flag = service.deleteQnaArticle(inVO);
		String message = (flag == 1) ?
				messageSource.getMessage("delete.success", null, Locale.getDefault()) :
			    messageSource.getMessage("delete.error", null, Locale.getDefault());

		return new ResultVO(String.valueOf(flag), message);
	}

}
