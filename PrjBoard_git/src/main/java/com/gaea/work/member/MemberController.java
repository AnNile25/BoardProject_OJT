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
import com.gaea.work.validation.MemberValidationService;

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
	
	@PostMapping(value = "/checkMemberIdDuplicate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO checkMemberIdDuplicate(@RequestParam String memberId) throws SQLException {
	    String validationMessage = validationService.validateField(memberId, "error.id.required", "^[a-zA-Z0-9]{1,20}$", "error.id.invalid", null);
	    if (validationMessage != null) {
	        return new SuccessMessageVO("0", messageSource.getMessage(validationMessage, null, Locale.getDefault()));
	    }

	    boolean isDuplicate = validationService.isIdDuplicate(memberId);
	    String message = isDuplicate ? "error.id.duplicate" : "success.id.available";
	    return new SuccessMessageVO(isDuplicate ? "0" : "1", messageSource.getMessage(message, null, Locale.getDefault()));
	}

	@PostMapping(value = "/checkNickNameDuplicate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO checkNickNameDuplicate(@RequestParam String nickName) throws SQLException {
	    // 유효성 검사
	    String validationMessage = validationService.validateField(nickName, "error.nickname.required", "^[가-힣a-zA-Z0-9]{1,15}$", "error.nickname.invalid", null);
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

	@GetMapping(value = "/viewJoinMember")
	public String viewJoinMember() throws SQLException {
		return "member/member_reg";
	}

	@GetMapping(value = "/personalMemberInfo")
	public String personalMemberInfo(MemberVO inVO, Model model, HttpSession session) throws SQLException {
		if (!sessionService.checkAndSetMemberId(session, inVO)) {
			model.addAttribute("errorMessage", messageSource.getMessage("error.login.required", null, Locale.getDefault()));
			return "login/login";
		}
		MemberVO outVO = service.viewMemberDetail(inVO);
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
		MemberVO outVO = service.viewMemberDetail(inVO);
		if (!sessionService.isSessionMatched(session, outVO.getMemberId())) {
			String errorMessage = messageSource.getMessage("error.permission", null, Locale.getDefault());
	        return new SuccessMessageVO("0", errorMessage);
		}

		int flag = service.withdrawalMember(inVO);
		String message;
		if (flag == 1) {
	        session.invalidate();
	        message = messageSource.getMessage("success.delete", null, Locale.getDefault());
	    } else {
	        message = messageSource.getMessage("error.delete", null, Locale.getDefault());
	    }

		return new SuccessMessageVO(String.valueOf(flag), message);
	}

	@PostMapping(value = "/updateMemberInfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO updateMemberInfo(MemberVO inVO) throws SQLException {	    
		int flag = service.updateMemberInfo(inVO);
		String message = (flag == 1) ? 
				messageSource.getMessage("success.update", null, Locale.getDefault()):
			    messageSource.getMessage("error.update", null, Locale.getDefault());
		return new SuccessMessageVO(String.valueOf(flag), message);
	}
	
	@PostMapping(value = "/joinMember", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO joinMember(MemberVO inVO) throws SQLException {
	    logger.info("회원가입 시도:" + inVO);

	    SuccessMessageVO validationResult = validationService.validateMember(inVO);
	    if (validationResult != null && "0".equals(validationResult.getMsgId())) {
	        logger.info("회원가입 실패 - 유효성 검사 실패: " + validationResult);
	        return validationResult;
	    }

	    int flag = service.joinMember(inVO);
	    String message = (flag == 1) ? 
	    		messageSource.getMessage("success.join", null, Locale.getDefault()):
				messageSource.getMessage("error.join", null, Locale.getDefault());
	    SuccessMessageVO result = new SuccessMessageVO(String.valueOf(flag), message);
	    logger.info("회원가입 결과: " + result);
	    return result;
	}

}
