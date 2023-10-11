package com.study.springboot;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		return "ValidationUtils(2)";
	}
	
	// 글쓰기 폼에 대한 매핑
	@RequestMapping("/insertForm")
	public String insert1() {
		return "createPage";
	}
	
	// Validator 인터페이스를 통한 폼값 유효성 검증
	@RequestMapping("/create")	
	public String insert2(@ModelAttribute("dto") ContentDto contentDto, BindingResult result) {
		
		/*
		 	해당 메서드의 첫번째 매개변수는 폼값을 한꺼번에 받을 수 있는
		 	커멘드객체를 정의한다. 폼값을 받은 후 View로 전달할 때는
		 	@ModelAttribute(어노테이션)을 통해 객체명을 dto로 변경한다.
		 	두번째 매개변수 BindingResult 객체는 폼값검증의 결과를
		 	확인하기 위해 정의한다.
		 */
		
		// 폼값 검증이 완료된 후 포워드할 View의 경로를 설정한다.
		String page = "createDonePage";
		System.out.println(contentDto);
		
		// 폼값 검증을 위해 객체를 생성한다.
		ContentValidator validator = new ContentValidator();
		// 폼값 검증을 위해 메서드를 호출한다.
		validator.validate(contentDto, result);	// 검증
		// 폼값 검증에 실패했는지 확인한다. 
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
}