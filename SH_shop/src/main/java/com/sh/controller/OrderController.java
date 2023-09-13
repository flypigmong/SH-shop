package com.sh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.sh.mapper.MemberMapper;
import com.sh.model.MemberVO;
import com.sh.model.OrderDTO;
import com.sh.model.OrderPageDTO;
import com.sh.service.MemberService;
import com.sh.service.OrderService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	MemberMapper membermapper;
	
	@GetMapping("/order/{memberId}")
	public String orderPgaeGET(@PathVariable("memberId") String memberId, OrderPageDTO opd, Model model) {
		
		System.out.println("memberId : " + memberId);
		System.out.println("orders : " + opd.getOrders());
		
		// 상품정보, 회원정보 만드는 서비스 메서드 호출
		model.addAttribute("orderList", orderService.getGoodsInfo(opd.getOrders()));
		model.addAttribute("memberInfo", memberService.getMemberInfo(memberId));
		
		return "/order";
	}
	
	
	@PostMapping("/order")
	public String orderPagePost(OrderDTO od, HttpServletRequest request) {
		System.out.println(od);
		
		orderService.order(od);
		
		MemberVO member = new MemberVO();
		member.setMemberId(od.getMemberId());
			
		member.setMemberId(od.getMemberId());
		System.out.println("memberId를 넣은 후 : " + member);
		
		HttpSession session = request.getSession();
		System.out.println("session1111: " + session);
	
		try {
			MemberVO memberLogin = membermapper.memberLogin(member);
			System.out.println("memberLogin1111 : " + memberLogin);
			
			memberLogin.setMemberPw("");
			System.out.println("setMemberPw이후 :" + member );
			
			session.setAttribute("member", memberLogin);
			System.out.println("member2222 : " + memberLogin);
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		return "redirect:/main";
	}
	
}
