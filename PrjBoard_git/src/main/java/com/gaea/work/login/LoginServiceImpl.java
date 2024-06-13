package com.gaea.work.login;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.gaea.work.cmn.GLog;
import com.gaea.work.cmn.SuccessMessageVO;
import com.gaea.work.member.MemberVO;
import com.google.gson.Gson;

@Service
public class LoginServiceImpl implements LoginService {

    Logger LOG = LogManager.getLogger(GLog.class);

    @Autowired
    LoginDao dao;

    public LoginServiceImpl() {
    }

	@Override
	public MemberVO doSelectOne(MemberVO inVO) throws SQLException, EmptyResultDataAccessException {
		return dao.selectOneMember(inVO);
	}

	@Override
    public int loginCheck(MemberVO inVO) throws SQLException {
        int checkStatus = 0;

        // idCheck
        int status = dao.idCheck(inVO);

        if(status == 0) {
            checkStatus = 10;
            LOG.debug("10 idCheck checkStatus:" + checkStatus);
            return checkStatus;
        }

        // idCheck: 비번 check
        status = dao.idPassCheck(inVO);
        if(status == 0) {
            checkStatus = 20;
            LOG.debug("20 idPassCheck checkStatus:" + checkStatus);
            return checkStatus;
        }

        checkStatus = 30; // id/비번 정상 로그인
        LOG.debug("30 idPassCheck pass checkStatus:" + checkStatus);
        return checkStatus;
    }

    @Override
    public String doLogin(MemberVO member, HttpSession session) {
        LOG.debug("[doLogin] member: " + member);
        String jsonString = "";

        SuccessMessageVO message = new SuccessMessageVO();

        try {
            // id null check
            if (member.getMemberId() == null || member.getMemberId().isEmpty()) {
                return createJsonMessage("1", "아이디를 입력 하세요.");
            }

            // pass null check
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
                    MemberVO outVO = doSelectOne(member);
                    message.setMsgId("30");
                    message.setMsgContents(outVO.getMemberName() + "님 반갑습니다.");
                    session.setAttribute("member", outVO);
                    break;
                default:
                    return createJsonMessage("99", "오류가 발생 했습니다.");
            }
        } catch (Exception e) {
            LOG.error("Exception occurred while logging in: " + e.getMessage(), e);
            return createJsonMessage("99", "서버 오류가 발생했습니다.");
        }

        jsonString = new Gson().toJson(message);
        LOG.debug("jsonString:" + jsonString);

        return jsonString;
    }

    private String createJsonMessage(String msgId, String msgContents) {
        SuccessMessageVO message = new SuccessMessageVO();
        message.setMsgId(msgId);
        message.setMsgContents(msgContents);
        return new Gson().toJson(message);
    }
	
}
