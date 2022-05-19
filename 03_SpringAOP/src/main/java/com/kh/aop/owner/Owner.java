package com.kh.aop.owner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kh.aop.pet.Pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Owner {
	@Value("이서희")
	private String name;
	
	@Value("20")
	private int age;
	
	// 다형성을 통해서 상속하고 있는 타입의 변수로 cat와 dog를 담아줄 수 있음
	@Autowired
	@Qualifier("dog")
	private Pet pet;
}
