package com.kh.mybatis.board.model.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.mybatis.board.model.vo.Board;
import com.kh.mybatis.common.util.PageInfo;

public class BoardDao {
	public int getBoardCount(SqlSession session, List<String> filters) {
		
		return session.selectOne("boardMapper.getBoardCountByFilters", filters);
	}

	// 파라미터값을 여러 개 넘겨줌
	public List<Board> findAll(SqlSession session, String writer, String title, String content) {
		// 여러개의 값을 넘길때 컬렉션 객체로 값을 넘길 수 있다.
		Map<String, String> map = new HashMap<>();
		
		// 쿼리문의 키 값으로 값을 불러온다.
		map.put("writer", writer);
		map.put("title", title);
		map.put("content", content);
		
		return session.selectList("boardMapper.selectAll", map);
	}

	public List<Board> findAll(SqlSession session, String[] filters, PageInfo pageInfo) {
		/*
		 *  List 타입이나 Array 타입의 데이터를 파라미터로 전달하면 내부적으로 Map으로 타입이 변환되어 저장되기 때문에
		 *  Mapper에서는 list나 array라는 이름으로 사용해야 한다.
		 *   session.selectList("boardMapper.selectBoardListByFilters", filters);
		 *   
		 *   Mapper.xml
		 *   	<if test="array != null">
		 *   	...
		 *   	</if>
		 * 만약에 filters라는 이름을 Mapper애서 사용하고 싶으면 Map 객체에 담아서 파라미터로 전달한다.  
		 */
		
		Map<String, Object> map = new HashMap<>();
		
		// filters라는 이름으로 그대로 쓰고 싶으면 Map객체 선언 후 map타입으로 넘겨준다.
		map.put("filters", filters);
		
		/*
		 * 기존에 Servlet/JSP에서는 쿼리문에서 rownum 컬럼과 서브쿼리를 통해서 페이징 처리를 했음
		 * 하지만 mybatis에서는 페이징 처리를 위해 RowBounds라는 클래스를 제공해준다. 
		 * 
		 * RowBounds의 객체를 생성할 때 offset과 limit 값을 전달해서 페이징 처리를 쉽게 구현한다.
		 * 	offset : 몇만큼 건너뛸건지 
		 *  limit : 몇만큼 가져올건지
		 *  
		 *  offset = 0, limit = 10
		 *  	- 0개를 건너뛰고 10개를 가져온다. (1 ~ 10)
		 *  offset = 10, limit = 10
		 *  	- 10개를 건너뛰고 10개를 가져온다. (11 ~ 20)
		 *  offset = 20, limit = 10
		 *  	- 20개를 건너뛰고 10개를 가져온다. (21 ~ 30)
		 *  
		 */
		
		// offset를 계산! 현재페이지에서 -1을 하고 10을 곱함
		// 1 페이지 일때는 오프셋이 1이 나오게 해줌 (1 - 1) * 10을 하는것과 같음!
		int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getListLimit(); // 10을 곱함
		int limit = pageInfo.getListLimit();
 		RowBounds rowBounds =  new RowBounds(offset, limit);
		
		 
		return session.selectList("boardMapper.selectBoardListByFilters", map, rowBounds);
	}
  
	public Board findBoardByNo(SqlSession session, int no) {
		
		return session.selectOne("boardMapper.selectBoardByNo", no);
	}

	public int insertBoard(SqlSession session, Board board) {
		
		return session.insert("boardMapper.insertBoard", board);
	}

	public int updateBoard(SqlSession session, Board board) {
		
		
		return session.update("boardMapper.updateBoard", board);
	}

	public int updateStatus(SqlSession session, int no, String status) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("no", no);
		map.put("status", status);
		
		// 다오에서 내부적인 쿼리는 updae임!
		return session.update("boardMapper.updateStatus", map);
	}

	

}
