package com.sh.service;

import com.sh.model.MemberVO;

public interface MemberService {

	//회원가입
	public void memberJoin(MemberVO member) throws Exception;

	//회원가입
	public int idCheck(String memberId);
}
