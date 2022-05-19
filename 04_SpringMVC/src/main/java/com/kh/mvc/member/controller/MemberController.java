package com.kh.mvc.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.kh.mvc.member.model.service.MemberService;
import com.kh.mvc.member.model.service.MemberServiceImpl;
import com.kh.mvc.member.model.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller // 웹 요청과 응답을 처리하는 클래스!
//Model 객체에 loginMember라는 키값으로 객체가 추가되면 해당 객체의 scope가 session까지 확장된다.
@SessionAttributes("loginMember") 
public class MemberController {
	/*
	 * 
	// 기본적으로는 get 요청, method 속성으로 post방식으로 설정도 가능하다.
	// 컨트롤러가 처리할 요청을 정의한다.(URL, Method 등)
//	@RequestMapping(value="/login", method = {RequestMethod.POST})
	@GetMapping("/login")
	public String login() {
		
		log.info("login() - 호출");
		
		return "home";
	}
	
	사용자의 파라미터를 전송받는 방법
	
	1. HttpServletRequest를 통해서 전송받기(기본 JSP/Servlet 방식) 
		- 메소드의 매개변수로 HttpServletRequest를 작성하면 메소드 실행 시 스프링 컨테이너가 자동으로 객체를 인자로 주입해준다.	
	
	@PostMapping("/login")
	public String login(HttpServletRequest request) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		log.info("login() - 호출 : {} {}", id, password);
		
		return "home";
	}
	
	2-1. @RequestParam 어노테이션을 통해 전송받기
		- 스프링에서 좀 더 간편하게 파라미터를 받아올 수 있는 방법 중 하나이다.
		- 내부적으로는  Request 객체를 이용해서 데이터를 전송받는 방법이다.
		- 단 매개변수의 이름과 name 속성의 값이 동일하게 설정된 경우 자동으로 주입된다.
			(어노테이션을 사용하는 것이 아니기 때문에 defaultValue 설정이 불가능하다.)
			
	@RequestMapping(value = "login", method = {RequestMethod.POST})
//	public String login(@RequestParam("id") String id, @RequestParam("password")String password) {
	public String login(String id, String password) {
		
		log.info("login() - 호출 : {} {}", id, password);
		
		return "home";
	}
	
	2-2. @RequestParam에 default 값 설정
	     -  defaultValue속성을 사용하면 파라미터 name 속성에 값이 없을 경우 기본값을 지정할 수 있다.
			
	
	@PostMapping("/login")
	public String login(@RequestParam("id") String id, 
						@RequestParam(value = "password", defaultValue = "0000")String password) {
		
		log.info("login() - 호출 : {} {}", id, password);
		
		return "home";
	}
	
	2-3. @RequestParam에 실제 존재하지 않는 파라미터를 받으려고 할 때
		- 파라미터 name 속성에 없는 값이 넘어올 경우 에러가 발생한다.
		- @RequestParam(required = false)로 지정하면 null값을 넘겨준다.
		- 단, defaultValue로 기본값을 설정하면 defaultValue에 설정된 값으로 넘겨준다.(에러가 발생하지 않음)
		
	@PostMapping("/login")
	public String login(@RequestParam("id") String id, 
						@RequestParam("password")String password,
//						@RequestParam(value ="address", required = false)String address) {
						@RequestParam(value ="address", defaultValue = "서울특별시")String address) {
		
		log.info("login() - 호출 : {} {} {}", new Object[] {id, password, address});
		
		return "home";
	}
	
	3. @PathVariable 어노테이션을 통해서 전송받기
		- URL패스 상에 있는 특정 값을 가져오기 위해 사용하는 방법이다.
		- REST API를 사용할 때, 요청 URL 상에서 필요항 값을 가져오는 경우 주로 사용한다.
		- 매핑 URL에 {}로 묶는다면, {} 안의 값을 Path Variable로 사용하고 요청시 실제 경로상의 값을 해당 Path Variable로 받겠다는 의미이다.
		- 매핑 URL에 {} 안의 변수명과 매개변수의 변수명이 동일하다면 @PathVariable의 괄호는 생략이 가능하다.(어노테이션 자체는 생략이 안된다)
	
	@GetMapping("/member/{id}")
//	public String findMember(@PathVariable("id")String id) {
	public String findMember(@PathVariable String id) {
		
		log.info("Member ID : {}", id);
		
		return "home";
	}

	4. @ModelAttribute 어노테이션을 통해서 전송받기
		- 요청 파라미터가 많은 경우 객체타입으로 넘겨받는 방법이다.
		- 스프링 컨테이너가 기본 생성자를 통해서 객체를 생성하고
		  파라미터 name 속성의 값과 동일한 필드명을 가진 필드에 값을 주입해 준다.
		- 단, 기본 생성자와 Setter가 존재해야 한다. 
		- @ModelAttribute 어노테이션을 생략해도 객체로 매핑된다.
	@PostMapping("/login")
//	public String login(@ModelAttribute Member member) {
	public String login(Member member) {
		
		log.info("{}, {}", member.getId(), member.getPassword());
		
		return "home";
	}
	 */
	
