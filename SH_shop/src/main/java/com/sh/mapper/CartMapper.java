package com.sh.mapper;

import java.util.List;

import com.sh.model.CartDTO;

public interface CartMapper {

	/* 카트 추가 (sh_cart 테이블의 row추가) */
	public int addCart(CartDTO cart);
	
	/* 카트 삭제 (sh_cart 테이블의 row삭제) */
	public int deleteCart(int cartId);
	
	/* 카트 수량 수정(sh_cart 테이블의 지정한 row 수량 변경) */
	public int modifyCount(CartDTO cart);
	
	/* 카트 목록 */
	public List<CartDTO> getCart(String memberId);	
	
	/* 카트 확인 */
	public CartDTO checkCart(CartDTO cart);
	
}
