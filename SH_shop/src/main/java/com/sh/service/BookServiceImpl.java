package com.sh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.AttachMapper;
import com.sh.mapper.BookMapper;
import com.sh.model.AttachImageVO;
import com.sh.model.BookVO;
import com.sh.model.CateVO;
import com.sh.model.Criteria;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BookServiceImpl implements BookService {

	@Autowired
	private BookMapper bookMapper;
	
	@Autowired
	private AttachMapper attachMapper;
	
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
		
		List<BookVO> list = bookMapper.getGoodsList(cri);
		log.info("list ::::::: " + list);
		System.out.println("list ::::::::: " +list );
		list.forEach(book -> {
			
			int bookId = book.getBookId();
			System.out.println("bookId ::::" +bookId);
			
			List<AttachImageVO> imageList = attachMapper.getAttachList(bookId);
			book.setImageList(imageList);
			System.out.println("imageList ::::::: " + imageList );
		});
		
		return list;
	}

	/* 상품 총 갯수*/
	@Override
	public int goodsGetTotal(Criteria cri) {
		log.info("goodsGetTotal()......");
		return bookMapper.goodsGetTotal(cri);
	}
	
	/* 국내 카테고리 리스트 */
	@Override
	public List<CateVO> getCateCode1() {
		
		log.info("getCateCode1()..................");
		
		return bookMapper.getCateCode1();
	}

	/* 국외 카테고리 리스트 */
	@Override
	public List<CateVO> getCateCode2() {
		
		log.info("getCateCode2()..................");
		
		return bookMapper.getCateCode2();
	}

}
