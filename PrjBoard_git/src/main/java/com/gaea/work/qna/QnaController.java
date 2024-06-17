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

import com.gaea.work.cmn.SuccessMessageVO;
import com.gaea.work.login.SessionCheckService;
import com.gaea.work.member.MemberVO;

@Controller
@RequestMapping("qna")
public class QnaController  {

	@Autowired
	QnaService service;
	
	@Autowired
	SessionCheckService sessionService;
	
	@Autowired
	MessageSource messageSource;

	public QnaController() {
	}
	
	@GetMapping("/retrieveQnaArticle")
	public String retrieveQnaArticle(QnaVO inVO, @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model) throws SQLException {
	    List<QnaVO> list = service.pagingList(page);
	    PageVO pageVO = service.pagingParam(page);

	    model.addAttribute("list", list);
	    model.addAttribute("paging", pageVO);
	    model.addAttribute("paramVO", inVO);

	    return "qna/qna_list";
	}

	@GetMapping(value = "/moveToMod")
	public String moveToMod(QnaVO inVO, Model model, HttpSession session) throws SQLException, EmptyResultDataAccessException {
		QnaVO outVO = service.selectOneQnaArticle(inVO);
		model.addAttribute("vo", outVO);
		model.addAttribute("memberId", outVO.getMemberId());
		if (!sessionService.isSessionMatched(session, outVO.getMemberId())) {
			model.addAttribute("errorMessage",  messageSource.getMessage("error.permission", null, Locale.getDefault()));
			return "qna/qna_mng";
		}
		return "qna/qna_mod";
	}

	@GetMapping(value = "/moveToReg")
	public String moveToReg(Model model, MemberVO inVO, HttpSession session) throws SQLException {
		if (!sessionService.isLoggedIn(session)) {
			model.addAttribute("errorMessage", messageSource.getMessage("error.login.required", null, Locale.getDefault()));
			return "login/login";
		}
		
		model.addAttribute("paramVO", inVO);
		return "qna/qna_reg";
	}

	@PostMapping(value = "/updateQnaArticle", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO updateQnaArticle(QnaVO inVO, HttpSession sesion) throws SQLException {
		int flag = service.updateQnaArticle(inVO);
		String message = (flag == 1) ? 
				messageSource.getMessage("success.update", null, Locale.getDefault()):
			    messageSource.getMessage("error.update", null, Locale.getDefault());
		return new SuccessMessageVO(String.valueOf(flag), message);
	}

	@GetMapping(value = "/selectOneQna")
	public String selectOneQna(QnaVO inVO, Model model, HttpSession session) throws SQLException {
		if (!sessionService.checkAndSetMemberId(session, inVO)) {
			model.addAttribute("errorMessage", messageSource.getMessage("error.login.required", null, Locale.getDefault()));
			return "login/login";
		}
		
		QnaVO outVO = service.selectOneQnaArticle(inVO);
		model.addAttribute("vo", outVO);
		return"qna/qna_mng";
	}

	@PostMapping(value = "/saveQnaArticle", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO saveQnaArticle(QnaVO inVO, @RequestParam String memberId) throws SQLException {
		inVO.setMemberId(memberId);

		int flag = service.saveQnaArticle(inVO);
		String message = (flag == 1) ? 
				messageSource.getMessage("success.save", null, Locale.getDefault()):
				messageSource.getMessage("error.save", null, Locale.getDefault());

		return  new SuccessMessageVO(String.valueOf(flag), message);
	}

	@GetMapping(value ="/deleteQnaArticle",produces = "application/json;charset=UTF-8" )
	@ResponseBody
	public SuccessMessageVO deleteQnaArticle(QnaVO inVO, HttpSession session) throws SQLException {
		QnaVO outVO = service.selectOneQnaArticle(inVO);
		if (!sessionService.isSessionMatched(session, outVO.getMemberId())) {
			String errorMessage = messageSource.getMessage("error.permission", null, Locale.getDefault());
	        return new SuccessMessageVO("0", errorMessage);
		}

		int flag = service.deleteQnaArticle(inVO);
		String message = (flag == 1) ?
				messageSource.getMessage("success.delete", null, Locale.getDefault()) :
			    messageSource.getMessage("error.delete", null, Locale.getDefault());

		return new SuccessMessageVO(String.valueOf(flag), message);
	}

}
