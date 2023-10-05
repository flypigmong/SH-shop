package com.sh.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sh.model.BookVO;
import com.sh.model.Criteria;
import com.sh.service.BookService;

	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root/root-context.xml")
	public class BookServiceTests {
	
		@Autowired
		BookService service;
		
	//	|---------|
	//	|catecode |
	//	|---------|
	//	|201001   |
	//	|---------|
		
	//	|----------|---------|---------|
	//	|catecount |catecode |catename |
	//	|----------|---------|---------|
	//	|1         |201001   |소설       |
	//	|----------|---------|---------|
		
		/*
		@Test
		public void getCateInfoListTest1() {                
			Criteria cri = new Criteria();    
			
			String type = "TC";  		 // 제목 + 내용
			String keyword = "눈";
			//String keyword = "없는거찾기";	
			String cateCode="201001";

			cri.setType(type);
			cri.setKeyword(keyword);
			cri.setCateCode(cateCode);
			
			System.out.println("List<CateFilterDTO> : " + service.getCateInfoList(cri));
			
		}
		
		
		//위와 동일결과
		@Test
		public void getCateInfoListTest2() {
			Criteria cri = new Criteria();
		
			String type = "AC";       // 작가 + 내용
			String keyword = "게이고";	
			//String keyword = "없는거찾기";	
			String cateCode = "201001";

			cri.setType(type);
			cri.setKeyword(keyword);
			cri.setCateCode(cateCode);
			
			System.out.println("List<CateFilterDTO> : " + service.getCateInfoList(cri));
			
		}	

		
		 //위와 동일결과
		@Test
		public void getCateInfoListTest3() {
			Criteria cri = new Criteria();
		
			String type = "T";         // 제목
			String keyword = "눈";
			//String keyword = "없는거찾기";	
			

			cri.setType(type);
			cri.setKeyword(keyword);
			
			System.out.println("List<CateFilterDTO> : " + service.getCateInfoList(cri));
			
		}	
		
		// 위와 동일결과
		@Test
		public void getCateInfoListTest4() {
			Criteria cri = new Criteria();
		
			String type = "A";     // 작가 
			String keyword = "게이고";	
			//String keyword = "없는거찾기";	
			

			cri.setType(type);
			cri.setKeyword(keyword);
			
			System.out.println("List<CateFilterDTO> : " + service.getCateInfoList(cri));
			
		}
		
		*/
		
		
		// 상품 상세 정보
		@Test
		public void getGoodsInfoTest() {
			
			int bookId = 29;
			
			BookVO goodsInfo = service.getGoodsInfo(bookId);
			
			System.out.println("===결과===");
			System.out.println("전체 : " + goodsInfo);
			System.out.println("bookId : " +goodsInfo.getBookId());
			System.out.println("이미지 정보: " + goodsInfo.getImageList());
		
		}
		
	}
