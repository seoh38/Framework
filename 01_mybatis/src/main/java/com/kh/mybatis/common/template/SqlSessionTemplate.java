package com.kh.mybatis.common.template;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionTemplate {
	public static SqlSession getSession() {
		InputStream is = null;
		SqlSession session =  null;
		SqlSessionFactoryBuilder builder = null;
		SqlSessionFactory factory = null;
				
		try {
			// Resources는 mybatis에서 제공해주는 util 클래스
			// 클래스패스로부터 자원(Resource)를 쉽게 읽어오는 메소드를 제공한다.
			is = Resources.getResourceAsStream("mybatis-config.xml");
			
			// 빌더 
			builder = new SqlSessionFactoryBuilder();
			
			// factory 객체가 DataSource를 참조하여 MyBatis와 sql 서버를 연동
			factory = builder.build(is);
			
			// default 값으로 설정한 객체를 가지고 싶지 않으면 뒤에 이름을 붙여준다.
//			factory = builder.build(is, "kh");
			
			// true : 오토커밋 활성, false : 오토커밋 비활성화
			session = factory.openSession(false);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return session;
	}

}
