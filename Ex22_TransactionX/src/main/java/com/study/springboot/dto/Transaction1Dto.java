package com.study.springboot.dto;

import lombok.Data;

// 현장 매표소(온라인 서버와 연결)
@Data
public class Transaction1Dto
{
	private String consumerId;
	private int amount;
}
