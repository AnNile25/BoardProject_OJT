package com.gaea.work.validation;

import java.sql.SQLException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.gaea.work.cmn.ResultVO;
import com.gaea.work.qna.QnaDao;
import com.gaea.work.qna.QnaVO;

@Service
public class QnaValidationService {
    
    @Autowired
    QnaDao dao;
    
    @Autowired
    MessageSource messageSource;
    
    public ResultVO validateQna(QnaVO inVO) throws SQLException {
        String errorMessage;
        
        errorMessage = validateField(inVO.getTitle(), "title.required", 30, "tite.invalid.error");
        if (errorMessage != null) return createErrorMessage(errorMessage);

        errorMessage = validateField(inVO.getContent(), "content.required", 500, "content.invalid.error");
        if (errorMessage != null) return createErrorMessage(errorMessage);

        return new ResultVO("1", messageSource.getMessage("validation.succes", null, Locale.getDefault()));
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
    
    private ResultVO createErrorMessage(String errorMessage) {
    	return new ResultVO("0", messageSource.getMessage(errorMessage, null, Locale.getDefault()));
    }
}
