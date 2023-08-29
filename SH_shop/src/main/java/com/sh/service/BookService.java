package com.sh.service;

import java.util.List;

import com.sh.model.BookVO;
import com.sh.model.Criteria;

public interface BookService {
	
	/* 상품 검색 */
	public List<BookVO> getGoodsList(Criteria cri);

	/* 상품 총 갯수 */
	public int goodsGetTotal(Criteria cri);

}
