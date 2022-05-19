package com.kh.mybatis.board.model.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("BoardService 테스트")
class BoardServiceTest {
	private BoardService service;

	@BeforeEach
	public void setUp() throws Exception {
		service  = new BoardService();
	}
	
	@Test
	@Disabled
	public void nothing() {
	}
	
	@Test
	@Disabled
	public void create() {	
		assertThat(service).isNotNull();
	}
	
	
	@ParameterizedTest
	@CsvSource(
			value = {
				"seo, null, null, 3",
				"null, 22, null, 2",
				"null, null, 테스트, 1",
				"null, null, null, 108"},
			nullValues = "null" // 실제로 값을 null로 넣어주는 속성
			)
	@DisplayName("게시글 목록 조회(검색 기능 적용) 테스트")
	@Order(1)
	public void findAllTest(String writer, String title, String content, int expected) {
		List<Board> list = null;
		
		/*
		 * 서블릿에서는 사용자가 보내준 파라미터값을 request.getParameter()로 받아온다.
		 * 
		 * 만약 작성자를 선택 후(writer) 파라미터를 보내면 실제로 전달받은 적이 없는 title, content 는 null이 나오게!
		 */
		
//		String writer = null;
//		String title = null;
//		String content = null;
		
		// service의 findAll() 메소드를 list에 담아준다.
		// null이 아닌 비어있는 list객체를 리턴해준다.
		list = service.findAll(writer, title, content);
		
		System.out.println(list.size());
		System.out.println(list);
		
		assertThat(list).isNotNull();
		assertThat(list.size()).isPositive().isEqualTo(expected); // 예상값을 넘겨줄 수도 있음
	}
	
	@ParameterizedTest
	// @MethodSource : 외부 또는 내부 메소드에서 반환하는 값을 파라미터에서 소스로 사용하는것
	@MethodSource("filterProvider")
	@DisplayName("게시글 수 조회(필터 기능 포함)테스트")
	@Order(2)
	public void getBoardCountTest(String[] filters) {
		int count = 0;
		
		// 필터링된 listCount(전체 페이지 수)를 가져온다.
		count = service.getBoardCount(filters);
		
		System.out.println(count);
		
		assertThat(count).isPositive().isGreaterThan(0);
	}
	
	@ParameterizedTest
	@MethodSource("listProvide")
	@DisplayName("게시글 목록 조회(필터 기능 포함) 테스트")
	@Order(3)
	public void findAllTest(String[] filters, int currentPage, int expected) {
		// filter를 배열로 넘겨줌
//		String[] filters = new String[] {"B2", "B3"}; //request.getParameterValues("filters");와 같음
		List<Board> list = null;
		PageInfo pageInfo = null;
		int listCount = 0;
		
		// getBoardCountTest(String[] filters)에서 필터가 적용된 게시글을 페이징 한다.
		listCount = service.getBoardCount(filters);
		pageInfo = new PageInfo(currentPage, 10, listCount, 10); // 현재페이지, 보여질 페이지, 전체 페이지,
		list = service.findAll(filters, pageInfo);
		
		System.out.println(list);
		System.out.println(list.size());
		
		assertThat(list).isNotNull();
		assertThat(list.size()).isPositive().isEqualTo(expected);
	}
	
	@ParameterizedTest
	@ValueSource(ints = {100})
	@DisplayName("게시글 상세 조회 (댓글 포함)테스트")
	@Order(4)
	public void findBoardByNoTest(int no) {
		Board board = null;
		
		board = service.findBoardByNo(no);
		
		System.out.println(board);
		
		assertThat(board).isNotNull().extracting("no").isEqualTo(no);
		assertThat(board.getReplies()).isNotNull();
		assertThat(board.getReplies().size()).isGreaterThan(0);
	}
	
	@Test
	@DisplayName("게시글 등록 테스트")
	@Order(5)
	public void insertBoardTest() {
		int result = 0;
		Board board = new Board();
		Board findBoard = null; 
		
		board.setWriterNo(21);
		board.setTitle("mybatis 게시글");
		board.setContent("mybatis로 등록한 게시글 입니다.");
		
		// 영향받는 행의 개수를 리턴해줌
		// insert와 update작업을 해준다
		result = service.save(board);
		findBoard = service.findBoardByNo(board.getNo());
		
		assertThat(result).isGreaterThan(0);
		assertThat(findBoard).isNotNull().extracting("no").isEqualTo(board.getNo());
	}
	
	@Test
	@DisplayName("게시글 수정 테스트")
	@Order(6)
	public void updateBoardTest() {
		int result = 0;
		Board board = new Board();
		Board findBoard = null; 
		
		board = service.findBoardByNo(121);
		
		board.setTitle("mybatis 게시글 - 수정");
		board.setContent("mybatis로 등록한 게시글 입니다. - 수정");
		board.setOriginalFileName(null);
		board.setRenamedFileName(null);
		
		// 영향받는 행의 개수를 리턴해줌
		result = service.save(board);
		findBoard = service.findBoardByNo(board.getNo());
		
		assertThat(result).isGreaterThan(0);
		assertThat(findBoard).isNotNull().extracting("title").isEqualTo(board.getTitle());
	}
	
	@Test
	@DisplayName("게시글 삭제 테스트")
	@Order(7)
	public void deleteBoardTest() {
		int result = 0;
		Board board = null;
		
		result = service.delete(121);
		board = service.findBoardByNo(121); // status가 "N"으로 변경되어있기 때문에 조회안됨!
		
		assertThat(result).isPositive().isEqualTo(1);
		assertThat(board).isNull();
	}
	
	
	// @MethodSource 에서 반환을 받는 메소드!
	public static Stream<Arguments> filterProvider() {
		return Stream.of( // 스트림 객체 만들어주는 of() 메소드
				Arguments.arguments((Object) new String[]{"B2", "B3"}),
				Arguments.arguments((Object) new String[]{"B1"})
		);
	}
	
	public static Stream<Arguments> listProvide() {
		return Stream.of( 
				Arguments.arguments((Object) new String[]{"B2", "B3"}, 1, 4),
				Arguments.arguments((Object) new String[]{"B1"}, 1 ,10),
				Arguments.arguments((Object) new String[]{"B1"}, 11 ,4)
		);
	}
	
	

}
