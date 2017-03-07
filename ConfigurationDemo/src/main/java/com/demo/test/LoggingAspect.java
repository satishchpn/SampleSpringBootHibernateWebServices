/*package com.demo.test;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

	//@Before("execution(com.demo..*)")
	//@Before("within(com.demo..*)")
	@Before("within(com.demo..*)")
	public void logBefore(JoinPoint joinPoint) {

		System.out.println("logBefore() is running!");
		System.out.println("Method Name : " + joinPoint.getSignature().getName());
		System.out.println("******");
	}

	//@After("execution(*com.demo..*(..))")
	@After("within(com.demo..*)")
	public void logAfter(JoinPoint joinPoint) {

		System.out.println("logAfter() is running!");
		System.out.println("Method Name : " + joinPoint.getSignature().getName());
		System.out.println("******");

	}

	//@AfterReturning(pointcut = "execution)", returning = "result")
	//@AfterReturning(pointcut = "within)", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {

		System.out.println("logAfterReturning() is running!");
		System.out.println("Method Name : " + joinPoint.getSignature().getName());
		System.out.println("Method Returned value is : " + result);
		System.out.println("******");

	}

	//@AfterThrowing(pointcut = "execution(*com.demo.*(..))", throwing = "error")
	//@AfterThrowing(pointcut = "execution(*com.demo.*(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {

		System.out.println("logAfterThrowing() is running!");
		System.out.println("Method Name : " + joinPoint.getSignature().getName());
		System.out.println("Exception : " + error);
		System.out.println("******");

	}

	//@Around("execution(*com.demo.*(..))")

		System.out.println("logAround() is running!");
		System.out.println("Method method : " + joinPoint.getSignature().getName());
		System.out.println("Method arguments : " + Arrays.toString(joinPoint.getArgs()));

		System.out.println("Around before is running!");
		joinPoint.proceed();
		System.out.println("Around after is running!");

		System.out.println("******");

	}

}*/