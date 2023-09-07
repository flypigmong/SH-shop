package com.sh.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sh.model.BookVO;
import com.sh.model.Criteria;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BookMapperTests {
	
	@Autowired
	private BookMapper mapper;
	
	/*
	@Test
	public void getGoodsListTest() {
		
		//테스트 키워드
		Criteria cri = new Criteria();
		cri.setKeyword("어린 왕자");
		System.out.println("cri : " + cri );
		
		List<BookVO> list = mapper.getGoodsList(cri);
		System.out.println("list : " + list);
		
		System.out.println("==========");
		int goodsTotal = mapper.goodsGetTotal(cri);
		System.out.println("total : " + goodsTotal);
	
	}
	*/
	
	/*
	@Test
	public void getAuthorId() {
		
		String keyword = "베르";
		
		String[] list = mapper.getAuthorIdList(keyword);
		
		System.out.println("결과 :" + list.toString());
		
		for(String id : list) {
			System.out.println(" 개별 결과 :" + id);
		}
	}
	*/
	
	/*
	// 검색 (동적 쿼리 적용) - 작가(A)
	@Test 
	public void getGoodsListTest1() {
		
		Criteria cri = new Criteria();
		String type = "A";
		String keyword = "베르나르";		// DB에 등록된 작가 데이터
       // String keyword = "없음";		// DB에 등록되지 않은 작가 데이터
		String catecode = "";
		
		cri.setType(type);
		cri.setKeyword(keyword);
		cri.setAuthorArr(mapper.getAuthorIdList(keyword));
		cri.setCateCode(catecode);
		
		List<BookVO> list = mapper.getGoodsList(cri);
		
		System.out.println("cri : " + cri);
		System.out.println("list : " + list);
		
	}
	
	
	
	
	// 검색 (동적 쿼리 적용) - 책제목
	@Test 
	public void getGoodsListTest2() {
		Criteria cri = new Criteria();
		String type = "T";
		String keyword = "어린";			// 테이블에 등록된 책 제목 데이터
		//String keyword = "없음";				// 테이블에 등록되지 않은 데이터
		String catecode = "";	
		
		cri.setType(type);
		cri.setKeyword(keyword);
		cri.setAuthorArr(mapper.getAuthorIdList(keyword));
		cri.setCateCode(catecode);
		
		List<BookVO> list = mapper.getGoodsList(cri);
		
		System.out.println("cri : " + cri);
		System.out.println("list : " + list);
		
	}
	
	
	// 검색 (동적 쿼리 적용) - 카테고리("C")
	@Test 
	public void getGoodsListTest3() {
		Criteria cri = new Criteria();
		String type = "C";
		String keyword = "";
		String catecode = "202001";		
		
		cri.setType(type);
		cri.setKeyword(keyword);
		cri.setAuthorArr(mapper.getAuthorIdList(keyword));
		cri.setCateCode(catecode);
		
		List<BookVO> list = mapper.getGoodsList(cri);
		
		System.out.println("cri : " + cri);
		System.out.println("list : " + list);
	}
	
	
	
	// 검색 (동적 쿼리 적용) - 카테고리 + 작가 
	@Test 
	public void getGoodsListTest4() {
		Criteria cri = new Criteria();
		String type = "AC";
		String keyword = "생텍쥐페리";	// 카테고리에 존재하는 작가
		//String keyword = "머스크";	// 카테고리에 존재하지 않는 작가
		String catecode = "202001";
		
		cri.setType(type);
		cri.setKeyword(keyword);
		cri.setAuthorArr(mapper.getAuthorIdList(keyword));
		cri.setCateCode(catecode);
		
		List<BookVO> list = mapper.getGoodsList(cri);
		
		System.out.println("cri : " + cri);
		System.out.println("list : " + list);	
		
	}
	
	
	
	
	// 검색 (동적 쿼리 적용) - 카테고리 + 책 제목 
	@Test 
	public void getGoodsListTest5() {
		Criteria cri = new Criteria();
		String type = "CT";			// 카테고리에 존재하는 책
		String keyword = "테스트";	// 카테고리에 존재하지 않는 책
		//String keyword = "없음";
		String catecode = "102001";
		
		cri.setType(type);
		cri.setKeyword(keyword);
		cri.setAuthorArr(mapper.getAuthorIdList(keyword));
		cri.setCateCode(catecode);
		
		List<BookVO> list = mapper.getGoodsList(cri);
		
		System.out.println("cri : " + cri);
		System.out.println("list : " + list);	
		
	}
	
	
	
	
	// 카테고리 리스트 (A or T ==> catecode)
	// |--------- |
	// |catecode|
	// |--------- |
	// |201001  |
	// |--------- |
	
	@Test
	public void getCateListTest1() {
		Criteria cri = new Criteria();
		
		String type ="T";           //검색타입="제목"
		String keyword = "눈";    //작성한 키워드
		
		//String type="A";
		//String keyword = "게이고";
		
		cri.setType(type);
		cri.setKeyword(keyword);
		System.out.println(" type :::: " + type);
		System.out.println(" keyword :::: " + keyword);
		//cri.setAuthorArr(mapper.getAuthorIdList(keyword));
		
		String[] cateList = mapper.getCateList(cri);
		for(String codeNum : cateList) {
			System.out.println("codeNum :::::: " + codeNum);
		}
	}
	

		// 카테고리 정보 얻기 	
		
		//		catecount |catecode |catename |
		//		|----------|---------|---------|
		// 		|1         |201001   |소설       |
		/*
		@Test
		public void getCateInfoTest1() {
			
			Criteria cri = new Criteria();
			
			String type = "TC";
			String keyword = "눈";	
			String cateCode="201001";

			cri.setType(type);
			cri.setKeyword(keyword);
			cri.setCateCode(cateCode);
			
			mapper.getCateInfo(cri);
			
		}
		*/
	
	//상품 정보
	@Test
	public void getGoodsInfo() {
		int bookId = 26;
		BookVO goodsInfo = mapper.getGoodsInfo(bookId); 
		System.out.println("===================");
		System.out.println(goodsInfo);
		System.out.println("===================");
	}
}
