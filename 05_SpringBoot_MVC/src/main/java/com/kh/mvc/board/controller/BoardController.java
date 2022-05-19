package com.kh.mvc.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.mvc.board.entity.Board;
import com.kh.mvc.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
    @GetMapping("/list")
    public ModelAndView list(@PageableDefault Pageable pageable, ModelAndView model) {
    	
        model.addObject("list", service.findBoardList(pageable));
        model.setViewName("board/list");
        
        return model;
    }

	@GetMapping("/view")
	public ModelAndView view(ModelAndView model, @RequestParam("no") long no) {
		log.info("게시글 상세 보기 페이지 요청");
		
		model.addObject("board", service.findBoardByNo(no));
		model.setViewName("board/view");
		
		return model;
	}
	
	@GetMapping("/update")
	public ModelAndView update(ModelAndView model, @RequestParam("no") long no) {
		log.info("게시글 수정 페이지 요청");
		
		model.addObject("board", service.findBoardByNo(no));
		model.setViewName("board/update");
		
		return model;
	}
	
	@PostMapping("/update")
	public ModelAndView update(ModelAndView model, 
				@RequestParam("no") long no,
				@RequestParam("title") String title,
				@RequestParam("writer") String writer,
				@RequestParam("content") String content) {
		log.info("게시글 수정 요청");
		
		Board board = service.update(no, title, writer, content);
		
		if(board != null && board.getNo() > 0) {
			model.addObject("msg", "게시글이 정상적으로 수정되었습니다.");
			model.addObject("location", "/board/list");
		} else {
			model.addObject("msg", "게시글이 수정을 실패하였습니다.");
			model.addObject("location", "/board/list");
		}
		
		model.setViewName("common/msg");
		
		return model;
	}
	
	@GetMapping("/write")
	public String write() {
		log.info("게시글 작성 페이지 요청");
		
		return "board/write";
	}
	
	@PostMapping("/write")
	public ModelAndView write(ModelAndView model, 
				@RequestParam("title") String title,
				@RequestParam("writer") String writer,
				@RequestParam("content") String content) {
		log.info("게시글 작성 요청");
		
		Board board = null;
		
		board = service.save(Board.builder()
								   .title(title)
								   .writer(writer)
								   .content(content)
								   .build());
		
		if(board != null && board.getNo() > 0) {
			model.addObject("msg", "게시글이 정상적으로 등록되었습니다.");
			model.addObject("location", "/board/list");
		} else {
			model.addObject("msg", "게시글이 등록을 실패하였습니다.");
			model.addObject("location", "/board/list");
		}
		
		model.setViewName("common/msg");
		
		return model;
	}
	
	@GetMapping("/delete")
	public ModelAndView delete(ModelAndView model, @RequestParam("no") long no) {
		Board board = null;
		
		board = service.delete(no);
		
		if(board == null) {
			model.addObject("msg", "게시글이 정상적으로 삭제되었습니다.");
			model.addObject("location", "/board/list");
		} else {
			model.addObject("msg", "게시글이 삭제를 실패하였습니다.");
			model.addObject("location", "/board/list");
		}
		
		model.setViewName("common/msg");
		
		return model;
	}
}
