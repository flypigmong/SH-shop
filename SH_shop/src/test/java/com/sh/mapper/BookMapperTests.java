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
	
	@Test
	public void getAuthorId() {
		
		String keyword = "베르";
		
		String[] list = mapper.getAuthorIdList(keyword);
		
		System.out.println("결과 :" + list.toString());
		
		for(String id : list) {
			System.out.println(" 개별 결과 :" + id);
		}
	}
}
