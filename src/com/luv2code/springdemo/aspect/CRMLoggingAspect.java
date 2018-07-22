package com.luv2code.springdemo.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public final class CRMLoggingAspect {

	// set up logger
	// setup pointcut declarations
	// @Before advice
	// @AfterReturning advice
	
	private Logger logg = Logger.getLogger(getClass().getName());
	
	
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage(){
		
	}
	
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage(){
		
	}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDAOPackage(){
		
	}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void appFlow(){
	
	}
	
	@Before("appFlow()")
	public void before(JoinPoint jp){
		
		String method = jp.getSignature().toShortString();
		
		logg.info("=======> in @Before: calling method " + method);
		
		Object[] args = jp.getArgs();
		
		for(Object temp: args){
			logg.info("======> Temp Argumt " + temp);
		}
	
	}
	
	@AfterReturning(
			pointcut="appFlow()",
			returning="result")
	public void afterReturning(JoinPoint jp, Object result){
		
		String method = jp.getSignature().toShortString();
		
		logg.info("=======> in @AfterReturning: from method " + method);
		
		logg.info("=======> Result " + result);
		
	}
	
	public void someChanges() {
		System.out.println("Do Nothing");
	}
	
}
