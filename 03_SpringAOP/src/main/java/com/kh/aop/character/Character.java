package com.kh.aop.character;

import com.kh.aop.weapon.Weapon;

import lombok.Data;

@Data
public class Character {	
    // 스프링 프로퍼티 플레이스 홀더 사용!
	private String name;
	
	private int level;
	
	private Weapon weapon;
	
	public String quest(String questName) throws Exception {
		
//		if(true) {
//			throw new Exception("Quest 처리중 예외 발생");
//		}
		
		System.out.println(questName + "퀘스트 진행중....");
		
		return questName + " 퀘스트 진행중....";
	}
}
