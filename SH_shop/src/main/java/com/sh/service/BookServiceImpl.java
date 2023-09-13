package com.sh.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.AdminMapper;
import com.sh.mapper.AttachMapper;
import com.sh.mapper.BookMapper;
import com.sh.model.AttachImageVO;
import com.sh.model.BookVO;
import com.sh.model.CateFilterDTO;
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
	
	@Autowired
	private AdminMapper adminMapper;
	
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

	/* 검색결과 카테고리 필터 정보*/
	@Override
	public List<CateFilterDTO> getCateInfoList(Criteria cri) {
		
		List<CateFilterDTO> filterInfoList = new ArrayList<CateFilterDTO>();
		log.info("filterInfoList" + filterInfoList );
		
		String[] typeArr = cri.getType().split(""); // 검색 타입을 저장할 배열 생성 
		log.info(":::: typeArr" + cri.getType().split("") );
		
		
		String [] authorArr; // 저자정보 저장할 배열
		
		for (String type : typeArr) { // typeArr 배열의 각 요소를 반복실행
				if(type.equals("A")) { // type=A이면 , 
//getAuthorIdList 메서드로 cri 객체의 getKeyword 메서드로 검색 키워드를 
//가져와서 저자 아이디 리스트를 반환해서 그 값을 authorArr에 저장
					authorArr = bookMapper.getAuthorIdList(cri.getKeyword());
					if(authorArr.length == 0 ) { //authorArr 배열이 요소를 가지지 않는 경우 getCateInfo() 메서드 실행x
						return filterInfoList;
					}
						cri.setAuthorArr(authorArr); // cri객체에 저자정보 저장
				}
		}
		
		//cateCode(카테고리 코드)를 반환해주는 getAuthorIdList를 호출하고 반환 값을 cateList 변수에 저장
		String[] cateList = bookMapper.getCateList(cri);
		log.info("getCateList" +cateList);
		String tempCateCode = cri.getCateCode(); // 임시로 cateCode 값 담기
		
		for (String cateCode : cateList) {
			cri.setCateCode(cateCode); // cateList 요소에 있는 cateCode를 cateCode 변수에 저장
			CateFilterDTO filterInfo = bookMapper.getCateInfo(cri);
			filterInfoList.add(filterInfo);
		}
		
		//임시로 저장해둔 tempCateCode 값을 cateCode 에 저장
		cri.setCateCode(tempCateCode);
		
		return filterInfoList;
	}

	@Override
	public BookVO getGoodsInfo(int bookId) {
		
		BookVO goodsInfo	= bookMapper.getGoodsInfo(bookId);
		goodsInfo.setImageList(adminMapper.getAttachInfo(bookId));
		
		return goodsInfo;
	}

	@Override
	public BookVO getBookIdName(int bookId) {
		
		return bookMapper.getBookIdName(bookId);
	}

}
