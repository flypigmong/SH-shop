package com.sh.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sh.model.BookVO;
import com.sh.model.MemberVO;
import com.sh.model.OrderDTO;
import com.sh.model.OrderItemDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class OrderMapperTests {

	@Autowired
	private OrderMapper mapper;
	
	// 상품 정보(주문 처리) 
	@Test
	public void getOrderInfoTest() {
		
		 OrderItemDTO orderInfo = mapper.getOrderInfo(11);
		 
		 System.out.println("result : " + orderInfo);
	}
	
	// sh_order 테이블 등록 
	@Test
	public void enrollOrderTest() {
		
		OrderDTO ord = new OrderDTO();
		List<OrderItemDTO> orders = new ArrayList();
		
		OrderItemDTO order1 = new OrderItemDTO();
		
		order1.setBookId(11);
		order1.setBookCount(5);
		order1.setBookPrice(70000);
		order1.setBookDiscount(0.1);
		order1.initSaleTotal();
		
		
		
		ord.setOrders(orders);
		
		ord.setOrderId("2021_test1");
		ord.setAddress("test");
		ord.setMemberId("admin");
		ord.setMemberAddr1("test");
		ord.setMemberAddr2("test");
		ord.setMemberAddr3("test");
		ord.setOrderState("배송중비");
		ord.getOrderPriceInfo();
		ord.setUsePoint(1000);
		
		mapper.enrollOrder(ord);		
		
	}
	
	
	// sh_itemorder 테이블 등록 
	@Test
	public void enrollOrderItemTest() {
		
		OrderItemDTO oid = new OrderItemDTO();
		
		oid.setOrderId("2021_test1");
		oid.setBookId(11);
		oid.setBookCount(1);
		oid.setBookPrice(70000);
		oid.setBookDiscount(0.1);
				
		oid.initSaleTotal();
		
		mapper.enrollOrderItem(oid);
		
	}	
	
	
	// 회원 돈, 포인트 정보 변경 
	@Test
	public void deductMoneyTest() {
		
		MemberVO member = new MemberVO();
		
		member.setMemberId("admin");
		member.setMoney(500000);
		member.setPoint(10000);
		
		mapper.deductMoney(member);
	}
	
	
	// 상품 재고 변경 
	@Test
	public void deductStockTest() {
		BookVO book = new BookVO();
		
		book.setBookId(11);
		book.setBookStock(77);
		
		mapper.deductStock(book);
	}
	
}
