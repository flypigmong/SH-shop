package com.sh.mapper;

import java.util.List;

import com.sh.model.BookVO;
import com.sh.model.CateVO;
import com.sh.model.Criteria;

public interface AdminMapper {

	/* 상품 등록 */
	public void bookEnroll(BookVO book);
	
	/* 카테고리 리스트 */
	public List<CateVO> cateList();
	
	/* 상품 리스트 */
	public List<BookVO> goodsGetList(Criteria cri); //각각 상품 목록 페이지에 출력될 페이징화 된 상품 데이터
	
	/* 상품 총 개수 */
	public int goodsGetTotal(Criteria cri);  //페이지 이동 인터페이스 객체를 인스턴스화 하는데 필요로 한 '상품 목록 총 개수' 데이터
	
}
