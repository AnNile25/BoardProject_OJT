package com.gaea.work.login;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaea.work.cmn.ResultVO;
import com.gaea.work.member.MemberService;
import com.gaea.work.member.MemberVO;
import com.google.gson.Gson;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginDao dao;    
    @Autowired
    MemberService memberService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public LoginServiceImpl() {
    }

	@Override
	@Transactional
    public int loginCheck(MemberVO inVO) throws SQLException {
        int checkStatus = 0;

        int status = dao.idCheck(inVO);
        
        if(status == 0) { // id check
            checkStatus = 10;
            return checkStatus;
        }

        MemberVO storedMember = memberService.viewMemberDetail(inVO);
        if (storedMember != null && !passwordEncoder.matches(inVO.getPassword(), storedMember.getPassword())) { // password check
            checkStatus = 20; 
            return checkStatus;
        }

        checkStatus = 30; // id-password correct
        return checkStatus;
    }

	@Override
    public String doLogin(MemberVO member, HttpSession session) {
        try {
            if (member.getMemberId() == null || member.getMemberId().isEmpty()) { 
                return createJsonMessage("1", "아이디를 입력 하세요.");
            }            
            if (member.getPassword() == null || member.getPassword().isEmpty()) {
                return createJsonMessage("2", "비밀번호를 입력 하세요.");
            }
            
            int check = loginCheck(member);
            switch (check) {
	            case 10:
	                return createJsonMessage("10", "아이디를 확인 하세요.");
	            case 20:
	                return createJsonMessage("20", "비밀번호를 확인 하세요.");
	            case 30:
	                MemberVO storedMember = memberService.viewMemberDetail(member);
	                session.setAttribute("memberId", storedMember.getMemberId());
	                return createJsonMessage("30", storedMember.getMemberName() + "님 반갑습니다.");
	            default:
	                return createJsonMessage("99", "오류가 발생 했습니다.");
            }
        } catch (Exception e) {
            return createJsonMessage("99", "서버 오류가 발생했습니다.");
        }
    }

    private String createJsonMessage(String msgId, String msgContents) {
        ResultVO message = new ResultVO();
        message.setMsgId(msgId);
        message.setMsgContents(msgContents);
        return new Gson().toJson(message);
    }
	
}
