package com.kh.mybatis.member.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.kh.mybatis.member.model.vo.Member;

@DisplayName("MemberService 테스트")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 순서를 정해주기!
class MemberServiceTest {
	private MemberService service;
	
	@BeforeEach
	public void setUp() {
		service = new MemberService();	
		
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
	
	@Test
	@DisplayName("회원 수 조회 테스트")
	@Order(1)
	public void getMemberCountTest() {
		int count = service.getMemberCount();
		
		// 양수이고 2보다 크거나 같은 정수값이면 테스트 성공! 아니면 실패!
		assertThat(count).isPositive().isGreaterThanOrEqualTo(2);
	}
	
	// member의 모든 회원을 조회해오는 findAllTest() 메소드 만들기
	@Test
	@DisplayName("모든 회원 조회 테스트")
	@Order(2)
	public void findAllTest() {
		List<Member> members = null; 
		
		members=service.findAll();
		
		assertThat(members).isNotNull() // members가 isNotNull()인지!
						   .isNotEmpty()
						   .extracting("id")
						   .isNotNull() // 추출된 id가 isNotNull() 인지!
						   .contains("seoh38","admin2");
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"seoh38", "admin2"})
	@DisplayName("회원 조회 테스트(ID로 검색)")
	@Order(3)
	public void findMmberByIdTest(String userId) {
		
		Member member = service.findMemberbyId(userId);
		
		assertThat(member).isNotNull()
						  .extracting("id")
						  .isEqualTo(userId);
	}
	
	@ParameterizedTest
	@CsvSource({ //여러개의 매가값을 넘겨주고 싶을때 사용
		"test1, 1234, 제노",
		"test2, 4567, 마크",
	})
	@DisplayName("회원 등록 테스트")
	@Order(4)
	public void insertMemberTest(String id, String password, String name) {
		// 정수값을 받기 때문에!
		int result = 0;
		Member findMember = null;
		Member member = new Member(id, password, name);
		
		result = service.save(member);
		
		// 실제로 DB에 Member가 저장되었는지 확인하기 위해서 다시 Member를 조회(Id로 조회)
		findMember = service.findMemberbyId(id);
		
		System.out.println(member.getNo());
		
		assertThat(result).isGreaterThan(0);
		assertThat(member.getNo()).isGreaterThan(0);
		assertThat(findMember).isNotNull().extracting("name").isEqualTo(name);
	}
	
	@ParameterizedTest
	@CsvSource({ //여러개의 매가값을 넘겨주고 싶을때 사용
		"test1, 5678, 이도",
		"test2, 0000, 정조",
	})
	@DisplayName("회원 정보 수정 테스트")
	@Order(5)
	public void updateMemberTest(String id, String password, String name) {
		int result = 0;
		Member member = null;
		Member findMember = null;
		
		member = service.findMemberbyId(id);
		
		member.setPassword(password);
		member.setName(name);
		
		result = service.save(member);
		
		// 실제로 DB에 Member가 수정되었는지 확인하기 위해서 다시 Member를 조회(Id로 조회)
		findMember = service.findMemberbyId(id);
		
		assertThat(result).isGreaterThan(0);
		assertThat(findMember).isNotNull().extracting("name").isEqualTo(name);
		assertThat(findMember).isNotNull().extracting("password").isEqualTo(password);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"test1", "test2"})
	@DisplayName("회원 삭제 테스트")
	@Order(6)
	public void deleteTest(String id) {
		int result = 0;
		Member findMember = null;
		Member member = service.findMemberbyId(id);
		
		// PK를 조건으로 delete 하기 때문에 하나의 행이 삭제됨
		result = service.delete(member.getNo());
		
		// 실제로 DB에 Member가 삭제되었는지 확인하기 위해서 다시 Member를 조회(Id로 조회)
		findMember = service.findMemberbyId(id);
		
		assertThat(result).isPositive().isEqualTo(1);
		// 전부 삭제 되면 null이 나오는지 확인!
		assertThat(findMember).isNull();
		
	}

}
