package com.sh.controller;


import java.util.Enumeration;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sh.model.AdminAnswerDTO;
import com.sh.model.Criteria;
import com.sh.model.CustomerCenterDTO;
import com.sh.model.MemberVO;
import com.sh.model.PageDTO;
import com.sh.service.MemberService;

@Controller
@RequestMapping(value = "/member") 
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberservice;
	
	@Autowired
	private JavaMailSender mailSender;
	
	//회원가입 페이지 이동
	@GetMapping(value = "join")
	public void joinGET() {
		
		logger.info("회원가입 페이지 진입");
	}		

	//로그인 페이지 이동
	@GetMapping(value = "login")
	public void loginGET() {
		
		logger.info("로그인 페이지 진입");	
	}		

	
	@PostMapping(value="join") 
	public String joinPOST(MemberVO member) throws Exception{ 
		memberservice.memberJoin(member); // 서비스에게 회원가입 요청 전달 return “redirect:/main”; // 메인페이지로 리다이렉트 }
		return "redirect:/main";
	}

	// 비밀번호 변경 페이지
	@GetMapping("/pwUpdateForm")
	public void meberPwUpdateForm(String memberId, Model model, HttpSession session) throws Exception{
		MemberVO member = (MemberVO) session.getAttribute("member");
		System.out.println("Member " + member);
		
	}
	
	//비밀번호 변경
	@PostMapping(value="/pwUpdate")
	public String memberPwUpdate(@RequestParam("currentPw") String currentPw,
			@RequestParam("newPw") String newPw, HttpSession session, RedirectAttributes rttr, Model model) {
				
		// 세션에서 로그인한 회원의 정보 가져오기
		MemberVO member = (MemberVO) session.getAttribute("member");
		System.out.println("Member : " + member);
		System.out.println("currentPw : " + currentPw);
		System.out.println("newPw: " + newPw);
		
		// 서비스에 비밀번호 변경 요청 전달
		boolean result = memberservice.memberPwUpdate(member, currentPw, newPw);
		
		//결과에 따라 다른 페이지로 리다이렉트
		if(result) {
			session.setAttribute("msg1", "비밀번호 변경성공!");
			 //session.removeAttribute("msg1"); // 세션에서 로그인 실패 메시지 삭제
			//rttr.addFlashAttribute("msg1", "비밀번호 변경성공!"); // 성공 메시지 전달
			// session.removeAttribute("msg1");
			//model.addAttribute("msg1", "비밀번호 변경성공!"); // Model 객체에 메시지 저장

			return "redirect:/member/pwUpdateForm";
		}else {
			 session.setAttribute("msg1", "기존 비밀번호가 틀립니다.");
			//rttr.addFlashAttribute("msg1", "기존 비밀번호가 틀립니다."); // 실패 메시지 전달
			 //session.removeAttribute("msg1"); // 세션에서 로그인 실패 메시지 삭제
			// session.removeAttribute("msg1"); // 세션에서 로그인 실패 메시지 삭제
			// model.addAttribute("msg1", "기존 비밀번호가 틀립니다."); // Model 객체에 메시지 저장
			return "redirect:/member/pwUpdateForm";
		}
		
	}
	
	@PostMapping(value="login")
	public String loginPOST(HttpServletRequest request, MemberVO member, RedirectAttributes rttr) throws Exception{
	  HttpSession session = request.getSession();
	  MemberVO lvo = memberservice.memberLogin(member); // Service를 호출하여 로그인 로직 수행
	  if(lvo != null) { // 로그인 성공 시
	    session.setAttribute("member", lvo); // session에 사용자의 정보 저장
	    logger.info("login success");
	    return "redirect:/main"; // 메인페이지로 리다이렉트
	  } else { // 로그인 실패 시
	    rttr.addFlashAttribute("msg", "아이디나 비밀번호를 다시 확인하세요!"); // 로그인 실패 메시지 전달
	    session.removeAttribute("msg"); // 세션에서 로그인 실패 메시지 삭제
	    return "redirect:/member/login"; // 로그인페이지로 리다이렉트
	  }
	}	
	
    
	// 아이디 중복 검사
	@PostMapping(value = "/memberIdChk")
	@ResponseBody
	public String memberIdChkPOST(String memberId) throws Exception{
		
		/* logger.info("memberIdChk() 진입"); */
		
		logger.info("memberIdChk() 진입");
		
		int result = memberservice.idCheck(memberId);
		
		logger.info("결과값 = " + result);
		
		if(result != 0) {
			
			return "fail";	// 중복 아이디가 존재
			
		} else {
			
			return "success";	// 중복 아이디 x
			
		}			
	} // memberIdChkPOST() 종료

	 
    /* 이메일 인증 */
    @GetMapping(value="/mailCheck")
    @ResponseBody
    public String mailCheckGET(String email) throws Exception{
        
        /* 뷰(View)로부터 넘어온 데이터 확인 */
        logger.info("이메일 데이터 전송 확인");
        logger.info("인증번호 : " + email);
               
        /* 인증번호(난수) 생성 */
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;
        logger.info("인증번호 " + checkNum);
        
        /* 이메일 보내기 */
        String setFrom = "jshun054@naver.com";
        String toMail = email;
        String title = "회원가입 인증 이메일 입니다.";
        String content = 
        		"홈페이지를 방문해주셔서 감사합니다." +
        			"<br><br>" +
        			"인증 번호는 " + checkNum + "입니다." +
        			"<br>" +
        			"해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
        
        try {
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
            
        }catch(Exception e) {
            e.printStackTrace();
        }
       
        
        String num = Integer.toString(checkNum);
        
		return num;        
    }

    
    /* 메인페이지 로그아웃 */
    @GetMapping(value="logout.do")
    public String logoutMainGET(HttpServletRequest request) throws Exception{
      
    	logger.info("logoutMainGET메서드 진입");
       
        HttpSession session = request.getSession();
        
        session.invalidate();
        
		return "redirect:/main";
    }

    
    /* 비동기방식 로그아웃 메서드 */
    @RequestMapping(value="logout.do", method=RequestMethod.POST)
    @ResponseBody
    public void logoutPOST(HttpServletRequest request) throws Exception{
        
        logger.info("비동기 로그아웃 메서드 진입");
        
        HttpSession session = request.getSession();
        
        session.invalidate();
        
    }   
    
    
    /* 마이페이지 */
	@GetMapping("/myPage/{memberId}")
	public String myPageGet(@PathVariable("memberId") String memberId, Model model) {
		
		model.addAttribute("memberInfo", memberservice.memberInfo(memberId));
		logger.info("model " + model);
		
		return "member/myPage";
	}
    
	
	/* 고객센터 페이지(페이징 적용) */
	@GetMapping("/customer/list")
	public void boardListGet(Model model, Criteria cri, HttpServletRequest request) {
		
		logger.info("고객센터 목록 페이지 진입");
		
		
		
		HttpSession session = request.getSession(); 
		
		MemberVO member = (MemberVO) session.getAttribute("member"); //세션에 저장한 member 가져오기
		System.out.println("세션객체 : " + member); // member 객체의 내용을 출력
		
		String memberId = member.getMemberId(); // 세션에 저장된 사용자의 id가져오기
		System.out.println("로그인아이디 : " + memberId); //memberId 가져오기
		
		model.addAttribute("memberId", memberId); // 모델에 memberId 속성 추가 
		
		
		
		
		model.addAttribute("list", memberservice.getList(cri));
		
		int total = memberservice.getTotal();
		PageDTO pageMake = new PageDTO(cri, total);
		model.addAttribute("pageMaker", pageMake);
		logger.info("pageMakeeeeeeeeeeeeeeeee"+pageMake);
		
	}
	
	// 고객센터 게시글 조회
	@GetMapping("/customer/get")
	public void boardGetPageGET(int postNo, Model model, HttpServletRequest request, Criteria cri) {
				
		HttpSession session = request.getSession(); 
		
		MemberVO member = (MemberVO) session.getAttribute("member"); //세션에 저장한 member 가져오기
		System.out.println("세션객체 : " + member); // member 객체의 내용을 출력
		
		String memberId = member.getMemberId(); // 세션에 저장된 사용자의 id가져오기
		System.out.println("로그인아이디 : " + memberId); //memberId 가져오기
		
		
		CustomerCenterDTO board = memberservice.getPage(postNo); // 글 정보를 가져옴 
		String writeId = board.getMemberId(); // 글 작성자의 아이디
		System.out.println("글작성자 : " + writeId);
		
		model.addAttribute("memberId", memberId); // 모델에 memberId 속성 추가 
		model.addAttribute("writeId", writeId); // 모델에 writeId 속성 추가
		
		model.addAttribute("goodsInfo", memberservice.getPage(postNo));
		
		model.addAttribute("cri", cri);
		logger.info("criiiiiiiiiiiiiiiiiiiiiiiiiiiii" + cri);
		
		logger.info("model@@@@@@@@@@@" + model);
		
	}
	
	
	@GetMapping("/customer/postEnroll")
	public void boardEnrollGet() {
		logger.info("고객센터 글 등록 페이지 진입");
	}
	
	@PostMapping("/customer/postEnroll")
	public String boardEnrollPOST(CustomerCenterDTO board, RedirectAttributes rttr) {
		
		logger.info("게시판 글 등록111 : " + board);
		
		memberservice.enroll(board);
		
		logger.info("게시판 글 등록222 : " + board);
		
		rttr.addFlashAttribute("result", "enroll success");
		
		return "redirect:/member/customer/list";
	}
	
	
	
	//고객센터 게시글 수정페이지 이동
	@GetMapping("/customer/modify")
	public void boardModifyGET(int postNo, Model model, HttpServletRequest request, Criteria cri) {
		
		model.addAttribute("goodsInfo", memberservice.getPage(postNo));
		
		model.addAttribute("cri", cri);
		
	}
	
	
	//고객센터 게시글 수정
	@PostMapping("/customer/modify")
	public String boardModifyPOST(MemberVO vo,CustomerCenterDTO dto, RedirectAttributes rttr, HttpServletRequest request, int postNo) {

		HttpSession session = request.getSession(); 
		
		MemberVO member = (MemberVO) session.getAttribute("member"); //세션에 저장한 member 가져오기
		System.out.println("세션객체 : " + member); // member 객체의 내용을 출력
		
		String memberId = (String) session.getAttribute("memberId"); // 세션에 저장된 사용자의 id가져오기
		System.out.println("로그인아이디 : " + memberId); //memberId 가져오기
		
		
		CustomerCenterDTO board = memberservice.getPage(postNo); // 글 정보를 가져옴 
		String writeId = board.getMemberId(); // 글 작성자의 아이디
		System.out.println("글작성자 : " + writeId);
		
		memberservice.modify(dto);
		
		rttr.addFlashAttribute("result", "modify success");
		
		return "redirect:/member/customer/list";
		
	}
	
	
	/*
	 * //고객게시글 답변페이지로 이동
	 * 
	 * @GetMapping("/customer/postEnroll") public void AdminBoardEnrollGet() {
	 * logger.info("고객센터 글 등록 페이지 진입"); }
	 * 
	 * //고객게시글 답변 글 등록
	 * 
	 * @PostMapping("/customer/postEnroll") public String
	 * AdminBoardEnrollPOST(AdminAnswerDTO adboard, RedirectAttributes rttr) {
	 * 
	 * logger.info("게시판 글 등록111 : " + adboard);
	 * 
	 * memberservice.adenroll(adboard);
	 * 
	 * logger.info("게시판 글 등록222 : " + adboard);
	 * 
	 * rttr.addFlashAttribute("result", "enroll success");
	 * 
	 * return "redirect:/member/customer/list"; }
	 */
	
	/* 리뷰 쓰기 */
	@GetMapping("/customer/cusReplyEnroll/{memberId}")
	public String replyEnrollWindowGET(@PathVariable("memberId")String memberId, int postNo, Model model) {
		CustomerCenterDTO post = memberservice.getPostIdName(postNo);
		
		//글작성자아이디가져오기
		CustomerCenterDTO board = memberservice.getPage(postNo); // 글 정보를 가져옴 
		String writeId = board.getMemberId(); // 글 작성자의 아이디
		System.out.println("글작성자 : " + writeId);
		
		
		model.addAttribute("postInfo", post);
		model.addAttribute("memberId", memberId);
		model.addAttribute("writeId", writeId);
		logger.info("model@@@@@@@@l" + model);
		return "/member/customer/cusReplyEnroll";
	}
	
	
	
}
	

