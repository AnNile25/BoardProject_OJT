package com.gaea.work.member;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.gaea.work.cmn.GLog;
import com.gaea.work.cmn.SuccessMessageVO;

@Controller
@RequestMapping("member")
public class MemberController implements GLog {

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
	
    /* 회원 수정 페이지 이동*/
	@GetMapping(value = "/moveToMod")
	public String moveToMod(MemberVO inVO, HttpSession httpSession, Model model) throws SQLException {
		LOG.debug("[moveToMod]");
		
		if(null == inVO.getMemberId() ) {
	        throw new NullPointerException("아이디를 확인하세요.");
	    }
		
		MemberVO outVO = service.selectOneMember(inVO);
		
		model.addAttribute("vo", outVO);			
	    model.addAttribute("memberId", outVO.getMemberId()); 
		
		return "member/member_mod";
	}

	@GetMapping(value = "/moveToReg")
	public String moveToReg() throws SQLException {
		LOG.debug("[moveToReg]");
		return "member/member_reg";
	}
	
	@GetMapping(value = "/personalMemberInfo")
	public String personalMemberInfo(MemberVO inVO, Model model) throws SQLException {
		LOG.debug("[personalMemberInfo]MemberVO:"+inVO);		
		
		if("" == inVO.getMemberId()|| null == inVO.getMemberId()) {			
			throw new NullPointerException("아이디를 확인하세요.");
		}
		
		MemberVO  outVO = service.selectOneMember(inVO);
		model.addAttribute("vo", outVO);
				
		return "member/member_mng";
	}
	
	@GetMapping(value = "/retrieveMember")
	public ModelAndView retrieveMember(MemberVO inVO, ModelAndView modelAndView) throws SQLException {
		LOG.debug("[retrieveMember]MemberVO:"+inVO);

	    List<MemberVO> list = service.retrieveMember(inVO);
	    	    
	    modelAndView.setViewName("member/member_list");	    
	    modelAndView.addObject("list", list);
	    modelAndView.addObject("paramVO", inVO);

	    return modelAndView;
	}
	
	@GetMapping(value ="/withdrawalMember",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO withdrawalMember(MemberVO inVO) throws SQLException {
		LOG.debug("[withdrawalMember]MemberVO:"+inVO);
		
		int flag = service.withdrawalMember(inVO);
		
		String message = (flag == 1) ? "삭제 되었습니다." : "삭제 실패.";
		SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);		
		return messageVO;
	}

	@PostMapping(value = "/updateMember", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO updateMember(MemberVO inVO) throws SQLException {
		LOG.debug("[updateMember]MemberVO:" + inVO);

		int flag = service.updateMember(inVO);

		String message = (flag == 1) ? "수정 되었습니다." : "수정 실패.";
		SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);
		return messageVO;
	}

	@PostMapping(value = "/joinMember", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public SuccessMessageVO joinMember(MemberVO inVO) throws SQLException {
		LOG.debug("[joinMember]MemberVO:" + inVO);

        int flag = service.joinMember(inVO);

        String message = (flag == 1) ? "가입 되었습니다." : "가입 실패.";
        SuccessMessageVO messageVO = new SuccessMessageVO(String.valueOf(flag), message);
        return messageVO;
	}

}
