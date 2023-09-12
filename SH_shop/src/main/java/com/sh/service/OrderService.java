package com.sh.service;

import java.util.List;

import com.sh.model.OrderDTO;
import com.sh.model.OrderPageItemDTO;

public interface OrderService {

	/* 주문 정보 (여러개 상품정보 반환해야하므로 반환타입은 List 타입)  */
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders);
	
	/* 주문 */
	public void order(OrderDTO orw);
	
}
