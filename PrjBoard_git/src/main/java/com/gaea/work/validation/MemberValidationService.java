package com.gaea.work.validation;

import java.sql.SQLException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.gaea.work.cmn.SuccessMessageVO;
import com.gaea.work.member.MemberDao;
import com.gaea.work.member.MemberVO;

@Service
public class MemberValidationService {

    @Autowired
    MemberDao dao;

    @Autowired
    MessageSource messageSource;

    public SuccessMessageVO validateMember(MemberVO inVO) throws SQLException {
        String errorMessage;

        errorMessage = validateField(inVO.getMemberId(), "memberId.required", "^[a-zA-Z0-9]{1,20}$", "memberId.invalid.error", this::isIdDuplicate);
        if (errorMessage != null) return createErrorMessage(errorMessage);

        errorMessage = validateField(inVO.getMemberName(), "memberName.required", "^[가-힣a-zA-Z]{1,15}$", "memberName.invalid.error", null);
        if (errorMessage != null) return createErrorMessage(errorMessage);

        errorMessage = validateField(inVO.getPassword(), "password.required", "^.{1,20}$", "password.length.error", null);
        if (errorMessage != null) return createErrorMessage(errorMessage);

        errorMessage = validateField(inVO.getTel(), "tel.required", "^\\d{8}$", "tel.invalid.error", null);
        if (errorMessage != null) return createErrorMessage(errorMessage);

        errorMessage = validateField(inVO.getNickName(), "nickName.required", "^[가-힣a-zA-Z0-9]{1,10}$", "nickName.invalid.error", this::isNickNameDuplicate);
        if (errorMessage != null) return createErrorMessage(errorMessage);

        errorMessage = validateField(inVO.getEmail(), "email.required", "^[a-zA-Z0-9@.]{1,50}$", "email.invalid.error", this::isEmailDuplicate);
        if (errorMessage != null) return createErrorMessage(errorMessage);
        
        return new SuccessMessageVO("1", messageSource.getMessage("validation.succes", null, Locale.getDefault()));
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

    private SuccessMessageVO createErrorMessage(String errorMessage) {
        return new SuccessMessageVO("0", messageSource.getMessage(errorMessage, null, Locale.getDefault()));
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
    
    @FunctionalInterface
    public interface FieldDuplicationChecker {
        boolean isDuplicate(String field) throws SQLException;
    }
}
