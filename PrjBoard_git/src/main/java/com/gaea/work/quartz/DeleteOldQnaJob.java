package com.gaea.work.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaea.work.qna.QnaService;

@Component
public class DeleteOldQnaJob implements Job {
	
	@Autowired
	QnaService service;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		service.deleteOldQna();
	}

}
