package com.example.trace.log;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class MethodTraceInterceptor {

	
	@Around("@annotation(MethodTrace)")
	public Object logRequest(ProceedingJoinPoint joinPoint) throws Throwable { // HttpServletRequest to be added in the context

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Logger logger = LoggerFactory.getLogger( method.getDeclaringClass()); 
		logger.info("\"start --- method " + method.getDeclaringClass().getName() + "." + method.getName() + " with args " + joinPoint.getArgs().length);
		Object obj = null;
		obj = joinPoint.proceed();
		logger.info("end --- method " + method.getName() + "with args " + joinPoint.getArgs());
		return obj;
	}

}
