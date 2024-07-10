package com.gaea.work.api;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddrApiService {
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	AddrApiDao addrApiDao;
	
	public void getAddrApi(HttpServletRequest req, HttpServletResponse response) {
		try {
            String jsonResponse = addrApiDao.fetchAddrApiData(req);
            String callback = req.getParameter("callback");
            jsonResponse = callback + "(" + jsonResponse + ")";
            
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (IOException e) {
            logger.error("Failed to process the request", e);
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다: " + e.getMessage());
            } catch (IOException ex) {
                logger.error("Failed to send error response", ex);
                try {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");
                } catch (IOException ex2) {
                    logger.error("Failed to send internal server error response", ex2);
                }
            }
        }
	}

}
