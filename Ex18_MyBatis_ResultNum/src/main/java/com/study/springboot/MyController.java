package com.study.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.dao.ISimpleBbsDao;

import jakarta.servlet.http.HttpServletRequest;

// 껍데기만 있어서 알맹이만 넣어주는 작업만 하면 됨. 쉬워짐
@Controller 
public class MyController
{
	// Mybatis 사용을 위해 자동 주입
	@Autowired
	ISimpleBbsDao dao;	// 약한 결합으로 주입 받는다.
	
	@RequestMapping("/")
	public String root() throws Exception{
		// JdbcTemplate : SimpleBBs
		return "redirect:list";	
	}
	
	// 게시물 목록
	@RequestMapping("/list")
	public String usetlistPage(Model model) {	
		// dao(mapper)의 listDao() 메서드를 호출해서 게시물 목록을 인출
		model.addAttribute("list", dao.listDao());
		
		int nTotalCount = dao.articleCount();
		System.out.println("Count : " + nTotalCount);
		return "/list";
	}
	
	// 게시물 내용 보기
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
	
	// 게시물 등록
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {
		
		String sName = request.getParameter("writer");
		String sTitle = request.getParameter("title");
		String sContent = request.getParameter("content");
		// HashMap은 이름이 여러개인 값을 넣을 때, Map에 파라미터를 저장한다.
		Map<String, String> map = new HashMap<String, String>();
		map.put("item1", sName);
		map.put("item2", sTitle);
		map.put("item3", sContent);
		
		int nResult = dao.writeDao(map);
		System.out.println("Write : " + nResult);
		
		return "redirect:list";
	}
	
	// 게시물 삭제
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {	
		String sId = request.getParameter("id");
		int nResult = dao.deleteDao(sId);
		System.out.println("Delete: " + nResult);
		
		return "redirect:list";
	}
}