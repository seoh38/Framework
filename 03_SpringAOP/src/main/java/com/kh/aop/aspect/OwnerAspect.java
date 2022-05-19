package com.kh.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.kh.aop.annotation.Repeat;

@Aspect
@Component
public class OwnerAspect {
	
//	@Around("execution(* com.kh.aop.pet.*.bark())")
//	@Around("execution(* com.kh.aop.pet.*.bark()) && !bean(dog)")
//	@Around("execution(* com.kh.aop.pet.*.bark()) && !@annotation(com.kh.aop.annotation.NoLogging)")
//	public String barkAdvice(ProceedingJoinPoint jp) {
//		String result = null;
//		
//		try {
//				System.out.println("짖어봐~");
//				
//				result = (String) jp.proceed();
//				
//				System.out.println(result);
//		} catch (Throwable e) {
//				System.out.println("안짖어도 예뻐~");
//		}
//		return result;
//	}
	
	@Around("@annotation(com.kh.aop.annotation.Repeat)")
	public String barkAdvice(ProceedingJoinPoint jp) {
	String result = null;
	MethodSignature signature = (MethodSignature)jp.getSignature();
	Repeat repeat = signature.getMethod().getAnnotation(Repeat.class);
	
//	System.out.println(repeat);
//	System.out.println(repeat.count());
	
	try {
		System.out.println("짖어봐~");
			
		result = (String)jp.proceed();
		
		for (int i = 0; i < repeat.count(); i++) {
			System.out.println(result);	
		}
			
	} catch (Throwable e) {
		System.out.println("안짖어도 예뻐~");
	}
	
	return result;
  }
	
}
