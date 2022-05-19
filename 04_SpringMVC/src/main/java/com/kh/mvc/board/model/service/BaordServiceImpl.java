package com.kh.mvc.board.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.mvc.board.model.dao.BoardMapper;
import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.common.util.PageInfo;

@Service
public class BaordServiceImpl implements BoardService {
	@Autowired
	private BoardMapper mapper;

	@Override
	public int getBoardCount() {
		
		return mapper.getBoardCount();
	}

	@Override
	public List<Board> getBoardList(PageInfo pageInfo) {
		int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getListLimit(); // 10을 곱함
		int limit = pageInfo.getListLimit();
 		RowBounds rowBounds =  new RowBounds(offset, limit);
		
		return mapper.findAll(rowBounds);
	}

	@Override
	public Board findBoardByNo(int no) {
		
		return mapper.selectBoardByNo(no);
	}
	
	@Override
	@Transactional // 알아서 commit, rollback 해줌!
	public int save(Board board) {
		int result = 0;
		
		if (board.getNo() != 0) {
			// update
			result = mapper.updateBoard(board);
		} else {
			// insert
			result = mapper.insertBoard(board);
		}
		return result;
	}

	@Override
	@Transactional
	public int delete(int no) {
		
		return mapper.updateStatus(no, "N");
	}


}
