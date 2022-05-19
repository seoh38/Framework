package com.kh.mybatis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * Hello world!
 *
 */

@Slf4j
public class App {
// @Slf4j으로 인해서 아래의 선언문은 생략이 가능하다.
//	private static Logger log = LoggerFactory.getLogger(App.class);
	
    public static void main( String[] args ) {
    	log.info("로깅 테스트");
    	
        System.out.println( "Hello World!" );

    }
}
