package com.kh.di.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Configuration // 해당 클래스가 자바 설정 파일임을 선언한다.
@Import(value =  {	// .xml 에 <import>태그를 사용하는것과 같음
		OwnerConfig.class,
		PetConfig.class
})
//@ImportResource(locations = {"classpath:"})
@ComponentScan("com.kh.di") //context:component-scan 태그와 같은 역할을 한다.
public class RootConfig {
	/*
		@Bean // 별도로 빈 ID를 지정해주지 않으면 메소드명으로 ID를 지정한다.
		public Dog dog() {
			// 메소드가 리턴해주는 객체를 애플리케이션 컨텍스트 빈으로 등록해줌
			return new Dog("댕댕");
		}
		
		// 스프링 애플리케이션 컨텍스트에 빈으로 등록할 객체를 반환한다.
		@Bean("lee")
		public Owner owner() {
			
			// dog() 메소드는 빈으로 등록되어 있기 때문에 호출시마다 객체를
			// 생성하는 것이 아닌 애플리케이션 컨텍스트에서 등록된 빈 객체를 리턴한다.
			return new Owner("이서희", 22, dog());
		}
	 */
	
	/*
	 *  자바 컨피그에서는 bean으로 만들어준다.
	 *  PropertySourcesPlaceholderConfigurer라는 객체를 빈으로 등록해준다.
	 *  configurer 객체를 만들어서 실제로 등록하고 싶은 properties를 등록해준다.
	 * 
	 */
	@Bean
	public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		
		// Resource를 배열로 만들어줌
		Resource[] resources = new ClassPathResource[] {
				// 클래스 패스 기준으로 리소스를 가져옴
				new ClassPathResource("character.properties"),
				new ClassPathResource("driver.properties")
		};
		
		configurer.setLocations(resources);
		
		return configurer;
	}
}
