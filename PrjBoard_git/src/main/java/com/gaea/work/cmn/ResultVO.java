package com.gaea.work.cmn;

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
public class ResultVO {
	
	private String msgId;//메시지ID
	private String msgContents;//메지시 내용
	private Object apiData; //api 호출 결과 데이터
	
	public ResultVO (String msgId, String msgContents) {
		this.msgId = msgId;
		this.msgContents = msgContents;
	}
	
}
