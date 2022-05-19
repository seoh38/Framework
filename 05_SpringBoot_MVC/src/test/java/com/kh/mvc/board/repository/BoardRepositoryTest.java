package com.kh.mvc.board.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.kh.mvc.board.entity.Board;

@DataJpaTest
class BoardRepositoryTest {
	@Autowired
	private BoardRepository repository;
	
	@Test
	void create() {
		assertThat(repository).isNotNull();
	}
	
	@Test
	void findAllTest() {
		List<Board> boardList = repository.findAll();
		
		assertThat(boardList).isNotNull();	
		assertThat(boardList.isEmpty()).isFalse();		
		assertThat(boardList.size()).isPositive().isGreaterThan(0);	
	}
	
	@Test
	void findByIdTest() {
		Board board = repository.findById(100L).orElse(null);
		
		assertThat(board).isNotNull();
		assertThat(board.getNo()).isNotNull().isPositive().isGreaterThan(0);
	}

	@Test
	void saveTest() {
		Board board = Board.builder()
						   .writer("ismoon")
						   .title("게시글 작성 테스트")
						   .content("안녕하세요. 게시글 작성 테스트 중 입니다.")
						   .createDate(LocalDateTime.now())
						   .modifyDate(LocalDateTime.now())
						   .build();
		
		board = repository.save(board);
		
		assertThat(board.getNo()).isPositive().isGreaterThan(0);
		assertThat(repository.findById(board.getNo())).isNotNull();
	}
	
	@Test
	void findByWriter() {
		Board board = null;
		
		board = repository.save(Board.builder()
								     .writer("admin")
								     .title("게시글 작성 테스트")
								     .content("안녕하세요. 게시글 작성 테스트 중 입니다.")
								     .createDate(LocalDateTime.now())
								     .modifyDate(LocalDateTime.now())
								     .build());
		
		assertThat(repository.findByWriter(board.getWriter())).isNotNull().extracting("writer").isEqualTo(board.getWriter());
	}
}
