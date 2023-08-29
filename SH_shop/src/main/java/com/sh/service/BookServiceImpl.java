package com.sh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.BookMapper;
import com.sh.model.BookVO;
import com.sh.model.Criteria;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;
	
	/* 상품 검색 */
	@Override
	public List<BookVO> getGoodsList(Criteria cri) {
		log.info("getGoodsList().....");
		
		String type = cri.getType();
		String[] typeArr = type.split("");
		String[] authorArr = bookMapper.getAuthorIdList(cri.getKeyword());
		
		for(String t : typeArr) {
			if(t.equals("A")) {
				cri.setAuthorArr(authorArr);
			}
		}
		
		return bookMapper.getGoodsList(cri);
	}

	/* 상품 총 갯수*/
	@Override
	public int goodsGetTotal(Criteria cri) {
		log.info("goodsGetTotal()......");
		return bookMapper.goodsGetTotal(cri);
	}

}
