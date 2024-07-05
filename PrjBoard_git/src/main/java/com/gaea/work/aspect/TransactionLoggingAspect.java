package com.gaea.work.aspect;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionLoggingAspect {    
    Logger logger = LogManager.getLogger(TransactionLoggingAspect.class);
    
    private ThreadLocal<String> currentTransactionId = new ThreadLocal<>();
    
    @Pointcut("execution(* com.gaea.work..*.*(..)) && @annotation(org.springframework.transaction.annotation.Transactional)")
    public void cut() {}
    
    @Before("cut()")
    public void logBeforeTransaction(JoinPoint joinPoint) {
    	 String transactionId = UUID.randomUUID().toString();
    	 currentTransactionId.set(transactionId);
    	 logger.info("Starting transaction [{}] for method: {}", transactionId, joinPoint.getSignature().toShortString());
    }
    
    @AfterReturning("cut()")
    public void logAfterTransaction(JoinPoint joinPoint) {
    	String transactionId = currentTransactionId.get();
        logger.info("Transaction [{}] completed successfully for method: {}", transactionId, joinPoint.getSignature().toShortString());
        currentTransactionId.remove();
    }
    
    @AfterThrowing(pointcut = "cut()", throwing = "ex")
    public void logAfterTransactionFailure(JoinPoint joinPoint, Exception ex) {
    	String transactionId = currentTransactionId.get();
        logger.error("Transaction [{}] failed for method: {} with exception: {}", transactionId, joinPoint.getSignature().toShortString(), ex.getMessage());
        currentTransactionId.remove();
    }
}
