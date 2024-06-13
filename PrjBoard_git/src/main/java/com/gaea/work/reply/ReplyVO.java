package com.gaea.work.reply;

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
public class ReplyVO {
	
	public int replySeq;
	public String content;
	public int likeCnt;
	public String regDt;
	public String modDt;
	public int boardSeq;
	public String memberId;

}
