package com.sh.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sh.model.MemberVO;
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
	
	
	/*
	 @PostMapping(value="login") 
	 public String loginPOST(MemberVO member, HttpSession session, RedirectAttributes rttr) throws Exception{ 
		 MemberVO login = memberservice.memberLogin(member); // 서비스에게 로그인 요청 전달 
		 if(login != null) {    // 로그인 성공 시
			 session.setAttribute("member", login);  // 세션에 로그인 정보 저장 
			 logger.info("login success");
			 rttr.addFlashAttribute("msg", "로그인 성공!"); // 로그인 성공 메시지 전달
			logger.info("msg");
			 return "redirect:/main"; // 메인페이지로 리다이렉트 

			 
		 	} else {		 // 로그인 실패 시 
		 		logger.info("login fail");
		 		 rttr.addFlashAttribute("msg", "아이디나 비밀번호가 틀렸어요."); // 로그인 실패 메시지 전달
				 return "redirect:/member/login"; // 로그인페이지로 리다이렉트 
		 	} 
	 }

	*/
	
	
	
	/*
	//회원가입
	@PostMapping(value="join")
	public String joinPOST(MemberVO member) throws Exception{
      
		String rawPw = "";            // 인코딩 전 비밀번호
        String encodePw = "";        // 인코딩 후 비밀번호
        
        rawPw = member.getMemberPw();            // 비밀번호 데이터 얻음
        encodePw = pwEncoder.encode(rawPw);        // 비밀번호 인코딩
        member.setMemberPw(encodePw);            // 인코딩된 비밀번호 member객체에 다시 저장
        
        /* 회원가입 쿼리 실행 
        memberservice.memberJoin(member);		
		
        return "redirect:/main";
        
	}	**/
	
	   /* 로그인 
    @PostMapping(value="login")
    public String loginPOST(HttpServletRequest request, MemberVO member, RedirectAttributes rttr) throws Exception{
       
        HttpSession session = request.getSession();
        String rawPw = "";
        String encodePw = "";
    
        MemberVO lvo = memberservice.memberLogin(member);    // 제출한아이디와 일치하는 아이디 있는지 
        
        if(lvo != null) {            // 일치하는 아이디 존재시
            
            rawPw = member.getMemberPw();        // 사용자가 제출한 비밀번호
            encodePw = lvo.getMemberPw();        // 데이터베이스에 저장한 인코딩된 비밀번호
            
            if(true == pwEncoder.matches(rawPw, encodePw)) {        // 비밀번호 일치여부 판단
                
                lvo.setMemberPw("");                    // 인코딩된 비밀번호 정보 지움
                session.setAttribute("member", lvo);     // session에 사용자의 정보 저장
                return "redirect:/main";        // 메인페이지 이동
                
                
            } else {
 
                rttr.addFlashAttribute("result", 0);            
                return "redirect:/member/login";    // 로그인 페이지로 이동
                
            }
            
        } else {                    // 일치하는 아이디가 존재하지 않을 시 (로그인 실패)
            
            rttr.addFlashAttribute("result", 0);            
            return "redirect:/member/login";    // 로그인 페이지로 이동
            
        }
    }
    */
    
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
    
}
	

