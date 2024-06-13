package com.gaea.work.qna;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Component
public class PageVO {
	private int page;
	private int maxPage;
	private int startPage;
	private int endPage;
	private int startRow;
}
