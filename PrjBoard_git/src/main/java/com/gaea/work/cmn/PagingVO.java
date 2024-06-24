package com.gaea.work.cmn;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class PagingVO {
	
	int nowPage; // 현재 페이지
	int startPage; // (화면에서 보여지는) 시작 페이지
	int endPage; // 끝 페이지
	int totalCnt; // 전체 게시글 수
	int cntPerPage; //  한 페이지 당 글 수
	int lastPage; // 마지막 페이지
	
	int startRow;
	int endRow;
	
	// 검색어
	String searchKeyword;
	String startDate;
	String endDate;
	
	int cntPage = 5; // 화면에서 보여지는 페이지 개수
	
	public PagingVO(int totalCnt, int nowPage, int cntPerPage) {
		setNowPage(nowPage);
		setCntPerPage(cntPerPage);
		setTotalCnt(totalCnt);
		calculatedLastPage(getTotalCnt(), getCntPerPage());
		calculatedStartEndPage(getNowPage(), cntPage);
		calculatedStartEnd(getNowPage(), getCntPerPage());
	}
	
	// 마지막 페이지 계산
	public void calculatedLastPage(int totalCnt, int cntPerPage) {
		setLastPage((int) Math.ceil((double)totalCnt / (double)cntPerPage));
	}
	
	// 한번에 보여지는 시작/끝 페이지 계산
	public void calculatedStartEndPage(int nowPage, int cntPage) {
		setEndPage(((int)Math.ceil((double)nowPage / (double)cntPage)) * cntPage);
		if (getLastPage() < getEndPage()) {
			setEndPage(getLastPage());
		}
		setStartPage(getEndPage() - cntPage + 1);
		if (getStartPage() < 1) {
			setStartPage(1);
		}
	}
	
	// DB에서 사용될 rowNum 시작/끝 계산
	public void calculatedStartEnd(int nowPage, int cntPerPage) {
		setEndRow(nowPage * cntPerPage);
		setStartRow(getEndRow() - cntPerPage + 1);
	}

}
