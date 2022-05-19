package com.kh.di.pet;

/*
 *  
 *  Java에서는 다형성과 생성자 주입을 통해 객체간의 결합도를 느슨하게 만들어준다.
 *  
 *  interface를 만들어 Dog, Cat 메소드가 상속을 받게 해준다.
 * 
 */

public interface Pet {
	public String bark(); // 추상메소드

}
