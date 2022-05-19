package com.kh.di.owner;

import com.kh.di.pet.Pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
	private String name;
	
	private int age;
	
	// 다형성을 통해서 상속하고 있는 타입의 변수로 cat와 dog를 담아줄 수 있음
	private Pet pet;
}
