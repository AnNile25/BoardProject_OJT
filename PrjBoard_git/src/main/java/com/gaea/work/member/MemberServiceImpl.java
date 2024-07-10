package com.gaea.work.member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaea.work.validation.MemberValidationService;

@Service
public class MemberServiceImpl implements MemberService {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	MemberDao dao;
	@Autowired
    MemberValidationService validationService;
	@Autowired
    BCryptPasswordEncoder passwordEncoder;
	
	public MemberServiceImpl() {}
	
	private static final String CONFMN_KEY = "devU01TX0FVVEgyMDI0MDYxMjE3MDc1MTExNDgzODM=";
	
	@Override
	public void getAddrApiUrl(HttpServletRequest req, HttpServletResponse response) throws IOException {
		BufferedReader br = null;
    	try {
	        String currentPage = req.getParameter("currentPage"); 
	        String countPerPage = req.getParameter("countPerPage"); 
	        String resultType = req.getParameter("resultType");
	        String keyword = req.getParameter("keyword"); 
	        String callback = req.getParameter("callback");
	        
			String apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage=" + currentPage 
									+ "&countPerPage=" + countPerPage 
									+ "&keyword=" + URLEncoder.encode(keyword, "UTF-8") 
									+ "&confmKey=" + CONFMN_KEY 
									+ "&resultType=" + resultType;
			URL url = new URL(apiUrl);
	        
        	br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String tempStr = null;
			while ((tempStr = br.readLine()) != null) {
	            sb.append(tempStr);
	        }
			br.close();
			
			String jsonResponse = sb.toString();
			jsonResponse = callback + "(" + jsonResponse + ")";
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse); 
    	} catch (IOException e) {
            logger.error("Error processing the request", e);
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                	logger.error("BufferedReader close ERROR", e);
                }
            }
        }
	}
	

	@Override
	public int updateMemberInfo(MemberVO inVO) throws SQLException {
		return dao.updateMember(inVO);
	}

	@Override
	@Transactional
	public int changeMemberPassword(MemberVO inVO) throws SQLException {
		String encodedPassword = passwordEncoder.encode(inVO.getPassword());
        inVO.setPassword(encodedPassword);
		return dao.updateMemberPassword(inVO);
	}

	@Override
	@Transactional
	public int withdrawalMember(MemberVO inVO) throws SQLException {
		return  dao.deleteMember(inVO);
	}

	@Override
	public MemberVO viewMemberDetail(MemberVO inVO) throws SQLException, EmptyResultDataAccessException {
		return dao.selectOneMember(inVO);
	}
	
	@Override
	@Transactional
	public int joinMember(MemberVO inVO) throws SQLException {
		validationService.validateMember(inVO);
		String encodedPassword = passwordEncoder.encode(inVO.getPassword());
        inVO.setPassword(encodedPassword);
		return dao.saveMember(inVO);
	}

	@Override
	public List<MemberVO> retrieveMember(MemberVO inVO) throws SQLException {
		return dao.retrieveMember(inVO);
	}


}
