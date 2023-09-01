package com.sh.service;

import java.util.List;

import com.sh.model.BookVO;
import com.sh.model.CateVO;
import com.sh.model.Criteria;

public interface BookService {
	
	/* 상품 검색 */
	public List<BookVO> getGoodsList(Criteria cri);

	/* 상품 총 갯수 */
	public int goodsGetTotal(Criteria cri);

	/* 국내 카테고리 리스트 */
	public List<CateVO> getCateCode1();
	
	/* 국외 카테고리 리스트 */
	public List<CateVO> getCateCode2();
	
}
