package com.gaea.work.qna;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QnaVO {
	
	public int boardSeq;		// 보드 번호
	public String memberId;		// 회원아이디(등록자)
	public String title;		// 제목
	public String content;		// 내용
	public int likeCnt;			// 추천수
	public int readCnt;			// 조회수
	public String regDt;		// 등록일
	public String modDt;		// 수정일
	
	public String qnaNo;		  //글 번호
	public String searchKeyword;  //검색 키워드
	public int replyCnt; // 댓글 수
	
}
