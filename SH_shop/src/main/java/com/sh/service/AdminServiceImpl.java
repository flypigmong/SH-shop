package com.sh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.AdminMapper;
import com.sh.model.BookVO;
import com.sh.model.CateVO;
import com.sh.model.Criteria;

import lombok.extern.log4j.Log4j;


@Service
@Log4j
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public void bookEnroll(BookVO book) {
		
		log.info("(service)bookEnroll...........");
		
		adminMapper.bookEnroll(book);
		
	}

	@Override
	public List<CateVO> cateList() {
		
		log.info("(service)cateList................");
		
		return adminMapper.cateList();
	}

	@Override
	public List<BookVO> goodsGetList(Criteria cri) {
		log.info("goodsGetTotalList()............");
		return adminMapper.goodsGetList(cri);
	}

	@Override
	public int goodsGetTotal(Criteria cri) {
		log.info("goodsGetTotal()....................");
		return adminMapper.goodsGetTotal(cri);
	}

}
