package com.kh.mvc.board.model.service;

import java.util.List;

import com.kh.mvc.board.model.vo.Board;
import com.kh.mvc.common.util.PageInfo;

public interface BoardService {

	int getBoardCount();

	List<Board> getBoardList(PageInfo pageInfo);

	int save(Board board);

	Board findBoardByNo(int no);

	int delete(int no);
	

}
