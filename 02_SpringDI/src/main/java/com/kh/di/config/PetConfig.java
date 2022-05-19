package com.kh.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kh.di.pet.Cat;
import com.kh.di.pet.Dog;

@Configuration
public class PetConfig {
	@Bean // 별도로 빈 ID를 지정해주지 않으면 메소드명으로 ID를 지정한다.
	public Dog dog() {
		// 메소드가 리턴해주는 객체를 애플리케이션 컨텍스트 빈으로 등록해줌
		return new Dog("댕댕");
	}
	
	@Bean("ray")
//	@Primary // <bean primary="true" /> 와 같다.
	public Cat cat() {
		// 어떤 형태든 객체를 리천해주면 된다.
		Cat cat = new Cat();
		cat.setName("레이");
		
		return cat;
	}
	
}
