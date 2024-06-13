package com.gaea.work.qna;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaea.work.cmn.GLog;
import com.gaea.work.cmn.StrReplace;
import com.gaea.work.cmn.SuccessMessageVO;
import com.gaea.work.member.MemberVO;

@Controller
@RequestMapping("qna")
public class QnaController implements GLog {

	@Autowired
	QnaService service;

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
	public String moveToMod(QnaVO inVO, Model model) throws SQLException, EmptyResultDataAccessException {
		LOG.debug("[moveToMod]QnaVO:" + inVO);

		if (0 == inVO.getBoardSeq()) {
			throw new NullPointerException("게시물을 확인하세요");
		}

		QnaVO outVO = service.selectOneQnaArticle(inVO);
		model.addAttribute("vo", outVO);
		model.addAttribute("memberId", outVO.getMemberId());
		return "qna/qna_mod";
	}

	@GetMapping(value = "/moveToReg")
	public String moveToReg(Model model, MemberVO inVO) throws SQLException {
		LOG.debug("[moveToReg]");

		model.addAttribute("paramVO", inVO);
		return "qna/qna_reg";
	}

	@PostMapping(value = "/updateQnaArticle", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO updateQnaArticle(QnaVO inVO, Model model) throws SQLException {
		LOG.debug("[updateQnaArticle]QnaVO:" + inVO);

		int flag = service.updateQnaArticle(inVO);
		String message = (flag == 1) ? "수정 되었습니다." : "수정 실패.";

		SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);
		return messageVO;
	}

	@GetMapping(value = "/selectOneQna")
	public String selectOneQna(QnaVO inVO, Model model, HttpSession session) throws SQLException {
		String view = "qna/qna_mng";

		if (0 == inVO.getBoardSeq()) {
			model.addAttribute("errorMessage", "게시글을 조회할 수 없습니다.");
			return "cmn/userError";
		}

		if (null == inVO.getMemberId()) {
			inVO.setMemberId(StrReplace.nvl(inVO.getMemberId(), "Guest"));
		}

		// session이 있는 경우
		if (null != session.getAttribute("member")) {
			MemberVO member = (MemberVO) session.getAttribute("member");
			inVO.setMemberId(member.getMemberId());
		}

		QnaVO outVO = service.selectOneQnaArticle(inVO);
		model.addAttribute("vo", outVO);

		return view;
	}

	@PostMapping(value = "/saveQnaArticle", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO saveQnaArticle(QnaVO inVO,
			@RequestParam(value = "memberId", required = false) String memberId) throws SQLException {
		LOG.debug("[saveQnaArticle]QnaVO:" + inVO);
		inVO.setMemberId(memberId);

		int flag = service.saveQnaArticle(inVO);
		String message = (flag == 1) ? "등록 되었습니다." : "등록 실패.";

		SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);
		return messageVO;
	}

	@GetMapping(value ="/deleteQnaArticle",produces = "application/json;charset=UTF-8" )
	@ResponseBody
	public SuccessMessageVO deleteQnaArticle(QnaVO inVO, HttpSession session) throws SQLException {
		LOG.debug("[deleteQnaArticle]QnaVO:" + inVO);
		
		MemberVO member = (MemberVO) session.getAttribute("member");
		LOG.debug("member:" + member);
		
		if (null == member)	{		
			SuccessMessageVO messageVO = new SuccessMessageVO("0", "로그인 후 이용 가능합니다.");
			return messageVO;
		}

		int flag = service.deleteQnaArticle(inVO);
		String message = (flag == 1) ? "삭제 되었습니다." : "삭제 실패.";

		SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);
		return messageVO;
	}

}
