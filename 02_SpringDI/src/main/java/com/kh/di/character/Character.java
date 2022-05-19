package com.kh.di.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.kh.di.weapon.Weapon;

import lombok.Data;

/*
 *  @Component :스프링에게 해당 클래스는 빈으로 만들어져야 한다고 알려주는 역할을 한다.
 *  	
 *  	- 설정 파일(xml)에 <context:component-scan>를 통해서 
 *  		@Component이 붙어있는 클래스를 빈으로 생성할수 있게끔 활성화 해주는 역할을 한다.	
 *  	
 * 	@Value : setter가 없어도 값을 직접 주입이 가능하다(자바 리플렉션)
 * 			 Reflection을 사용해서 스프링에서는 런타임 시에 개발자가 등록한 빈을 애플리케이션에서 가져와 사용할 수 있게 되는 것입니다.
 * 
 * 	@PropertySource 어노테이션에 properties 파일의 값을 읽어오는 방법
 * 
 * 		1. Environment 객체에 빈이 있으면! 프로퍼티 값이 자동으로 주입해준다. (애플리케이션 컨텍스트의 내장빈)
 * 		2. 스프링 프로퍼티 플레이스 홀더 사용!(${프로퍼티의 key값:기본값}) 
 * 		   만약 설정해 놓은 키의 값이 없으면 기본키를 설정할 수도 있다.
 */

@Data
@Component // 빈 생성시 별도의 ID를 지정해 주지 않으면 클래스 이름에서 앞글자를 소문자로 바꾼 ID를 갖는다.
//@PropertySource("classpath:character.properties")

//@PropertySource 어노테이션을 생략후 스프링 프로퍼티 플레이스 홀더를 통해서 properties 파일의 값을 읽어오는 방법
// 1. xml 설정 파일에 경우 <context:property-placeholder /> 추가
// 2. java 설정 파일에 경우 
public class Character {	
    // 스프링 프로퍼티 플레이스 홀더 사용!
	@Value("${character.name:메르세데스}")
	private String name;
	
	@Value("${character.level:99}")
	private int level;
	
	@Autowired // 빈이 존재하면! 자동으로 주입
	@Qualifier("windForce")
	private Weapon weapon;
	
	
//	Environment 객체를 통해서 getProperty() 메소드를 사용해 값을 가져오기
//	public Character(/* @Autowried */Environment env) {
//		this.name = env.getProperty("character.name");
//		this.level = Integer.parseInt(env.getProperty("character.level"));
//		
//	}
	
}
