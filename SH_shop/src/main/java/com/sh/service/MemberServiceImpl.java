package com.sh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.MemberMapper;
import com.sh.model.MemberVO;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	MemberMapper membermapper;

	@Override
	public void memberJoin(MemberVO member) throws Exception {
		
		membermapper.memberJoin(member);
		
	}
	
	
	
}
