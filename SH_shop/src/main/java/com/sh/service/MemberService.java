package com.sh.service;

import java.util.List;

import com.sh.model.AdminAnswerDTO;
import com.sh.model.Criteria;
import com.sh.model.CustomerCenterDTO;
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
	
    // 회원 정보
    public MemberVO memberInfo(String memberId);

    // 비밀번호 변경
	public boolean memberPwUpdate(MemberVO member, String currentPw, String newPw);
    
	// 고객센터 글 목록 
	public List<CustomerCenterDTO> getList();
	
	//고객센터 글 등록
	public void enroll(CustomerCenterDTO board);
	
	//고객센터 글 조회
	public CustomerCenterDTO getPage(int postNo);
	
	//고객센터 글 수정
	public int modify(CustomerCenterDTO board);

	//관리자 글 등록
	public void adenroll(AdminAnswerDTO adboard);

	//고객센터 글 id 이름
	public CustomerCenterDTO getPostIdName(int postNo);


}
