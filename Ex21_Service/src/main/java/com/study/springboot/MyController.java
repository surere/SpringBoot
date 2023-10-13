package com.study.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.dao.ISimpleBbsDao;
import com.study.springboot.service.ISimpleBbsService;

import jakarta.servlet.http.HttpServletRequest;

@Controller // 컴포넌트
public class MyController
{
//	@Autowired
//	ISimpleBbsDao dao;
	
	@Autowired	// 역할!
	ISimpleBbsService bbs;	// 서비스를 사용
	
	@RequestMapping("/")
	public String root() throws Exception{
		// Service vs DAO
		return "redirect:list";	
	}
	
	// 게시물 목록
	@RequestMapping("/list")	
	public String usetlistPage(Model model) {	
//		model.addAttribute("list", dao.listDao());
		model.addAttribute("list", bbs.list());
		
		int nTotalCount = bbs.count();
		System.out.println("Count : " + nTotalCount);
		return "/list";
	}
	
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Model model) {	
		String sId = request.getParameter("id");
		model.addAttribute("dto", bbs.view(sId));
		return "/view";
	}
	
	@RequestMapping("/writeForm")
	public String writeForm() {
		return "/writeForm";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {	
		String sName = request.getParameter("writer");
		String sTitle =	request.getParameter("title");
		String sContent = request.getParameter("content");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("item1", sName);
		map.put("item2", sTitle);
		map.put("item3", sContent);
		
		int nResult = bbs.write(map);
		System.out.println("Write : " + nResult);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {	
		String sId = request.getParameter("id");
		int nResult = bbs.delete(sId);
		System.out.println("Delete : " + nResult);
		return "redirect:list";
	}
}
