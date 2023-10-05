package com.sh.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sh.model.Criteria;
import com.sh.model.CustomerCenterDTO;
import com.sh.model.MemberVO;
import com.sh.model.MemberVO1;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root/root-context.xml")
@Log4j

public class MemberMapperTests {

		@Autowired
		private MemberMapper membermapper;			//MemberMapper.java 인터페이스 의존성 주입

		/*

		@Test
		public void memberPwUpdate() throws Exception{
			MemberVO member = new MemberVO();
			
			member.setMemberId("test14");			//회원 id
			member.setMemberPw("test16");			//회원 비밀번호
			member.setMemberName("test14");		//회원 이름
			member.setMemberMail("test14");		//회원 메일
			member.setMemberAddr1("test14");		//회원 우편번호
			member.setMemberAddr2("test14");		//회원 주소
			member.setMemberAddr3("test14");		//회원 상세주소
			
			membermapper.memberPwUpdate(member);;			//쿼리 메서드 실행
			
		}
		
		
	
		//회원가입 쿼리 테스트 메서드
		@Test
		public void memberJoin() throws Exception{
			MemberVO member = new MemberVO();
			
			member.setMemberId("test14");			//회원 id
			member.setMemberPw("test14");			//회원 비밀번호
			member.setMemberName("test14");		//회원 이름
			member.setMemberMail("test14");		//회원 메일
			member.setMemberAddr1("test14");		//회원 우편번호
			member.setMemberAddr2("test14");		//회원 주소
			member.setMemberAddr3("test14");		//회원 상세주소
			
			membermapper.memberJoin(member);			//쿼리 메서드 실행
			
		}

		
		
		// 아이디 중복검사 
		 
		@Test
		public void memberIdChk() throws Exception{
			String id = "admin";	// 존재하는 아이디
			String id2 = "test123";	// 존재하지 않는 아이디
			membermapper.idCheck(id);
			membermapper.idCheck(id2);
		}		
		
		
	    // 로그인 쿼리 mapper 메서드 테스트 
	    @Test
	    public void memberLogin() throws Exception{
	        
	        MemberVO member = new MemberVO();    // MemberVO 변수 선언 및 초기화
	        
	        // 올바른 아이디 비번 입력경우 
	        //member.setMemberId("test7");
	        //member.setMemberPw("test7");
	        
	        // 올바르지 않은 아이디 비번 입력경우 
	        member.setMemberId("test1123");
	        member.setMemberPw("test1321321");
	        
	        membermapper.memberLogin(member);
	        System.out.println("결과 값 : " + membermapper.memberLogin(member));		
		
		}
	    
		
		
		//  회원 목록 테스트
		@Test
		public void memberGetListTest() throws Exception{
			
			Criteria cri = new Criteria(1,10); // 1페이지 & 10 개 행 표시
			
			cri.setKeyword("test"); // 검색
			
			List<MemberVO1> list = membermapper.memberGetList(cri);
			
			for (int i = 0; i < list.size(); i++) {
				System.out.println("list" + i + "......." + list.get(i));
			}
		}
		
		
		
		// 회원 총 수 
		@Test
		public void memberGetTotalTest() throws Exception{
			
			Criteria cri = new Criteria();
			cri.setKeyword("test");
			
			int total = membermapper.memberGetTotal(cri);
			
			System.out.println("total................ " + total);
			
		}
		
		
		// 회원 정보
		@Test
		public void memberGetInfo() throws Exception{
			
			//get
			String memberId = "admin" ; //테스트 할 회원 아이디
			
			//when
			MemberVO member = membermapper. getMemberInfo(memberId); // mapper메소드호출
			
			  // then
			  assertNotNull(member); // member 객체가 null이 아닌지 확인
			  assertEquals(memberId, member.getMemberId()); // member 객체의 아이디가 입력한 아이디와 같은지 확인
			  log.info(member); // member 객체의 정보를 로그로 출력
			
		}
		
	
		//고객센터 게시글 목록 테스트
		@Test
		public void testGetList() {
			List list = membermapper.getList();
			
	         for(int i = 0; i < list.size();i++) {
	             log.info("" + list.get(i));
	         }
		}
		
		
	    // 고객센터 게시판 조회 
	     @Test
	    public void testGetPage() {
	        
	        // 실제 존재하는 페이지 
	        int postNo = 1;
	        
	        log.info("" + membermapper.getPage(postNo));
	        
	    }

	     */

	    
		// 고객센터 게시판 글 등록
		@Test
		public void testEnroll() {
			
			CustomerCenterDTO dto = new CustomerCenterDTO();
			
			dto.setPostTitle("mapper test");
			dto.setPostContent("mapper test");
			dto.setMemberId("test11");
			
			membermapper.enroll(dto);
			
		}
	    
}

