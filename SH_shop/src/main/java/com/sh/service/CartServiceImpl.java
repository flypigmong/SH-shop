package com.sh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.CartMapper;
import com.sh.model.CartDTO;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;

	@Override
	public int addCart(CartDTO cart) {
		// 장바구니 데이터 체크
		CartDTO checkCart = cartMapper.checkCart(cart);
		
		if(checkCart != null) {
			return 2;
		}
		
		// 장바구니 등록 & 에러 시 0 반환
		try {
			return cartMapper.addCart(checkCart);
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public List<CartDTO> getCartList(String memberId) {
		List<CartDTO> cart = cartMapper.getCart(memberId);
		
		for(CartDTO dto : cart) {  // CartDTO의 'salePrice', 'totalPrice', 'point', 'totalPoint' 변수 값 초기화
			dto.initSaleTotal();
		}
		
		return cart;
	}
}
