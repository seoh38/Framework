package com.kh.mvc;

import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kh.mvc.board.entity.Board;
import com.kh.mvc.board.repository.BoardRepository;

@SpringBootApplication
public class Application {

	// 메인 함수 존재(자바 애플리케이션 실행시 가장 먼저 실행!)
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
		
	@Bean 
	public CommandLineRunner runner(BoardRepository repository) {
		// CommandLineRunner : 애플리케이션 구동 후 특정 코드를 실행시키고 싶을 때 구현하는 인터페이스이다. 
		
		return (args) -> {
			IntStream.rangeClosed(1, 200).forEach(index ->
				repository.save(Board.builder()
						  .writer("ismoon")
						  .title("게시글 작성 테스트 " + index)
						  .content("안녕하세요. 게시글 작성 테스트 중 입니다.")
						  .build())
			);

		};
	}

}
