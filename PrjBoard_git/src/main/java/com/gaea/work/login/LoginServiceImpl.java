package com.gaea.work.login;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gaea.work.cmn.SuccessMessageVO;
import com.gaea.work.member.MemberService;
import com.gaea.work.member.MemberVO;
import com.google.gson.Gson;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    LoginDao dao;
    
    @Autowired
    MemberService memberService;

    public LoginServiceImpl() {
    }

	@Override
    public int loginCheck(MemberVO inVO) throws SQLException {
        int checkStatus = 0;

        // idCheck
        int status = dao.idCheck(inVO);

        if(status == 0) {
            checkStatus = 10;
            return checkStatus;
        }

        // idCheck: 비번 check
        status = dao.idPassCheck(inVO);
        if(status == 0) {
            checkStatus = 20;
            return checkStatus;
        }

        checkStatus = 30; // id/비번 정상 로그인
        return checkStatus;
    }

    @Override
    public String doLogin(MemberVO member, HttpSession session) {
        String jsonString = "";

        SuccessMessageVO message = new SuccessMessageVO();

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
                    return createJsonMessage("20", "비번을 확인 하세요.");
                case 30:
                    MemberVO outVO = memberService.viewMemberDetail(member);
                    message.setMsgId("30");
                    message.setMsgContents(outVO.getMemberName() + "님 반갑습니다.");
                    session.setAttribute("memberId", outVO.getMemberId());
                    break;
                default:
                    return createJsonMessage("99", "오류가 발생 했습니다.");
            }
        } catch (Exception e) {
            return createJsonMessage("99", "서버 오류가 발생했습니다.");
        }

        jsonString = new Gson().toJson(message);

        return jsonString;
    }

    private String createJsonMessage(String msgId, String msgContents) {
        SuccessMessageVO message = new SuccessMessageVO();
        message.setMsgId(msgId);
        message.setMsgContents(msgContents);
        return new Gson().toJson(message);
    }
	
}