	@Autowired // 빈이 존재하면 객체로 가져와 주입!
	private MemberService service;
	
	/*
	 * 로그인 처리
	 * 
	 * 1. HttpSession과 Model 객체
	 * 	1) Model
	 * 	  - 컨트롤러에세 데이터를 뷰로 전달하고자 할 때 사용하는 객체이다. (서블릿에서는 영역객체(pageContext, request...등)
	 *    - 전달하고자 하는 데이터를 맵 형태(key, value)로 담을 수 있다.
	 *    - Model 객체의 scope는 Request이다. (하나의 요청이 처리될때 까지만 유지)
	
	@PostMapping("/login")
	public String login(
			HttpSession session, Model model,
			@RequestParam String id, @RequestParam String password) {
		
		log.info("{}, {}", id, password);
		
		Member member = service.login(id, password);
		
		if (member != null) {
			session.setAttribute("loginMember", member);
	 */
			
			/*
			 *  return "home";
			 *  	- forwarding 방식으로 viewResolver에 의해 /WEB-INF/views/home.jsp로 포워딩 한다.
			 *  
			 *  return "redirect/";
			 *  	- redirect 방식으로 여기서 리턴한 경로로 브라우저에서 다시 요청을 하도록 반환한다. 
			 *  	- URL의 주소가 변경된다.
			
			return "redirect:/";
		} else {
			// 포워딩시 모델 객체도 같이 넘어간다.
			// view에게 데이터를 넘기기위한 객체
			model.addAttribute("msg","아이디나 비밀번호가 일치하지 않습니다.");
			model.addAttribute("location","/");
			
			// WEB-INF/views/common/msg.jsp로 포워딩한다.
			return "common/msg"; 
		}
		
	}
	
	// 로그아웃 처리
	@PostMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	2. @SessionAttributes와 ModelAndView 객체
	  1) @SessionAttributes("키값")
	  	  - Model 객체에 "키값"에 해당하는 Attribute를 Session Scope까지 범위를 확장시킨다.
	      - 클래스에만 어노테이션을 붙일 수 있다.
	      
	  2) ModelAndView
	      - 컨트롤러에서 뷰로 전달할 데이터와 forwading 할 뷰에 정보를 담는 객체이다.
	         (Model 객체는 데이터만 전달)
	      - addAttribute()가 아닌 addObject()메소드를 통해서 데이터를 담을 수 있다.
	
	*/
	
	@RequestMapping(value = "/login", method = {RequestMethod.POST})
	public ModelAndView login(
			ModelAndView model,
			@RequestParam("id") String id, @RequestParam("password") String password) {
		
		log.info("{}, {}", id, password);
		
		Member loginMember = service.login(id, password);
		
		if (loginMember != null) {
			// 모델은 request scope을 가지는데 session 객체에 담아도 로그인이 유지됨
			// why? @SessionAttributes()어노테이션을 사용해서 키값에 해당하는 Attribute를 session 범위까지 확장해서!
			model.addObject("loginMember", loginMember);
			model.setViewName("redirect:/");
		} else {
			// 로그인 실패
			model.addObject("msg", "아이디나 비밀번호가 일치하지 않습니다.");
			
			model.addObject("location", "/");
			model.setViewName("common/msg");
		}
		
		// 포워딩할 view 이름을 적어준다.
		return model;
	}
	
	// 로그아웃 처리(SessionStatus 사용)
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		
		log.info("status.isComplete() : {}", status.isComplete());
			
