package com.sh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sh.mapper.AdminMapper;
import com.sh.model.AttachImageVO;
import com.sh.model.BookVO;
import com.sh.model.CateVO;
import com.sh.model.Criteria;

import lombok.extern.log4j.Log4j;


@Service
@Log4j
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	/* 상품 등록 */
	@Transactional
	@Override
	public void bookEnroll(BookVO book) {
		
		log.info("service:::bookEnroll...........");
		
		adminMapper.bookEnroll(book);
		
		//이미지 없으면 밑에 for문 실행안하고 조기종료
		if(book.getImageList() == null || book.getImageList().size() <= 0) {
			return;
		}
		
		// ImageList 요소 하나씩 넘겨주기
		for(int i=0; i<book.getImageList().size(); i++) {
			AttachImageVO attachVO = book.getImageList().get(i);
			attachVO.setBookId(book.getBookId());
			adminMapper.imageEnroll(attachVO);
			}
		
	}

	@Override
	public List<CateVO> cateList() {
		
		log.info("service:::cateList................");
		
		return adminMapper.cateList();
	}

	@Override
	public List<BookVO> goodsGetList(Criteria cri) {
		log.info("service:::goodsGetTotalList()............");
		return adminMapper.goodsGetList(cri);
	}

	@Override
	public int goodsGetTotal(Criteria cri) {
		log.info("service:::goodsGetTotal()....................");
		return adminMapper.goodsGetTotal(cri);
	}

	/*  상품 상세정보  */
	@Override
	public BookVO goodsGetDetail(int bookId) {
		log.info("service:::goodsGetDetail()....................");
		return adminMapper.goodsGetDetail(bookId);
	}

	/*  상품 정보 수정 */
	@Transactional
	@Override
	public int goodsModify(BookVO vo) {
		
		int result = adminMapper.goodsModify(vo);
		
		// 이미지파일이 정상적(result==1)이고 , 이미지정보가 존재할 때 
		if(result == 1 && vo.getImageList() != null && vo.getImageList().size() > 0) {
			adminMapper.deleteImageAll(vo.getBookId()); //정보 모두 삭제
	
			// 각 요소 순서대로 이미지 정보를 DB에 저장..
			for (AttachImageVO attach : vo.getImageList()) {
							
			    attach.setBookId(vo.getBookId());
			    adminMapper.imageEnroll(attach);
			
			};		
		
		}
		
		return result;
	}

	/* 상품 정보 삭제 */
	@Override
	public int goodsDelete(int bookId) {
		log.info("service:::goodsDelete()...................");
		return adminMapper.goodsDelete(bookId) ;
	}

	/* 지정 상품 이미지 정보 얻기 */
	@Override
	public List<AttachImageVO> getAttachInfo(int bookId) {
		log.info("getAttachInfo.......");
		return adminMapper.getAttachInfo(bookId);
	}

}
