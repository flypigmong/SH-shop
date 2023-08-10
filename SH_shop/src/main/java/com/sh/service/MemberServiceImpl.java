package com.sh.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.controller.MemberController;
import com.sh.mapper.MemberMapper;
import com.sh.model.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	MemberMapper membermapper;

	@Override
	public void memberJoin(MemberVO member) throws Exception {
		
		membermapper.memberJoin(member);
		
		logger.info("join success : "+ member);
	}
	
	
	
}
