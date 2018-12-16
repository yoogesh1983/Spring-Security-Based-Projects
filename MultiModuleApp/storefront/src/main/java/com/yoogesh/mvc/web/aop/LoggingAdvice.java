package com.yoogesh.mvc.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LoggingAdvice 
{
	@Pointcut("execution(* com.yoogesh.service.*.*(..))")      // execution(returnType   package   .   class   .  method  (arguments)  )
	public void applyLogging(){
		
	}
	

	@Around("applyLogging()")
	public Object aroundAdvice(ProceedingJoinPoint method) throws Throwable
	{
		//System.out.println("Entering into a method : " + method.getSignature().getName() + " with Arguments: " + Arrays.asList(method.getArgs()));
		
		Object result = method.proceed();
		
		//System.out.println("Exiting from the metod");
		return result;
	}

}
