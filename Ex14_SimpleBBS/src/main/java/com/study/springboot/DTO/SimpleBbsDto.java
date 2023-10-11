package com.study.springboot.DTO;

import lombok.Data;

@Data
public class SimpleBbsDto
{
	private int id;
	private String writer;
	private String title;
	private String content;
}
