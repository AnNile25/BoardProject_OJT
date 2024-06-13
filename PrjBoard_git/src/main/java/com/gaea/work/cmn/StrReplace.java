package com.gaea.work.cmn;

public class StrReplace {
	
	/**
	 * 문자열 치환
	 * @param strTarget(원본 문자열)
	 * @param strReplace(치환문자열
	 * @return String
	 */
	public static String nvl(final String strTarget, final String strReplace) {
		if(null == strTarget || "".equals(strTarget)) {
			return strReplace.trim();
		}
		
		return strTarget.trim();
	}

}
