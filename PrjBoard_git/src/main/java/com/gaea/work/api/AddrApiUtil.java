package com.gaea.work.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

public class AddrApiUtil {
	
	private static final String CONFMN_KEY = "devU01TX0FVVEgyMDI0MDYxMjE3MDc1MTExNDgzODM=";
	
	public String fetchAddrApiData(String currentPage, String countPerPage, String resultType, String keyword) throws IOException {
		BufferedReader br = null;
		try {
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
			try {
				br.close();
			} catch (IOException e) {
				throw new IOException("BufferedReader close ERROR", e);
			}
		}
	}
	
}
