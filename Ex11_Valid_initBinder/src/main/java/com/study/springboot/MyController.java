package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MyController
{
	@RequestMapping("/")
	public @ResponseBody String root() throws Exception{
		return "Validator(3)";
	}
	
	@RequestMapping("/insertForm")
	public String insert1() {
		return "createPage";
	}
	/*
		폼값이 전송되면 contentDto객체를 통해 한꺼번에 받는다.
		해당 객체에는 폼값 검증을 위한 어노테이션이 추가되어 있으므로
		해당 객체를 통해 검증을 하겠다는 의미로 @Validated어노테이션을
		추가해야 한다.
	 */
	
	@RequestMapping("/create")	
	public String insert2(@ModelAttribute("dto") @Validated ContentDto contentDto, BindingResult result) {
		
		String page = "createDonePage";
		System.out.println(contentDto);
		
		// 검증을 위한 클래스를 별도로 정의할 필요가 없다.
//		ContentValidator validator = new ContentValidator();
//		validator.validate(contentDto, result);	// 검증
		if(result.hasErrors()) {
			// 제목 검증에 실패한 경우 디폴트 에러코드를 출력한다.
			System.out.println("getAllErrors : " +result.getAllErrors());
			if(result.getFieldError("writer") != null) {
				System.out.println("1:"+result.getFieldError("writer").getCode());
			}
			
			if(result.getFieldError("content") != null) {
				System.out.println("2:"+result.getFieldError("content").getCode());
			}
			
			page = "createPage";	// 에러가 있으면 이전페이지로..
		}
		
		return page;			
	}
	
	// 검증을 위한 클래스를 대체한다.(위 43,44 줄을 대체함)
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new ContentValidator());
	}
}