package com.study.springboot.DAO;

import java.util.List;

import com.study.springboot.DTO.SimpleBbsDto;

public interface ISimpleBbsDao
{
	public List<SimpleBbsDto> listDao();
	public SimpleBbsDto viewDao(String id);
	public int writeDao(String writer, String title, String content);
	public int deleteDao(String id);
}
