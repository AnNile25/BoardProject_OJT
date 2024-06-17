package com.gaea.work.login;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.gaea.work.member.MemberVO;
import com.gaea.work.qna.QnaVO;

@Service
public class SessionCheckService {
    
    // 로그인 여부 확인
    public boolean isLoggedIn(HttpSession session) {
    	String sessionMemberId = (String) session.getAttribute("memberId");
    	return sessionMemberId != null;
    }

    // 권한 확인
	public boolean isSessionMatched(HttpSession session, String memberId) {
		String sessionMemberId = (String) session.getAttribute("memberId");
		return sessionMemberId != null && sessionMemberId.equals(memberId);
	}
	
	// 로그인 확인 후 세션 부여(MemverVO)
	public boolean checkAndSetMemberId(HttpSession session, MemberVO inVO) {
        if (!isLoggedIn(session)) {
            return false;
        }
        inVO.setMemberId((String) session.getAttribute("memberId"));
        return true;
    }
	
	// 로그인 확인 후 세션 부여(QnaVO)
		public boolean checkAndSetMemberId(HttpSession session, QnaVO inVO) {
	        if (!isLoggedIn(session)) {
	            return false;
	        }
	        inVO.setMemberId((String) session.getAttribute("memberId"));
	        return true;
	    }

}
