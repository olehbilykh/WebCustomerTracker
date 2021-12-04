package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {
    private Logger logger = Logger.getLogger(getClass().getName());


    @Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
    private void forControllerPackage(){}

    @Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
    private void forServicePackage(){}

    @Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
    private void forDaoPackage(){}

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow(){}


    @Before("forAppFlow()")
    public void before(JoinPoint joinPoint){
        String methodSignature = joinPoint.getSignature().toShortString();
        logger.info("=====> in @Before: calling method: " + methodSignature);

        Object[] args = joinPoint.getArgs();

        for (Object object: args) {
            logger.info("Args >>> " + object);
        }

    }

    @AfterReturning(pointcut = "forAppFlow()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result){
        String methodSignature = joinPoint.getSignature().toShortString();
        logger.info("=====> in @AfterReturning: calling method: " + methodSignature);
        logger.info("===>> result: " + result);
    }
}
