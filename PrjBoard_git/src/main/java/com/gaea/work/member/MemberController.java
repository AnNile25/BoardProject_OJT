package com.gaea.work.member;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.gaea.work.api.AddrApiUtil;
import com.gaea.work.cmn.ResultVO;
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
	
	@PostMapping("/getAddrApi")
    public void getAddrApi(HttpServletRequest req, HttpServletResponse response) {
		try {
			String callback = req.getParameter("callback");
			String countPerPage = req.getParameter("countPerPage");
			String keyword = req.getParameter("keyword");
			
			AddrApiUtil addrApiUtil = new AddrApiUtil();
			String addrResult = addrApiUtil.fetchAddrApiData(countPerPage, keyword);
			
			String jsonResponse = callback + "(" + addrResult + ")";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonResponse);
			
		} catch (IOException e) {
			 logger.error("Failed to process the request", e);
			 try {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
			} catch (IOException e1) {
				 logger.error("Failed to send internal server error response", e1);
			}
		}
		
    }
	
	@PostMapping(value = "/checkMemberIdDuplicate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultVO checkMemberIdDuplicate(@RequestParam String memberId) throws SQLException {
	    String validationMessage = validationService.validateField(memberId, "memberId.required", "^[a-zA-Z0-9]{1,20}$", "memberId.invalid.error", null);
	    if (validationMessage != null) {
	        return new ResultVO("0", messageSource.getMessage(validationMessage, null, Locale.getDefault()));
	    }

	    boolean isDuplicate = validationService.isIdDuplicate(memberId);
	    String message = isDuplicate ? "memberId.duplicate" : "memberId.available";
	    return new ResultVO(isDuplicate ? "0" : "1", messageSource.getMessage(message, null, Locale.getDefault()));
	}

	@PostMapping(value = "/checkNickNameDuplicate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultVO checkNickNameDuplicate(@RequestParam String nickName) throws SQLException {
	    // 유효성 검사
	    String validationMessage = validationService.validateField(nickName, "nickName.required", "^[가-힣a-zA-Z0-9]{1,15}$", "nickName.invalid.error", null);
	    if (validationMessage != null) {
	        return new ResultVO("0", messageSource.getMessage(validationMessage, null, Locale.getDefault()));
	    }

	    // 중복 체크
	    boolean isDuplicate = validationService.isNickNameDuplicate(nickName);
	    String message = isDuplicate ? "nickName.duplicate" : "nickName.available";
	    return new ResultVO(isDuplicate ? "0" : "1", messageSource.getMessage(message, null, Locale.getDefault()));
	}

	@PostMapping(value = "/checkEmailDuplicate", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultVO checkEmailDuplicate(@RequestParam String email) throws SQLException {
	    // 유효성 검사
	    String validationMessage = validationService.validateField(email, "email.required", null, null, null);
	    if (validationMessage != null) {
	        return new ResultVO("0", messageSource.getMessage(validationMessage, null, Locale.getDefault()));
	    }

	    // 중복 체크
	    boolean isDuplicate = validationService.isEmailDuplicate(email);
	    String message = isDuplicate ? "email.duplicate" : "email.available";
	    return new ResultVO(isDuplicate ? "0" : "1", messageSource.getMessage(message, null, Locale.getDefault()));
	}

	@GetMapping(value = "/viewJoinMember")
	public String viewJoinMember() throws SQLException {
		return "member/member_reg";
	}

	@GetMapping(value = "/personalMemberInfo")
	public String personalMemberInfo(MemberVO inVO, Model model, HttpSession session) throws SQLException {
		if (!sessionService.checkAndSetMemberId(session, inVO)) {
			model.addAttribute("errorMessage", messageSource.getMessage("login.required", null, Locale.getDefault()));
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
	public ResultVO withdrawalMember(MemberVO inVO, HttpSession session) throws SQLException {
		MemberVO outVO = service.viewMemberDetail(inVO);
		if (!sessionService.isSessionMatched(session, outVO.getMemberId())) {
			String errorMessage = messageSource.getMessage("login.permission.error", null, Locale.getDefault());
	        return new ResultVO("0", errorMessage);
		}

		int flag = service.withdrawalMember(inVO);
		String message;
		if (flag == 1) {
	        session.invalidate();
	        message = messageSource.getMessage("delete.success", null, Locale.getDefault());
	    } else {
	        message = messageSource.getMessage("delete.error", null, Locale.getDefault());
	    }

		return new ResultVO(String.valueOf(flag), message);
	}

	@PostMapping(value = "/updateMemberInfo", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultVO updateMemberInfo(MemberVO inVO) throws SQLException {
		int flag = service.updateMemberInfo(inVO);
		String message = (flag == 1) ? 
				messageSource.getMessage("update.success", null, Locale.getDefault()):
			    messageSource.getMessage("update.error", null, Locale.getDefault());
		return new ResultVO(String.valueOf(flag), message);
	}
	
	@PostMapping(value = "/changeMemberPassword", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultVO changeMemberPassword(MemberVO inVO) throws SQLException {	    
		int flag = service.changeMemberPassword(inVO);
		String message = (flag == 1) ? 
				messageSource.getMessage("update.success", null, Locale.getDefault()):
			    messageSource.getMessage("update.error", null, Locale.getDefault());
		return new ResultVO(String.valueOf(flag), message);
	}
	
	@PostMapping(value = "/joinMember", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public ResultVO joinMember(MemberVO inVO) throws SQLException {
	    logger.info("회원가입 시도:" + inVO);

	    ResultVO validationResult = validationService.validateMember(inVO);
	    if (validationResult != null && "0".equals(validationResult.getMsgId())) {
	        logger.info("회원가입 실패 - 유효성 검사 실패: " + validationResult);
	        return validationResult;
	    }

	    int flag = service.joinMember(inVO);
	    String message = (flag == 1) ? 
	    		messageSource.getMessage("memberJoin.success", null, Locale.getDefault()):
				messageSource.getMessage("memberJoin.error", null, Locale.getDefault());
	    ResultVO result = new ResultVO(String.valueOf(flag), message);
	    logger.info("회원가입 결과: " + result);
	    return result;
	}

}