		// SessionStatus 객체의 setComplete() 메소드로 세션 스코프로 확장된 객체들을 지워준다.
		status.setComplete();
		
		log.info("status.isComplete() : {}", status.isComplete());
		
		return "redirect:/";
		}
	
	@GetMapping("/member/enroll")
	public String enroll() {
		
		log.info("회원 가입 페이지 요청");
		
		return "member/enroll";
	}
	
	@PostMapping("/member/enroll")
	public ModelAndView enroll(ModelAndView model, @ModelAttribute Member member) {
		
		log.info(member.toString());
		
		int result = service.save(member); // 행의 개수를 리턴해주기 때문에 정수값을 리턴!

		if (result > 0) {
			model.addObject("msg", "회원가입이 정상적으로 완료되었습니다.");
			model.addObject("location", "/");
		} else {
			model.addObject("msg", "회원가입을 실패하였습니다.");
			model.addObject("location", "/member/enroll");
		}
		
		model.setViewName("common/msg");
		
		return model;
	}
	/*
	 * @ResponseBody
	 * 	- 일반적으로 컨트롤러 메소드의 반환형이 String일 경우 view의 이름을 반환한다.
	 * 	- @ResponseBody 어노테이션이 붙은 String 반환은 해당 요청을 보낸 클라이언트에 전달할 데이터를 의미한다.
	 *    문자열을 바지에 담아서 클라이언트에게 바로 넘겨준다.
	 *    
	 *  jackson 라이브러리
	 *   - 자바 객체를 JSON형태의 데이터로 변환하기 위한 라이브러리이다. (GSON, jsonSimple)
	 *   - 스프링에서는 jackson 라이브러리를 추가하고  @ResponseBody을 사용하면 리턴되는 객체를 자동으로 JSON으로 변경해서 응답한다.
	 * 
	 *  @RestController
	 *   - 해당 컨트롤러의 메소드에서 데이터를 반환하는 경우 사용한다. 
	 *   - @Controller와 @ResponseBody를 합쳐놓은 역할을 수행한다.
	 */
	@GetMapping("member/jsonTest")
	@ResponseBody // forwarding하지 않고 바로 클라이언트에 넘겨준다.
	public Object jsonTest() {
//		Map<String, String> map = new HashMap<>();
		
//		map.put("hi", "hello");
		
		return new Member("seoh38", "1234", "이서희");
//		return map;
	}
	
	// produces = {MediaType.APPLICATION_JSON_VALUE} : json 데이터를 생산한다.(생략가능)
	@PostMapping("member/idCheck")
	@ResponseBody
	public Object idCheck(@RequestParam("userId") String userId) {
		
		Map<String, Boolean> map = new HashMap<>();
		
		log.info("{}", userId);
		
		map.put("duplicate", service.isDuplicateID(userId));
		
		return map;  
	}
	
	@GetMapping("member/myPage")
	public String myPage() {
	
		return "member/myPage";
	}
	
	@PostMapping("member/update")
	public ModelAndView update(
			ModelAndView model,
			@SessionAttribute(name = "loginMember") Member loginMember,
			@ModelAttribute Member member) {
		int result = 0;
		
		log.info(member.toString());
		
		member.setNo(loginMember.getNo());
		
		result = service.save(member);
		
		if (result > 0) {
			model.addObject("loginMember", service.findMemberById(loginMember.getId()));
			model.addObject("msg", "회원정보 수정을 완료했습니다.");
			model.addObject("location", "/member/myPage");
		} else {
			model.addObject("msg", "회원정보 수정에 실패했습니다.");
			model.addObject("location", "/member/myPage");
		}
		
		model.setViewName("common/msg");
		
		return model;
	}
	
	@GetMapping("member/delete")
	public ModelAndView delete(
			ModelAndView model,
			@SessionAttribute(name = "loginMember") Member loginMember) {
		int result = 0;
		
		result = service.delete(loginMember.getNo());
		
		if (result > 0) {
			model.addObject("msg", "정상적으로 탈퇴되었습니다.");
			model.addObject("location", "/logout");
		} else {
			model.addObject("msg", "회원 탈퇴에 실패하였습니다.");
			model.addObject("location", "/member/myPage");
		}
		
		model.setViewName("common/msg");
		
		return model;
	}
	
	
	
	
	
	
	
}
