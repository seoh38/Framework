package com.kh.mvc.member.model.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.mvc.member.model.dao.MemberMapper;
import com.kh.mvc.member.model.vo.Member;

@Service // 서비스 어노테이션(서비스 레이어, 비즈니스 로직을 가진 클래스), 빈ID도 지정 가능!
//@Transactional // 클래스에 붙여도 됨
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// 세션 탬플릿에서 SqlSession을 준다
//	@Autowired
//	private SqlSession session;
	
	@Override
	public Member findMemberById(String id) {

		return mapper.findMemberById(id);
	}

	@Override
	public Member login(String id, String password) {
		Member member = null;
		
		// dao에게 session 전달!
//		member = dao.findMemberById(session, id);
		member = this.findMemberById(id);
		
		/*
		 *  평문 패스워드와 암호화된 패스워드가 서로 같다라는 사실이 증명되어야 로그인을 구현할 수 있다.
		 *  이 때 이 두 문자열을 비교해주는 메소드가 matches()이다.
		 */
//		System.out.println(passwordEncoder.encode(password)); // password를 한번 더 encoding...
//		System.out.println(passwordEncoder.encode(password)); // 암호화된 결과가 다 다르다.
//		System.out.println(member.getPassword());
//		System.out.println(passwordEncoder.matches(password, member.getPassword()));
//		
//		
//		if (member != null && member.getPassword().equals(passwordEncoder.encode(password))) {
//			return member;
//		} else {
//			return null;
//		}
		
		return member != null && 
				passwordEncoder.matches(password, member.getPassword()) ? member : null;
	}
	
	@Override
	@Transactional  // 트랜젝션을 사용할 메소드에만 붙이는 것이 좋다.
	public int save(Member member) {
		int result = 0;
		
		if (member.getNo() != 0) {
			//update		
			result = mapper.updateMember(member);
		} else {
			// password 암호화
			member.setPassword(passwordEncoder.encode(member.getPassword()));
			result = mapper.insertMember(member);
		}

//		if (true) {
//			throw new RuntimeException();
//		} 
		
		return result;
	}

	@Override
	public Boolean isDuplicateID(String id) {
		
		return this.findMemberById(id) != null;
	}

	@Override
	public int delete(int no) {
		
		
		return mapper.deleteMember(no);
	}


}
