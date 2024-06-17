package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaea.work.cmn.SuccessMessageVO;
import com.gaea.work.login.SessionCheckService;

@Controller
@RequestMapping("member")
public class MemberController {
	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	MemberService service;
	
	@Autowired
    MemberValidationService validationService;
	
	@Autowired
	SessionCheckService sessionService;
	
	@Autowired
    MessageSource messageSource;

	public MemberController() {
	}
	
	@PostMapping(value = "/checkIdDuplicate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO checkIdDuplicate(@RequestParam String memberId) throws SQLException {
	    // 유효성 검사
	    String validationMessage = validationService.validateField(memberId, "error.id.required", "^[a-zA-Z0-9]*$", "error.id.invalid", null);
	    if (validationMessage != null) {
	        return new SuccessMessageVO("0", messageSource.getMessage(validationMessage, null, Locale.getDefault()));
	    }

	    // 중복 체크
	    boolean isDuplicate = validationService.isIdDuplicate(memberId);
	    String message = isDuplicate ? "error.id.duplicate" : "success.id.available";
	    return new SuccessMessageVO(isDuplicate ? "0" : "1", messageSource.getMessage(message, null, Locale.getDefault()));
	}

	@PostMapping(value = "/checkNickNameDuplicate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO checkNickNameDuplicate(@RequestParam String nickName) throws SQLException {
	    // 유효성 검사
	    String validationMessage = validationService.validateField(nickName, "error.nickname.required", "^[가-힣a-zA-Z0-9]*$", "error.nickname.invalid", null);
	    if (validationMessage != null) {
	        return new SuccessMessageVO("0", messageSource.getMessage(validationMessage, null, Locale.getDefault()));
	    }

	    // 중복 체크
	    boolean isDuplicate = validationService.isNickNameDuplicate(nickName);
	    String message = isDuplicate ? "error.nickname.duplicate" : "success.nickname.available";
	    return new SuccessMessageVO(isDuplicate ? "0" : "1", messageSource.getMessage(message, null, Locale.getDefault()));
	}

	@PostMapping(value = "/checkEmailDuplicate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO checkEmailDuplicate(@RequestParam String email) throws SQLException {
	    // 유효성 검사
	    String validationMessage = validationService.validateField(email, "error.email.required", null, null, null);
	    if (validationMessage != null) {
	        return new SuccessMessageVO("0", messageSource.getMessage(validationMessage, null, Locale.getDefault()));
	    }

	    // 중복 체크
	    boolean isDuplicate = validationService.isEmailDuplicate(email);
	    String message = isDuplicate ? "error.email.duplicate" : "success.email.available";
	    return new SuccessMessageVO(isDuplicate ? "0" : "1", messageSource.getMessage(message, null, Locale.getDefault()));
	}

	/* 회원 수정 페이지 이동 */
	@GetMapping(value = "/moveToMod")
	public String moveToMod(MemberVO inVO, HttpSession session, Model model) throws SQLException {
		MemberVO outVO = service.selectOneMember(inVO);
		model.addAttribute("vo", outVO);
		model.addAttribute("memberId", outVO.getMemberId());
		if (!sessionService.isSessionMatched(session, outVO.getMemberId())) {
			model.addAttribute("errorMessage",  messageSource.getMessage("error.permission", null, Locale.getDefault()));
			return "member/member_mng";
		}
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
		MemberVO outVO = service.selectOneMember(inVO);
		if (!sessionService.isSessionMatched(session, outVO.getMemberId())) {
			String errorMessage = messageSource.getMessage("error.permission", null, Locale.getDefault());
	        return new SuccessMessageVO("0", errorMessage);
		}

		int flag = service.withdrawalMember(inVO);
		String message = (flag == 1) ?
				messageSource.getMessage("success.delete", null, Locale.getDefault()) :
			    messageSource.getMessage("error.delete", null, Locale.getDefault());

		return new SuccessMessageVO(String.valueOf(flag), message);
//		String sessionMemberId = (String) session.getAttribute("memberId");
//		if (sessionMemberId == null || !sessionMemberId.equals( inVO.getMemberId())) {
//			return new SuccessMessageVO("0", "삭제 권한이 없습니다.");
//		}
//		
//		int flag = service.withdrawalMember(inVO);
//		String message = (flag == 1) ? "삭제 되었습니다." : "삭제 실패.";
//		SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);
//		return messageVO;
	}

	@PostMapping(value = "/updateMember", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO updateMember(MemberVO inVO) throws SQLException {
		int flag = service.updateMember(inVO);
		String message = (flag == 1) ? 
				messageSource.getMessage("success.update", null, Locale.getDefault()):
			    messageSource.getMessage("error.update", null, Locale.getDefault());
		return new SuccessMessageVO(String.valueOf(flag), message);
	}
	
	@PostMapping(value = "/joinMember", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String joinMember(MemberVO inVO) throws SQLException {
	    logger.info("회원가입 시도:" + inVO);

	    // Validation
	    String validationResult = validationService.validateMember(inVO);
	    if (!validationResult.contains("\"msgId\":\"1\"")) {
	        logger.info("회원가입 실패 - 유효성 검사 실패: " + validationResult);
	        return validationResult;
	    }

	    int flag = service.joinMember(inVO);
	    String message = (flag == 1) ? "success.join" : "error.join";
	    String result = validationService.createJsonMessage(String.valueOf(flag), messageSource.getMessage(message, null, null));

	    logger.info("회원가입 결과: " + result);
	    return result;
	}

}
