package com.study.springboot;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.jdbc.IDao;

import jakarta.servlet.http.HttpServletRequest;

@Controller 
public class MyController
{
	@Autowired
	private IDao userDao;
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception{
		// JdbcTemplate : SimpleBBs
		return "MyBatis : 복잡한(join) 쿼리 결과 출력하기";	
	}
	
	@RequestMapping("/employee")
	public String userlistPage(Model model) {	
		model.addAttribute("employees", userDao.getEmployee());
		return "/employeelist";
	}
}