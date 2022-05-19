package com.kh.aop.weapon;

import lombok.Data;

@Data
public class Bow implements Weapon {
	private String name;
	
	@Override
	public String attack() throws Exception {
		
//		if(true) {
//			throw new Exception();
//		}

		return "민첩하게 활을 쏜다.";
	}

}
