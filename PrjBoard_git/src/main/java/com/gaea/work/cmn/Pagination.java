package com.gaea.work.cmn;

public class Pagination {	
	
	/**
	 * 
	 * @param maxNum : 총글수
	 * @param currentPageNo : 페이지번호
	 * @param rowPerPage : 페이지 사이즈 ->10/20/30/50/100
	 * @param bottomCount: 10/5
	 * @param url        : 서버호출 URL
	 * @param scriptName : 자바스크립트 함수명
	 * @return
	 */
	public static String renderingPager(long maxNum, long currentPageNo, long rowPerPage, long bottomCount, String url, String scriptName ) {
		StringBuilder html=new StringBuilder(3000);
		
		//<< < 1 2 3...10 > >>
		//<< : 첫 page
		//<  :  bottomCount 만큼 이동 -
		//1,2,3 : 해당페이지 글
		//>  :  bottomCount 만큼 이동 +
		//>> : 마지막 페이지
		
		//maxNum = 21
		//currentPageNo = 1
		//rowPerPage    = 10
		//bottomCount   = 10
		
		long maxPageNo   = (maxNum -1)/rowPerPage +1;//3
		long startPageNO = ((currentPageNo -1)/bottomCount) * bottomCount +1  ;//1,11,21,...
		long endPageNo   = ((currentPageNo -1)/bottomCount+1)*bottomCount;   //10,20,30,...
		
		long nowBlockNo  = ((currentPageNo-1)/bottomCount)+1;//1
		long maxBlockNo  = ((maxNum -1)/bottomCount)+1;//3
		
		if(currentPageNo > maxPageNo) {
			return "";
		}
		
		html.append("<ul class=\"pagination\"> \n");
		//<<
		if(nowBlockNo>1 && nowBlockNo <= maxBlockNo) {
			html.append("<li class=\"page-item\"> \n");
			html.append("<a class=\"page-link\" href=\"javascript:"+scriptName+"('"+url+"',1); \"> \n"); 
			html.append("<span>&laquo;</span>");
			html.append("</a>  \n");
			html.append("</li> \n");
		}
		
		//< : &lt;
		if(startPageNO > bottomCount) {
			html.append("<li class=\"page-item\"> \n");
			html.append("<a class=\"page-link\" href=\"javascript:"+scriptName+"('"+url+"',"+(startPageNO - bottomCount)+"); \"> \n");  
			html.append("<span>&lt;</span>");
			html.append("</a>  \n");
			html.append("</li> \n");			
		}
		
		//1 2 3 ... 10
		long inx  = 0;
		for(inx =startPageNO ;inx<=maxPageNo && inx <=endPageNo;inx++ ) {
			if(inx == currentPageNo) {//현제 페이지 이면 link 없음
				html.append("<li class=\"page-item\"> \n");
				html.append("<a  class=\"page-link active\" href=\"#\"> \n");
				html.append(inx);
				html.append("</a>  \n");
				html.append("</li> \n");	
			}else {
				html.append("<li class=\"page-item\"> \n");
				html.append("<a class=\"page-link\" href=\"javascript:"+scriptName+"('"+url+"',"+inx+"); \"> \n");
				html.append(inx);
				html.append("</a>  \n");
				html.append("</li> \n");	
			}
		}
		
		//> : &gt;
		if(maxPageNo>inx) {
			html.append("<li class=\"page-item\"> \n");
			html.append("<a class=\"page-link\" href=\"javascript:"+scriptName+"('"+url+"',"+((nowBlockNo * bottomCount)+1)+"); \"> \n");
			html.append("<span>&gt;</span>");
			html.append("</a>  \n");
			html.append("</li> \n");	
		}
		
		//>>
		if(maxPageNo>inx) {
			html.append("<li class=\"page-item\"> \n");
			html.append("<a class=\"page-link\" href=\"javascript:"+scriptName+"('"+url+"',"+maxPageNo+"); \"> \n");
			html.append("<span>&raquo;</span>");
			html.append("</a>  \n");
			html.append("</li> \n");	
		}
		
		html.append("</ul> \n");
		
		return html.toString();
	}
}
