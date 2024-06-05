package com.gaea.work.board.domain;

import com.gaea.work.cmn.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardVO extends DTO{
	
	private int boardSeq;		// 보드 번호
	private int boardCtgrySeq;	// 카테고리 번호
	private String memberId;	// 회원아이디(등록자)
	private String title;		// 제목
	private String content;		// 내용
	private int likeCnt;		// 추천수
	private int readCnt;		// 조회수
	private String regDt;		// 등록일
	private String modDt;		// 수정일
	
	private String searchKeyword; //검색 키워드

	@Override
	public String toString() {
		return "BoardVO [boardSeq=" + boardSeq + ", boardCtgrySeq=" + boardCtgrySeq + ", memberId=" + memberId
				+ ", title=" + title + ", content=" + content + ", likeCnt=" + likeCnt + ", readCnt=" + readCnt
				+ ", regDt=" + regDt + ", modDt=" + modDt + ", searchKeyword=" + searchKeyword + ", toString()="
				+ super.toString() + "]";
	}
	
}
