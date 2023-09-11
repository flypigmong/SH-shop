package com.sh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sh.model.CartDTO;
import com.sh.model.MemberVO;
import com.sh.service.CartService;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	/* 장바구니 추가 */
	@PostMapping("/cart/add")
	@ResponseBody
	public String addCartPOST(CartDTO cart, HttpServletRequest request) {
		
		// 로그인 체크
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO) session.getAttribute("member");
		System.out.println("mvo: " + mvo);
		if (mvo == null) {
			return "5";
		}
		
		// 카트 등록
		int result = cartService.addCart(cart);
		System.out.println("result : " + result);
		return result + "";
	}
	
	@GetMapping("/cart/{memberId}")
	public String cartPageGET(@PathVariable("memberId") String memberId, Model model) {
		
		model.addAttribute("cartInfo", cartService.getCartList(memberId)); // 장바구니 데이터 전송
	
		return "/cart";
	}
	
	/* 장바구니 수량 삭제 */
	@PostMapping("/cart/delete")
	public String updateCartPOST(CartDTO cart) {
		
		cartService.modifyCount(cart);
		
		return "redirect:/cart/" + cart.getMemberId();
	}
}
