package com.study.springboot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.springboot.jdbc.MyUserDAO;

@Controller // 컴포넌트
public class MyController
{
	@Autowired
	// 오라클 접속 정보로 new 된것이 들어옴.
	private MyUserDAO userDao;
	
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception{
		return "JdbcTemplate 사용하기";
	}
//	@GetMapping("/user")	// 잘 안씀
	// 필드명으로 사용하여 순서가 바뀌어도 상관 없음(최신유행)
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String usetlistPage(Model model) {	// heap에 있는 빈 model
		// DAO클래스의 list()메서드를 호출하여 회원목록을 userlist로 반환한 후
		// Model객체에 저장하고 View를 반환한다.
		model.addAttribute("users", userDao.listForBeanPropertyRowMapper());
		return "userlist";
	}
	
	
}