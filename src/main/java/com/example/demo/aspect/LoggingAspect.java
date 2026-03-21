package com.example.demo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
	// Declaring Pointcut
    @Pointcut("execution(* com.example.demo.service.*.*(..))")
    public void serviceMethods() {}

    //  Declaring Advice 
    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

       
        Object result = joinPoint.proceed(); 

        long timeTaken = System.currentTimeMillis() - startTime;
        
        // Real world use case: Performance monitoring
        log.info("Method {} executed in {} ms", joinPoint.getSignature(), timeTaken);
        
        return result;
    }
}
