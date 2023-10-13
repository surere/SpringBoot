package com.study.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.DAO.ISimpleBbsDao;

import jakarta.servlet.http.HttpServletRequest;

@Controller // 컴포넌트
public class MyController
{
	/*
		DAO의 부모로 선언된 서비스 인터페이스를 통해 dao객체를 생성한다.
		상속구조를 가지고 있으므로 부모를 통해 자식의 메서드를 호출할 수 있다.
	 */
	@Autowired
	ISimpleBbsDao dao;	// 인터페이스 데이타 사용
	
	@RequestMapping("/")
	public String root() throws Exception{
		// JdbcTemplate : SimpleBBs
		return "redirect:list";	// /list로 보냄
	}
	
	// 게시물 목록
	@RequestMapping("/list")	// 프론트 컨트롤러
	public String usetlistPage(Model model) {	
		model.addAttribute("list", dao.listDao());
		return "/list";
	}
	/*
		DAO의 클래스의 select()메서드를 호출하여 회원목록을 List로 반환받은 후 
		Model객체에 저장하고 View로 반환한다. 
	 */
	
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Model model) {	
		String sId = request.getParameter("id");
		model.addAttribute("list", dao.viewDao(sId));
		return "/view";
	}
	
	@RequestMapping("/writeForm")
	public String writeForm() {
		return "/writeForm";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {	
		// 파라미터가 몇개 안 될때 다 쓰는 것이 좋음.
		dao.writeDao(request.getParameter("writer"), 
					request.getParameter("title"),
					request.getParameter("content"));
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {	
		dao.deleteDao(request.getParameter("id"));
		return "redirect:list";
	}
	
	
}
// SQL TABLE
//drop table myuser;
//create table myuser (
//    id varchar2(10),
//    name varchar2(10)
//);
//
//? 샘플 데이터 직접 추가하기
//insert into myuser values ( 'test1', '홍길동1');
//insert into myuser values ( 'test2', '홍길동2');
//insert into myuser values ( 'test3', '홍길동3');
//commit;
//DROP TABLE myuser;
//
//drop table simple_bbs;
//create table simple_bbs (
//    id number(4) primary key,
//    writer varchar2(100),
//    title varchar2(100),
//    content varchar2(100)
//);
//
//drop sequence simple_bbs_seq;
//create sequence simple_bbs_seq;