package com.kh.di.owner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.di.config.OwnerConfig;
import com.kh.di.config.PetConfig;
import com.kh.di.config.RootConfig;
import com.kh.di.pet.Cat;
import com.kh.di.pet.Dog;

// JUNIT에서 스프링을 사용할 수 있도록 SpringExtension.class를 사용하여 기능을 확장한다.
// 해당 설정이 있어야 @ContextConfiguration()를 통해서 설정 파일을 읽고 applicationcontext를 내부에서 생성할 수 있다.
@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"classpath:spring/root-context.xml"})
@ContextConfiguration(classes = {RootConfig.class}) // 이 방식을 쓰도록 하자...!
//@ContextConfiguration(classes = {
//		OwnerConfig.class,
//		PetConfig.class
//})
class OwnerTest {
	/*
	 *  애플리케이션 컨텍스트 상에서 클래스 타입과 일치하는 빈을 자동으로 주입시켜준다.
	 *  이때 동일한 클래스 타입에 빈이 여러개가 존재할 경우 @Qualifier("빈 ID)를 명시적으로 넣어주어야한다.
	 *  
	 * 	@Autowired : 빈을 자동으로 주입해주는 어노테이션 
	 * 	@Qualifier : Qualifier를 지정해서 어떤 객체를 주입할건지 구분해줌
	 * 
	 */
	@Autowired
	@Qualifier("lee")
	private Owner owner; // 필드 선언

	@Test
	@Disabled
	public void nothing() {
	}
	
	@Test
	public void create() {
		// 기존에 자바 애플리케이션에서는 인터페이스(추상클래스)와 생성자주입을 통해 객체간의 결합을 느슨하게 만들어 준다.
		Owner owner = new Owner("이서희", 27, new Cat("오애옹"));
		
		assertThat(owner).isNotNull();
		assertThat(owner.getPet()).isNotNull();
	}
	
	@Test
	public void contextTest() {
		// 스프링의 애플리케이션 컨텍스트를 활용해서 객체간의 결합을 더욱 느슨하게 만들어준다.
		// root-context.xml을 사용하려면 classes 폴더안에 있어야함
		// web-app안에 있는 root-context.xml는 웹에서 사용하는 것이기때문에 실제로 사용하려면 target에도 들어가 있어야함
		
		// new GenericXmlApplicationContext("클래스패스 상의 xml 파일의 위치 지정")
		// ApplicationContext가 GenericXmlApplicationContext보다 상위에 있다.
		ApplicationContext context = 
//				new GenericXmlApplicationContext("spring/root-context.xml");
//				new GenericXmlApplicationContext("spring/root-context.xml", "spring/pet-context.xml" );
//				new GenericXmlApplicationContext("classpath:spring/root-context.xml");
//				new GenericXmlApplicationContext("file:src/main/resources/spring/root-context.xml");
				
				// 자바 설정을 읽어오는 AnnotationConfigApplicationContext() 메소드를 실행
				new AnnotationConfigApplicationContext(RootConfig.class);
				
		
//		Owner owner = (Owner)context.getBean("owner");
//		Owner owner = context.getBean("hong", Owner.class); 
		Owner owner = context.getBean("lee", Owner.class); // getBean()으로 id를 넘겨준다.
		
		System.out.println(owner);
		
		assertThat(owner).isNotNull();
		assertThat(owner.getPet()).isNotNull();
	}
	
	@Test
	public void autoWriedTest() {
		System.out.println(owner);
		System.out.println(owner.getPet().bark());
		
		assertThat(owner).isNotNull();
		assertThat(owner.getPet()).isNotNull();
		assertThat(owner.getPet().bark()).isNotNull().isEqualTo("멍멍");
	}
	
	

}
