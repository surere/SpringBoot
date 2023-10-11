package com.study.springboot.bean;

public class Member 
{
	// 멤버변수(문자열, 정수, 사용자정의 객체 타입)
	private String name;	// private 약한 결합
	private String nickname;
	private Printer printer;
	
	// 기본(디폴트)생성자, 인수생성자, getter, setter, toString()
	// 메서드를 추가한다. (자동생성 메뉴 사용) - 기본생성자 게터 세터
	public Member() {}	// public으로 하면 강한결합
	
	/*
	 	클래스를 통해 객체를 생성할 때는 항상 생성자 메서드 호출을 통해 기술한다.
	 	만약 개발자가 생성자를 정의하지 않으면 컴파일러에 의해 디폴트 생성자(인수,
	 	실행부가 없는 형태)가 만들어진다. 그런데 개발자가 생성자를 정의하면
	 	디폴트 생성자는 만들어지지 않으므로 필요에 따라 추가해야 한다.
	 */
	public Member(String name, String nickname, Printer printer) {
		this.name = name;
		this.nickname = nickname;
		this.printer = printer;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setPrinter(Printer printer) {
		this.printer = printer;
	}
	public void print() {
		printer.print("Hello" + name + " : "+nickname);
	}
}
