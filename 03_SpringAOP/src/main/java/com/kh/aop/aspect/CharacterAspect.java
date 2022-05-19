package com.kh.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect // 일반적인 자바 클래스가 아니라 에스펙트임을 나타낸다.
public class CharacterAspect {
	// 포인트 커트를 한 번만 정의하고 필요할 때마다 참조할 수  있는 @Pointcut 어노테이션을 제공한다.
	@Pointcut("execution(* com.kh.aop.character.Character.quest(..)) && args(questName)")
	public void questPointCut(String questName) {
	}
	
//	@Before("execution(* com.kh.aop.character.Character.quest(..))") 
	public void beforeQuest(JoinPoint jp) {
		// getSignature() : 실제 메소드의 정보를 가지고 올 수 있다.
		System.out.println(jp.getSignature().getName());
		System.out.println(jp.getSignature().getDeclaringType());
		
		System.out.println(jp.getArgs()[0] + "퀘스트 준비중....");
	}
	
	// 파라미터로 넘기는 값이 매개값의 이름과 같아야한다. -> args(questName) 
//	@After("execution(* com.kh.aop.character.Character.quest(..)) && args(questName)") 
	public void afterQuest(String questName) {
//		퀘스트 수행 결과에 상관없이 수행 후에 필요한 부가 작업을 수행한다.	
		System.out.println(questName + "퀘스트 수행 완료....");
	}
	
//	@AfterReturning(
//			value = "questPointCut(questName)",
//			returning = "result"
//	)
	public void questSuccess(String questName, String result) {
		// 퀘스트 수행 완료 후에 필요한 부가 작업을 수행한다.
		System.out.println(result);
		System.out.println(questName + "퀘스트 수행 완료...");
	}
	
//	@AfterThrowing(
//			value = "questPointCut(questName)",
//			throwing = "exception" 
//	)
	public void questFail(String questName, Exception exception) {
//		퀘스트 수행 중에 예외를 던졌을 때 필요한 부가 작업을 수행한다.
		System.out.println(exception.getMessage());
		
		System.out.println("에러가 발생하였습니다. 다시 시도해 주세요.");
	}
	
	@Around("execution(* com.kh.aop.character.Character.quest(..))")
	public String questAdvice(ProceedingJoinPoint jp) { // around-advice는 ProceedingJoinPoint를 매개값으로 필수로 가져야한다!
		String result = null; 
		String questName = "[" + (String) jp.getArgs()[0] + "]";
				
		try {
			// before 어드바이스에 대한 기능을 수행!
			System.out.println("퀘스트 준비중.....");
			
			// 실제 타겟 객체의 메소드를 실행시킨다.
//			jp.proceed();
		
			// 타겟 객체의 메소드에 리턴값이 있을 경우
			// proceed()는 object 형태로 반환해주기 때문에 String로 형변환 해준다.
//			result = (String) jp.proceed();
			
			// 타겟 객체의 메소드에 파라미터 값을 변경해서 전달해줄 경우
			result = (String) jp.proceed(new Object[] {questName}); // Object[]를 만들어서 배열로 넘겨준다.
			
			// after-returning 어드바이스에 대한 기능을 수행
			System.out.println("퀘스트 수행 완료...");
		} catch (Throwable e) {
			
			// after-throwing 어드바이스에 대한 기능을 수행!
			System.out.println(e.getMessage());
			System.out.println("에러가 발생하였습니다. 다시 시도해 주세요.");
		}
		
		return result;
	}
	
	// 실습 문제
	// Sword, Bow의 attack() 메소드 실행 시 
	// @Around 어드바이스를 사용하여 코드 작성 하세요.
	// 1. attack() 메소드 정상 동작 시
	//			공격을 준비중 입니다...
	//			"검을 휘두른다." or  '민첩하게 활을 쏜다."
	//			공격을 성공했습니다.
	// 2. attack() 메소드 실행중에 예외 발생 시
	//			공격을 준비중입니다.
	//			공격에 실패했습니다.......
	
	// 문자열을 리턴함!
	// weapon패키지 안에 모든 클래스의 attack()메소드를 어드바이스에 포인트컷으로 만들고 싶다면! "*"을 넣어준다.
	@Around("execution(* com.kh.aop.weapon.*.attack())")
	public String attackAdvice(ProceedingJoinPoint jp) {
		String result = null; 
		
		try {
			// before 어드바이스
			System.out.println("공격을 준비중 입니다.....");
			
			// 타겟 객체의 메소드호츌해주고 result에 값을 담아줌!
			result= (String) jp.proceed();
			
			// result의 값을 출력!
			System.out.println(result);
			
			// after-returning 어드바이스
			System.out.println("공격을 성공했습니다....");
		} catch (Throwable e) {
			// after-throwing 어드바이스
			System.out.println("에러가 발생했습니다....");
		}
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
