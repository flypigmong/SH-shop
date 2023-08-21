package com.sh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.AdminMapper;
import com.sh.model.BookVO;

import lombok.extern.log4j.Log4j;


@Service
@Log4j
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public void bookEnroll(BookVO book) {
		
		log.info("(service)bookEnroll...........");
		
		adminMapper.bookEnroll(book);
		
	}

}