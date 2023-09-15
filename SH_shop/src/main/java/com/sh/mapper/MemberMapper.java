package com.sh.mapper;

import java.util.List;

import com.sh.model.Criteria;
import com.sh.model.MemberVO;
import com.sh.model.MemberVO1;


public interface MemberMapper {
	
	//회원가입
	public void memberJoin(MemberVO member);

	// 아이디 중복 검사
	public int idCheck(String memberId);	
	
    /* 로그인 */
    public MemberVO memberLogin(MemberVO member);	
    
    /* 주문자 주소 정보 */
    public MemberVO getMemberInfo(String memberId);
    
    /* 회원 목록 */
    public List<MemberVO1> memberGetList(Criteria cri);
    
    /* 회원 총 수 */
    public int memberGetTotal(Criteria cri);
    
}
