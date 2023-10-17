package com.sh.model;

import lombok.Data;

@Data
public class OrderCancelDTO {

	private String memberId;
	
	private String orderId;
	
	private String keyword;
	
	// 멤버 업데이트 값 가져오기
	
	//회원 돈
	private int money;
	
	//회원 포인트
	private int point;
	
	private int amount;
	
	private int pageNum;

}

