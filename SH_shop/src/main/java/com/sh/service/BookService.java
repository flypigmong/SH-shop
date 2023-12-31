package com.sh.service;

import java.util.List;

import com.sh.model.BookVO;
import com.sh.model.CateFilterDTO;
import com.sh.model.CateVO;
import com.sh.model.Criteria;
import com.sh.model.SelectDTO;

public interface BookService {
	
	/* 상품 검색 */
	public List<BookVO> getGoodsList(Criteria cri);

	/* 상품 총 갯수 */
	public int goodsGetTotal(Criteria cri);

	/* 국내 카테고리 리스트 */
	public List<CateVO> getCateCode1();
	
	/* 국외 카테고리 리스트 */
	public List<CateVO> getCateCode2();
	
	/* 검색결과 카테고리 필터 정보 */
	public List<CateFilterDTO> getCateInfoList(Criteria cri);
	
	/* 상품 정보 */
	public BookVO getGoodsInfo(int bookId);
	
	/* 상품 id 이름 */
	public BookVO getBookIdName(int bookId);
	
	/* 메인페이지 평점순 상품 정보 */
	public List<SelectDTO> likeSelect();
	
}
