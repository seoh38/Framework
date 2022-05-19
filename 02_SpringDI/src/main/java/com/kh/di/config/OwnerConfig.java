package com.kh.di.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kh.di.owner.Owner;
import com.kh.di.pet.Pet;

@Configuration // 설정 파일임을 선언, 애플리케이션 컨텍스트만 사용하는 자바
public class OwnerConfig {
	
	// 스프링 애플리케이션 컨텍스트에 빈으로 등록할 객체를 반환한다.
		@Bean("lee")
		public Owner owner(@Autowired @Qualifier("dog") Pet pet) {
			
			// Pet 매개값에 객체를 찾아 ID가 dog인 빈이 있으면 반환해줌!
			
			// dog() 메소드는 등록되어있기 때문에 호출 시 마다 객체를
			// 생성하는 것이 아닌 애플리케이션 컨텍스트에서 등록된 빈 객체를 리턴한다.
			
			return new Owner("이서희", 22, pet);
		}
		
		@Bean("hong")
		public Owner owner2(/* @Autowired(생략 가능) */ @Qualifier("ray") Pet pet) { 
			
			// @Autowired는 매개값에 객체가 있으면 Pet에 빈이 있으면 생략가능하다.
			// 여러개가 있어서 구분을 해주고 싶다면 @Qualifier()로 ID를 지정해준다.
			
			return new Owner("홍길동", 500, pet);
		}
		

}
