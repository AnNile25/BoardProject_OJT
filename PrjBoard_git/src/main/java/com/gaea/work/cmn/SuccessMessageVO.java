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
public class SuccessMessageVO {
	
	private String msgId;//메시지ID
	private String msgContents;//메지시 내용
	
}
