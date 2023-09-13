package com.sh.mapper;

import java.util.List;

import com.sh.model.BookVO;
import com.sh.model.CateFilterDTO;
import com.sh.model.CateVO;
import com.sh.model.Criteria;

public interface BookMapper {
	
	/* 상품 검색 (검색 결과에 대한 데이터) */
	public List<BookVO> getGoodsList(Criteria cri);
	
	/* 상품 총 갯수 (페이징 데이터) */
	public int goodsGetTotal(Criteria cri);
	
	/* 작가 id 리스트 요청 */
	public String[] getAuthorIdList(String keyword);
	
	/* 국내 카테고리 리스트 */
	public List<CateVO> getCateCode1();
	
	/* 국외 카테고리 리스트 */
	public List<CateVO> getCateCode2();
	
	/* 검색 대상 카테고리 리스트 */
	public String[] getCateList(Criteria cri);
	
	/* 카테고리 정보(+검색대상 갯수) */
	public CateFilterDTO getCateInfo(Criteria cri);
	
	/* 상품 정보 */
	public BookVO getGoodsInfo(int bookId);
	
	/* 상품 id 이름 */
	public BookVO getBookIdName(int bookId);
	
}
