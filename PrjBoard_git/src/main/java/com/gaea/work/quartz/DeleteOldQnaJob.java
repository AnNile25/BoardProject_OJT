package com.gaea.work.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaea.work.qna.QnaService;

@Component
public class DeleteOldQnaJob implements Job {
	
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	QnaService service;
	
	@Autowired
	public void setQnaService(QnaService service) {
	    this.service = service;
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("DeleteOldQnaJob start.");
		try {
			service.deleteOldQnaArticle();
			logger.info("DeleteOldQnaJob 성공");
        } catch (Exception e) {
            logger.error("DeleteOldQnaJob 실패", e);
        }
	}

}