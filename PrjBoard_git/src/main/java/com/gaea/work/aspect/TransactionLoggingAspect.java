package com.gaea.work.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionLoggingAspect {    
    Logger logger = LogManager.getLogger(this.getClass());
    
    @Before("execution(* com.gaea.work..*(..))")
    public void logBeforeTransaction(JoinPoint joinPoint) {
        logger.info("Starting transaction for method: {}", joinPoint.getSignature().toShortString()); //실행 중인 메서드 이름 반환
    }
    
    @AfterReturning("execution(* com.gaea.work..*(..))")
    public void logAfterTransaction(JoinPoint joinPoint) {
        logger.info("Transaction completed successfully for method: {}", joinPoint.getSignature().toShortString());
    }
    
    @AfterThrowing(pointcut = "execution(* com.gaea.work..*(..))", throwing = "ex")
    public void logAfterTransactionFailure(JoinPoint joinPoint, Throwable ex) {
        logger.error("Transaction failed for method: {} with exception: {}", joinPoint.getSignature().toShortString(), ex.getMessage());        
    }    
}
