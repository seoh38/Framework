package com.kh.mybatis.member.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.member.model.vo.Member;

public class MemberDao {

	public int getMemberCount(SqlSession session) {
		
		// mapper의 selectCount 쿼리문을 실행시키고 결과값을 리턴해준다.
		// Result, PreparedStatment를 실행하지 않고도 자동으로 실행!
		
		/*
		 *  SqlSession 객체가 제공하는 메소드를 통해서 SQL을 실행시킨다.
		 *  객체 한개를 조회하기 위해서 SqlSession 객체의 selectOne()메소드를 사용한다.
		 *  	- 첫 번째 매개값을 쿼리문이 존재하는 경로이다.("Mapper의 네임스페이스.쿼리문 아이디"
		 * 		- 두 번째 매개값은 쿼리문에서 사용될 파라미터 객체이다.
		 */
		
		// 하나의 결과를 가져오려면 selectOne() 메소드 사용
		return session.selectOne("memberMapper.selectCount");
	}

	public List<Member> findAll(SqlSession session) {
		
		// 결과에 대한 값을 List로 받고자 할 때 selectList()
		return session.selectList("memberMapper.selectAll");
	}

	public Member findMemberById(SqlSession session, String id) {
		
		
		return session.selectOne("memberMapper.selectMemberById", id);
	}

	public int insertMember(SqlSession session, Member member) {
		
		return session.insert("memberMapper.insertMember", member);
	}

	public int updateMember(SqlSession session, Member member) {

		return session.update("memberMapper.updateMember", member);
	}

	public int delete(SqlSession session, int no) {

		return session.delete("memberMapper.deleteMember", no);
	}


}
