package com.kh.aop.pet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kh.aop.annotation.NoLogging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Cat implements Pet {
	@Value("고영희")
	private String name;
	
	@Override
	@NoLogging
	public String bark() throws Exception {
		
		// 예외 발생
//		if (true) {
//			throw new Exception();
//		}
//		
		return "매웅";
	}

}
