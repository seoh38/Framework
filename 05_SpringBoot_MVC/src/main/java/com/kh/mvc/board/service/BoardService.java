package com.kh.mvc.board.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kh.mvc.board.entity.Board;
import com.kh.mvc.board.repository.BoardRepository;

@Service
public class BoardService {
	@Autowired
	private BoardRepository repository;
	
	public Page<Board> findBoardList(Pageable pageable) {
		pageable = PageRequest.of(
				pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, 
				pageable.getPageSize(),
				Sort.by("no").descending());
		
		return repository.findAll(pageable);
	}
	
	public Board findBoardByNo(long no) {
		
		return repository.findById(no).orElse(null);
	}

	@Transactional
	public Board save(Board board) {
		
		return repository.save(board);
	}

	@Transactional
	public Board update(long no, String title, String writer, String content) {
		Board board = null;
		Optional<Board> optional = repository.findById(no);
		
		if(optional.isPresent()) {
			board = optional.get();
			board.update(title, writer, content);
		}
		
		return board;
	}
	
	@Transactional
	public Board delete(long no) {
		
		repository.deleteById(no);
		
		return repository.findById(no).orElse(null);
	}
}
