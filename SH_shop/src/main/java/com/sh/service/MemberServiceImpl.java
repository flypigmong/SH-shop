package com.sh.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sh.controller.MemberController;
import com.sh.mapper.MemberMapper;
import com.sh.model.AdminAnswerDTO;
import com.sh.model.Criteria;
import com.sh.model.CustomerCenterDTO;
import com.sh.model.MemberVO;
import com.sh.model.MemberVO1;

@Service
public class MemberServiceImpl implements MemberService{

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberMapper membermapper;
	
	@Autowired(required=false)
	private BCryptPasswordEncoder pwEncoder;
	
	
	/* 회원가입 */
	public void memberJoin(MemberVO member) throws Exception{ 
			String rawPw = ""; // 인코딩 전 비밀번호 
			String encodePw = ""; // 인코딩 후 비밀번호
	
		rawPw = member.getMemberPw();            // 비밀번호 데이터 얻음
		encodePw = pwEncoder.encode(rawPw);        // 비밀번호 인코딩
		member.setMemberPw(encodePw);            // 인코딩된 비밀번호 member객체에 다시 저장
		logger.info("success1:" + rawPw,encodePw);
		/* 회원가입 쿼리 실행 */
		membermapper.memberJoin(member);            // DAO에게 회원가입 쿼리 요청
	
		}
	
	/* 비밀번호 변경 */
	@Override
	public boolean memberPwUpdate(MemberVO member, String currentPw, String newPw) {
		// 일치하는 아이디 있는지 확인
		MemberVO Ivo = membermapper.memberLogin(member); 
		System.out.println("getmemberpwww:  " + Ivo.getMemberPw());
		System.out.println("memberpwwwwwwww:  " +currentPw);
		// 일치하는 아이디 있으면
		if(Ivo != null) {
			if(pwEncoder.matches(currentPw, Ivo.getMemberPw())) { //현재의 비밀번호가 일치하면
				String encodePw = pwEncoder.encode(newPw); // 변경할 비밀번호 인코딩
				member.setMemberPw(encodePw); // 인코딩 된 비밀번호 member객체에 다시 저장
				membermapper.memberPwUpdate(member); // dao 에 비밀번호 변경 요청 전달
	            return true;      //비밀번호 변경 성공을 반환
	        } else {
	            return false;      //비밀번호 변경 실패를 반환
	        }
		}
		return false;  //비밀번호 변경 실패를 반환
	}
	



	
		/* 로그인 */
		public MemberVO memberLogin(MemberVO member) {
			  MemberVO lvo = membermapper.memberLogin(member); // DAO를 호출하여 일치하는 아이디가 있는지 확인
			  logger.info("success1:" + lvo);
			  if(lvo != null) { // 일치하는 아이디가 있으면
			    if(pwEncoder.matches(member.getMemberPw(), lvo.getMemberPw())) { // 비밀번호가 일치하면
			     // lvo.setMemberPw(null); // 인코딩된 비밀번호 정보 지움
			      logger.info("success2:" + lvo);
			      return lvo; // 로그인 성공한 회원 정보 반환
			    } else {
			      return null; // 비밀번호가 일치하지 않으면 null 반환
			    }
			  } else { // 일치하는 아이디가 없으면
			    return null; // null 반환
			  }
			  
			  
			}
		
		
	
	/*
	@Override
	public void memberJoin(MemberVO member) throws Exception {
		
		membermapper.memberJoin(member);
		
		logger.info("join success : "+ member);
	}
*/
	
	/*
	public MemberVO memberLogin(MemberVO member) throws Exception{ 
		String rawPw = ""; // 인코딩 전 비밀번호 
		String encodePw = ""; // 인코딩 후 비밀번호

		rawPw = member.getMemberPw();            // 비밀번호 데이터 얻음
		encodePw = pwEncoder.encode(rawPw);        // 비밀번호 인코딩
		member.setMemberPw(encodePw);            // 인코딩된 비밀번호 member객체에 다시 저장
		logger.info("success2:" + rawPw);
		logger.info("success3:" + encodePw);
		/* 로그인 쿼리 실행 
		return membermapper.memberLogin(member);    // mapper에게 로그인 쿼리 요청하고 결과 리턴
		}
	*/
	
	/*
	@Override
	public MemberVO memberLogin(MemberVO member) throws Exception {
		
		return membermapper.memberLogin(member);
	}
	*/
		
	/* 아이디 중복 검사 */
	@Override
	public int idCheck(String memberId) throws Exception {
		
		return membermapper.idCheck(memberId);
	}


	/* 주문자 정보 */
	@Override
	public MemberVO getMemberInfo(String memberId) {
		
		return membermapper.getMemberInfo(memberId);
	}

	@Override
	public List<MemberVO1> memberGetList(Criteria cri) {
		
		logger.info("(service)memberGetList()........." + cri);
		
		return membermapper.memberGetList(cri);
	}

	@Override
	public int memberGetTotal(Criteria cri) throws Exception {
        logger.info("(service)memberGetTotal()......." + cri);
        return membermapper.memberGetTotal(cri);
	}

	@Override
	public MemberVO memberInfo(String memberId) {
		logger.info("(service)membeGetrInfo()...."+ memberId);
		return membermapper.memberInfo(memberId);
	}

	//고객센터 게시글 목록
	@Override
	public List<CustomerCenterDTO> getList() {
		
		return membermapper.getList();
	}

	//고객센터 게시글 등록
	@Override
	public void enroll(CustomerCenterDTO board) {

			membermapper.enroll(board);
	}

	//고객센터 게시글 조회
	@Override
	public CustomerCenterDTO getPage(int postNo) {
		
		return membermapper.getPage(postNo);
	}

	//고객센터 게시글 수정
	@Override
	public int modify(CustomerCenterDTO board) {
		
		return membermapper.modify(board);
	}

	//관리자 글 등록
	@Override
	public void adenroll(AdminAnswerDTO adboard) {
		
		membermapper.adenroll(adboard);
	}

	//고객센터 글아이디 알아내기
	@Override
	public CustomerCenterDTO getPostIdName(int postNo) {
		
		return membermapper.getPostIdName(postNo);
	}



	
	
	
	
	
}
