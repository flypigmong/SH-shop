package com.sh.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.mapper.AttachMapper;
import com.sh.mapper.BookMapper;
import com.sh.mapper.CartMapper;
import com.sh.mapper.MemberMapper;
import com.sh.mapper.OrderMapper;
import com.sh.model.AttachImageVO;
import com.sh.model.BookVO;
import com.sh.model.CartDTO;
import com.sh.model.MemberVO;
import com.sh.model.OrderCancelDTO;
import com.sh.model.OrderDTO;
import com.sh.model.OrderItemDTO;
import com.sh.model.OrderPageItemDTO;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private AttachMapper attachMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private BookMapper bookMapper;
	
	@Override
	public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders) {
		
		List<OrderPageItemDTO> result = new ArrayList<OrderPageItemDTO>();
		
		for(OrderPageItemDTO ord : orders) {
		 
			OrderPageItemDTO goodsInfo = orderMapper.getGoodsInfo(ord.getBookId());
			
			goodsInfo.setBookCount(ord.getBookCount());
			
			goodsInfo.initSaleTotal();			
			
			// 이미지 넣기
			List<AttachImageVO> imageList = attachMapper.getAttachList(goodsInfo.getBookId());
			
			goodsInfo.setImageList(imageList);
			
			result.add(goodsInfo);
		}
		
		return result;
	}

	@Override
	@Transactional
	public void order(OrderDTO OrderDTO) {
		
		// 사용할 데이터 가져오기 
		
		//1.회원 정보 가져오기
		MemberVO member = memberMapper.getMemberInfo(OrderDTO.getMemberId());
	
		//2. 주문 정보 가져오기
		List<OrderItemDTO> ords = new ArrayList<>();
		for(OrderItemDTO OIdto : OrderDTO.getOrders()) {
			OrderItemDTO orderItem = orderMapper.getOrderInfo(OIdto.getBookId());
			// 수량 셋팅
			orderItem.setBookCount(OIdto.getBookCount());
			// 기본정보 셋팅
			orderItem.initSaleTotal();
			//List객체 추가
			ords.add(orderItem);
		}
		// OrderDTO 셋팅 
		OrderDTO.setOrders(ords);
		OrderDTO.getOrderPriceInfo();
		
		
		// 3.DB 주문,주문상품(,배송정보) 넣기
		
		// orderId만들기 및 OrderDTO객체 orderId에 저장 
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
		String orderId = member.getMemberId() + format.format(date);
		OrderDTO.setOrderId(orderId);
		
		// db넣기 
		orderMapper.enrollOrder(OrderDTO);		//vam_order 등록
		for(OrderItemDTO OIdto : OrderDTO.getOrders()) {		//vam_orderItem 등록
			OIdto.setOrderId(orderId);
			orderMapper.enrollOrderItem(OIdto);			
		}

		
		// 4. 비용 포인트 변동 적용 
		// 비용 차감 & 변동 돈(money) Member객체 적용 
		int calMoney = member.getMoney();
		calMoney -= OrderDTO.getOrderFinalSalePrice();
		member.setMoney(calMoney);
		
		// 포인트 차감, 포인트 증가 & 변동 포인트(point) Member객체 적용 
		int calPoint = member.getPoint();
		calPoint = calPoint - OrderDTO.getUsePoint() + OrderDTO.getOrderSavePoint();	// 기존 포인트 - 사용 포인트 + 획득 포인트
		member.setPoint(calPoint);
			
		// 변동 돈, 포인트 DB 적용 
		orderMapper.deductMoney(member);
		
		
		// 5. 재고 변동 적용 
		for(OrderItemDTO oit : OrderDTO.getOrders()) {
			/* 변동 재고 값 구하기 */
			BookVO book = bookMapper.getGoodsInfo(oit.getBookId());
			book.setBookStock(book.getBookStock() - oit.getBookCount());
			/* 변동 값 DB 적용 */
			orderMapper.deductStock(book);
		}			
		
		
		// 6. 장바구니 제거 
		for(OrderItemDTO oit : OrderDTO.getOrders()) {
			CartDTO dto = new CartDTO();
			dto.setMemberId(OrderDTO.getMemberId());
			dto.setBookId(oit.getBookId());
			
			cartMapper.deleteOrderCart(dto);
		}

		
	}

	/* 주문취소 */
	@Override
	@Transactional
	public void orderCancle(OrderCancelDTO dto) {
		
			// 1.주문, 주문상품 객체 가져오기
			// 회원정보
			MemberVO member = memberMapper.getMemberInfo(dto.getMemberId());
			// 주문상품정보
			List<OrderItemDTO> ords = orderMapper.getOrderItemInfo(dto.getOrderId());
			for(OrderItemDTO ord : ords) {
				ord.initSaleTotal();
			}
			// 주문정보
			OrderDTO orw = orderMapper.getOrder(dto.getOrderId());
			orw.setOrders(ords);
			
			orw.getOrderPriceInfo();
			
			
			
			// 2. 주문상품 취소 DB('주문상태' 컬럼 값 -> '주문 취소' 로 변경) */
			orderMapper.orderCancle(dto.getOrderId());
			
			
			// 3. 돈, 포인트 변환 
			// 돈 
			int calMoney = member.getMoney();
			calMoney += orw.getOrderFinalSalePrice();
			member.setMoney(calMoney);
			
			// 포인트 
			int calPoint = member.getPoint();
			calPoint = calPoint + orw.getUsePoint() - orw.getOrderSavePoint();
			member.setPoint(calPoint);
			
			// 주문 돈,포인트 취소 DB (돈,포인트 가산)
			orderMapper.deductMoney(member);
				
			
			// 4. 재고 변환
			for(OrderItemDTO ord : orw.getOrders()) {
				BookVO book = bookMapper.getGoodsInfo(ord.getBookId());
				book.setBookStock(book.getBookStock() + ord.getBookCount());
				orderMapper.deductStock(book);
			}
		
	}
	
	
}
