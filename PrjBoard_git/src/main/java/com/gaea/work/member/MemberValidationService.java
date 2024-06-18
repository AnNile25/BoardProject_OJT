package com.gaea.work.member;

import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import com.gaea.work.cmn.SuccessMessageVO;
import com.google.gson.Gson;

@Service
public class MemberValidationService {
    
    @Autowired
    MemberDao dao;
    
    @Autowired
    MessageSource messageSource;
    
    public String validateMember(MemberVO inVO) throws SQLException {
        String errorMessage = validateField(inVO.getMemberId(), "error.id.required", "^[a-zA-Z0-9]{1,20}$", "error.id.invalid", this::isIdDuplicate);
        if (errorMessage != null) return createJsonMessage("0", messageSource.getMessage(errorMessage, null, null));

        errorMessage = validateField(inVO.getMemberName(), "error.name.required", "^[가-힣a-zA-Z]{1,15}$", "error.name.invalid", null);
        if (errorMessage != null) return createJsonMessage("0", messageSource.getMessage(errorMessage, null, null));

        errorMessage = validateField(inVO.getPassword(), "error.password.required", "^.{1,20}$", "error.password.length", null);
        if (errorMessage != null) return createJsonMessage("0", messageSource.getMessage(errorMessage, null, null));

        errorMessage = validateField(String.valueOf(inVO.getTel()), "error.tel.required", "^\\d{8}$", "error.tel.invalid", null);
        if (errorMessage != null) return createJsonMessage("0", messageSource.getMessage(errorMessage, null, null));

        errorMessage = validateField(inVO.getNickName(), "error.nickname.required", "^[가-힣a-zA-Z0-9]{1,10}$", "error.nickname.invalid", this::isNickNameDuplicate);
        if (errorMessage != null) return createJsonMessage("0", messageSource.getMessage(errorMessage, null, null));

        errorMessage = validateField(inVO.getEmail(), "error.email.required", "^[a-zA-Z0-9@.]{1,50}$", "error.email.invalid", this::isEmailDuplicate);
        if (errorMessage != null) return createJsonMessage("0", messageSource.getMessage(errorMessage, null, null));


        return createJsonMessage("1", messageSource.getMessage("success.validation", null, null));
    }
    
    public String validateField(String field, String emptyMessage, String regex, String regexErrorMessage, FieldDuplicationChecker duplicationChecker) throws SQLException {
        if (field == null || field.trim().isEmpty()) {
            return emptyMessage;
        }
        if (regex != null && !field.matches(regex)) {
            return regexErrorMessage;
        }
        if (duplicationChecker != null && duplicationChecker.isDuplicate(field)) {
            return "error.duplicate";
        }
        return null;
    }

    public boolean isIdDuplicate(String memberId) throws SQLException {
        MemberVO vo = new MemberVO();
        vo.setMemberId(memberId);
        return dao.idDuplicateCheck(vo) > 0;
    }

    public boolean isNickNameDuplicate(String nickName) throws SQLException {
        MemberVO vo = new MemberVO();
        vo.setNickName(nickName);
        return dao.nickNameDuplicateCheck(vo) > 0;
    }

    public boolean isEmailDuplicate(String email) throws SQLException {
        MemberVO vo = new MemberVO();
        vo.setEmail(email);
        return dao.emailDuplicateCheck(vo) > 0;
    }
    
    public String createJsonMessage(String msgId, String msgContents) {
        SuccessMessageVO message = new SuccessMessageVO();
        message.setMsgId(msgId);
        message.setMsgContents(msgContents);
        return new Gson().toJson(message);
    }

    @FunctionalInterface
    private interface FieldDuplicationChecker {
        boolean isDuplicate(String field) throws SQLException;
    }
}
