package com.kh.di.pet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Dog implements Pet {
	@Value("갱얼쥐")
	private String name;
	
	@Override
	public String bark() {
		
		return "멍멍";
	}

}
