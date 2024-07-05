package com.gaea.work.exception;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gaea.work.cmn.SuccessMessageVO;

@ControllerAdvice
public class GlobalExceptionHandler {
	Logger logger = LogManager.getLogger(this.getClass());
	
	@ExceptionHandler(SQLException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public SuccessMessageVO handleSQLException(SQLException ex) {
		logger.error("SQLException 발생: ", ex);
		String clientMessage = "서버 요청 중 데이터베이스 오류가 발생했습니다. 관리자에게 문의해주세요.";
		return new SuccessMessageVO("0", clientMessage);
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public SuccessMessageVO handleException(Exception ex) {
		logger.error("Exception 발생: ", ex);
		String clientMessage = "서버 오류가 발생했습니다. 관리자에게 문의해주세요.";
		return new SuccessMessageVO("0", clientMessage);
	}

}
