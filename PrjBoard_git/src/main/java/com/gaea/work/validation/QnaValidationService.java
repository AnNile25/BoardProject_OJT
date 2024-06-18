package com.gaea.work.validation;

import java.sql.SQLException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.gaea.work.cmn.SuccessMessageVO;
import com.gaea.work.qna.QnaDao;
import com.gaea.work.qna.QnaVO;

@Service
public class QnaValidationService {
    
    @Autowired
    QnaDao dao;
    
    @Autowired
    MessageSource messageSource;
    
    public SuccessMessageVO validateQna(QnaVO inVO) throws SQLException {
        String errorMessage;
        
        errorMessage = validateField(inVO.getTitle(), "validation.title.required", 30, "error.title.invalid");
        if (errorMessage != null) return createErrorMessage(errorMessage);

        errorMessage = validateField(inVO.getContent(), "validation.content.required", 500, "error.content.invalid");
        if (errorMessage != null) return createErrorMessage(errorMessage);

        return new SuccessMessageVO("1", messageSource.getMessage("success.validation", null, Locale.getDefault()));
    }
    
    public String validateField(String field, String emptyMessage, int maxLength, String lengthErrorMessage) throws SQLException {
        if (field == null || field.trim().isEmpty()) {
            return emptyMessage;
        }
        if (field.length() > maxLength) {
            return lengthErrorMessage;
        }
        return null;
    }
    
    private SuccessMessageVO createErrorMessage(String errorMessage) {
    	return new SuccessMessageVO("0", messageSource.getMessage(errorMessage, null, Locale.getDefault()));
    }
}
