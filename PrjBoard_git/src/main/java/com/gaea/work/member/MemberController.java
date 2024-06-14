package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaea.work.cmn.SuccessMessageVO;

@Controller
@RequestMapping("member")
public class MemberController {
	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	MemberService service;

	public MemberController() {
	}

	/* 중복 체크 */
	@PostMapping("/checkIdDuplicate")
	@ResponseBody
	public boolean checkIdDuplicate(@RequestParam String memberId) throws SQLException {
		return service.isIdDuplicate(memberId);
	}

	@PostMapping("/checkNickNameDuplicate")
	@ResponseBody
	public boolean checkNickNameDuplicate(@RequestParam String nickName) throws SQLException {
		return service.isNickNameDuplicate(nickName);
	}

	@PostMapping("/checkEmailDuplicate")
	@ResponseBody
	public boolean checkEmailDuplicate(@RequestParam String email) throws SQLException {
		return service.isEmailDuplicate(email);
	}

	/* 회원 수정 페이지 이동 */
	@GetMapping(value = "/moveToMod")
	public String moveToMod(MemberVO inVO, HttpSession session, Model model) throws SQLException {
		String sessionMemberId = (String) session.getAttribute("memberId");		
		if (sessionMemberId == null || !sessionMemberId.equals( inVO.getMemberId())) {
			return "cmn/userError";
		}

		MemberVO outVO = service.selectOneMember(inVO);		
		model.addAttribute("vo", outVO);
		
		return "member/member_mod";
	}

	@GetMapping(value = "/moveToReg")
	public String moveToReg() throws SQLException {
		return "member/member_reg";
	}

	@GetMapping(value = "/personalMemberInfo")
	public String personalMemberInfo(MemberVO inVO, Model model) throws SQLException {
		MemberVO outVO = service.selectOneMember(inVO);
		model.addAttribute("vo", outVO);

		return "member/member_mng";
	}

	@GetMapping(value = "/retrieveMember")
	public String retrieveMember(MemberVO inVO, Model model) throws SQLException {
		List<MemberVO> list = service.retrieveMember(inVO);

		model.addAttribute("list", list);
		model.addAttribute("paramVO", inVO);

		return "member/member_list";
	}

	@GetMapping(value = "/withdrawalMember", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO withdrawalMember(MemberVO inVO, HttpSession session) throws SQLException {
		String sessionMemberId = (String) session.getAttribute("memberId");
		if (sessionMemberId == null || !sessionMemberId.equals( inVO.getMemberId())) {
			return new SuccessMessageVO("0", "삭제 권한이 없습니다.");
		}
		
		int flag = service.withdrawalMember(inVO);
		String message = (flag == 1) ? "삭제 되었습니다." : "삭제 실패.";
		SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);
		return messageVO;
	}

	@PostMapping(value = "/updateMember", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO updateMember(MemberVO inVO) throws SQLException {
		int flag = service.updateMember(inVO);

		String message = (flag == 1) ? "수정 되었습니다." : "수정 실패.";
		SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);
		return messageVO;
	}

	@PostMapping(value = "/joinMember", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO joinMember(MemberVO inVO) throws SQLException {
		logger.info("회원가입 시도:" + inVO);
		int flag = service.joinMember(inVO);

		String message = (flag == 1) ? "가입 되었습니다." : "가입 실패.";
		SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);
		logger.info("회원가입 결과: " + messageVO);

		return messageVO;
	}

}
