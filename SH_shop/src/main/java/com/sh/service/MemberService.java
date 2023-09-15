package com.sh.service;

import java.util.List;

import com.sh.model.Criteria;
import com.sh.model.MemberVO;
import com.sh.model.MemberVO1;

public interface MemberService {

	//회원가입
	public void memberJoin(MemberVO member) throws Exception;

	//아이디 중복 검사
	public int idCheck(String memberId) throws Exception;
	
	//로그인
	public MemberVO memberLogin(MemberVO member) throws Exception;

	// 주문자 정보
	public MemberVO getMemberInfo(String memberId);

	// 회원 목록
	public List<MemberVO1> memberGetList(Criteria cri);
	
	// 회원 총 수
	public int memberGetTotal(Criteria cri) throws Exception;
	
}
