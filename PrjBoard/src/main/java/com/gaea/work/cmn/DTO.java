package com.gaea.work.cmn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTO {

	private long no;      //글번호
	private long totalCnt;//총글수
	private long pageSize;//페이지 사이즈
	private long pageNo;  //페이지 번호
	
	private String searchDiv;//검색구분
	private String searchWord;//검색어
	
	@Override
	public String toString() {
		return "DTO [no=" + no + ", totalCnt=" + totalCnt + ", pageSize=" + pageSize + ", pageNo=" + pageNo
				+ ", searchDiv=" + searchDiv + ", searchWord=" + searchWord + "]";
	}

}
