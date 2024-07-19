package com.gaea.work.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class AddrApiUtil {
	
	private static final String CONFMN_KEY = "devU01TX0FVVEgyMDI0MDYxMjE3MDc1MTExNDgzODM=";
	
	public String fetchAddrApiData(HttpServletRequest req) throws IOException {
		BufferedReader br = null;		
		try {
            String currentPage = req.getParameter("currentPage");
            String countPerPage = req.getParameter("countPerPage");
            String resultType = req.getParameter("resultType");
            String keyword = req.getParameter("keyword");

            String apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do?currentPage=" + currentPage
                    + "&countPerPage=" + countPerPage
                    + "&keyword=" + URLEncoder.encode(keyword, "UTF-8")
                    + "&confmKey=" + CONFMN_KEY
                    + "&resultType=" + resultType;
            URL url = new URL(apiUrl);

            br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String tempStr;
            while ((tempStr = br.readLine()) != null) {
                sb.append(tempStr);
            }
            return sb.toString();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new IOException("BufferedReader close ERROR", e);
                }
            }
        }
	}

}
