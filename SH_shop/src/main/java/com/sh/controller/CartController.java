package com.sh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sh.model.CartDTO;
import com.sh.model.MemberVO;
import com.sh.service.CartService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	@PostMapping("/cart/add")
	@ResponseBody
	public String addCartPOST(CartDTO cart, HttpServletRequest request) {
		
		// 로그인 체크
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("member");
		if (mvo == null) {
			return "5";
		}
		
		// 카트 등록
		int result = cartService.addCart(cart);
		
		return result + "";
	}
}
