package com.gaea.work.aspect;

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
    
    @Pointcut("execution(* com.gaea.work..*.*(..)) && @annotation(org.springframework.transaction.annotation.Transactional)")
    public void cut() {}
    
    @Before("cut()")
    public void logBeforeTransaction(JoinPoint joinPoint) {
        logger.info("Starting transaction for method: {}", joinPoint.getSignature().toShortString()); //실행 중인 메서드 이름 반환
    }
    
    @AfterReturning(value = "cut()", returning = "returnObj")
    public void logAfterTransaction(JoinPoint joinPoint, Object returnObj) {
        logger.info("Transaction completed successfully for method: {} return Obj: {}", joinPoint.getSignature().toShortString(), returnObj);
    }
    
    @AfterThrowing(pointcut = "cut()", throwing = "ex")
    public void logAfterTransactionFailure(JoinPoint joinPoint, Exception ex) {
        logger.error("Transaction failed for method: {} with exception: {}", joinPoint.getSignature().toShortString(), ex.getMessage());        
    }    
}
