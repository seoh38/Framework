package com.kh.mvc.member.model.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kh.mvc.member.model.vo.Member;

/*
 *  1) 네임스페이스를 패키지명 포함하여 변경
 *  2) 매퍼 id를 메소드명과 동일하게 변경!
 *  	- 인터페이스와 연결되어있는 xml의 메소드와 동일한 이름을 가지는 쿼리문 아이디!
 */
@Mapper
public interface MemberMapper {
//	@Select("select * from member")
//	List<Member> findAll();

	Member findMemberById(@Param("id") String id);

	int insertMember(Member member);

	int updateMember(Member member);

	int deleteMember(int no);
	
	
	
}
