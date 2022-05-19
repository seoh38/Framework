package com.kh.di.character;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.kh.di.config.RootConfig;

@ExtendWith(SpringExtension.class) // 스프링 기능을 사용할 수 있도록 확장
//@ContextConfiguration(locations = "classpath:spring/root-context.xml") // 설정파일을 지정
@ContextConfiguration(classes = RootConfig.class)
class CharacterTest {
	
	// required는 빈 주입이 필수로 징행되어야 하는지 설정하는 옵션이다.
	// required가 true 일 경우 주입해야되는 빈이 애플리케이션 컨텍스트에 존재하지 않으면 Exception이 발생한다.
	// required가 false일 경우 주입해야 되는 빈이 애플리케이션 컨텍스트에 존재하지 않으면 null을 주입한다.
	@Autowired(required = false) // 자동으로 빈을 주입받는 어노테이션(빈이 없으면 에러발생)
	private Character character;

	@Value("${character.name:메르세데스}")
	private String name;
	
	@Value("${character.level:99}")
	private int level;
	
	@Value("${db.driver}")
	private String driver;
	
	@Value("${db.url}")
	private String url;
		
	@Test
//	@Disabled
	void test() {
		// 테스트에 성공하면 xml 파일을 읽어서 애플리케이션 컨텍스트를 만들고 사용할 준비가 되어있다는 뜻!
		
		System.out.println(driver);
		System.out.println(url);
		
		assertThat(driver).isNotNull().isEqualTo("oracle.jdbc.driver.OracleDriver");
		assertThat(url).isNotNull().isEqualTo("jdbc:oracle:thin:@localhost:1521:xe");	
	}
	
	@Test
	public void create() {
		
		System.out.println(character);
		
		assertThat(character).isNotNull();
		assertThat(character.getName()).isNotNull().isEqualTo(name);
		assertThat(character.getLevel()).isPositive().isGreaterThan(0).isEqualTo(level); // 숫자니까 .isPositive().isGreaterThan(0)
		assertThat(character.getWeapon()).isNotNull();
		
	}

}
