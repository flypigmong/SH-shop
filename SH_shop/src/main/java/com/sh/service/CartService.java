package com.sh.service;

import com.sh.model.CartDTO;

public interface CartService {

	/* 장바구니 추가 */
	public int addCart(CartDTO cart);
	
}
