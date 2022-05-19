package com.kh.mybatis.member.model.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import static com.kh.mybatis.common.template.SqlSessionTemplate.getSession;
import com.kh.mybatis.member.model.dao.MemberDao;
import com.kh.mybatis.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MemberService {
	private MemberDao dao = new MemberDao();

	public int getMemberCount() {
		log.info("getMemberCount() 메소드 호출");
		int count = 0;
		// JDBCTemplate의 connection 객체의 역할을 한다.
		SqlSession session = getSession();
		
		count = dao.getMemberCount(session);

		// 사용이 끝나면 항상 close를 해준다.
		session.close();
		
		return count;
	}

	public List<Member> findAll() {
		log.debug("findAll() - 호출");
		List<Member> members = null;
		SqlSession session = getSession();
		
		members = dao.findAll(session);
		
		System.out.println(members);
		
		session.close();
		
		return members;
	}

	public Member findMemberbyId(String id) {
		log.warn("findMemberById() : 경고 문구 출력");
		Member member = null;
		SqlSession session = getSession();
		
		member = dao.findMemberById(session, id);
		
		session.close();
		
		return member;
	}

	public int save(Member member) {
		// 정수형 데이터를 리턴한다
		// 쿼리문에 영향을 받은 행의 개수를 리턴하기 때문에!!
		int result = 0;
		SqlSession session = getSession();
		
		
		if(member.getNo() != 0) {
			//update
			result = dao.updateMember(session, member);
		} else {
			result = dao.insertMember(session,member);
		}
		
		if(result > 0) {
			session.commit();
		}else {
			session.rollback();
		}
		
		session.close();
		
		return result;
	}

	public int delete(int no) {
		int result = 0;
		SqlSession session = getSession();
		
		result = dao.delete(session, no);
		
		// 커밋 롤백을 수행해주어야 실제 데이터베이스에 반영이 된다.
		if(result > 0) {
			session.commit();
		}else {
			session.rollback();
		}
		
		session.close();
		
		return result;
	}

}
