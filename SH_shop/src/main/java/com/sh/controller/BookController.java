package com.sh.controller;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sh.mapper.AttachMapper;
import com.sh.model.AttachImageVO;
import com.sh.model.BookVO;
import com.sh.model.Criteria;
import com.sh.model.PageDTO;
import com.sh.service.BookService;



@Controller
public class BookController {

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private AttachMapper attachMapper;
	
	@Autowired
	private BookService bookService;
	
	
	/* 메인 페이지 이동 */
	@RequestMapping(value="/main", method = RequestMethod.GET) 
		public void mainPageGET(Model model) {
			logger.info("메인 페이지 진입");
			
			model.addAttribute("cate1", bookService.getCateCode1()); // 카테고리
			model.addAttribute("cate2", bookService.getCateCode2());
			logger.info(" 국내 :" + bookService.getCateCode1());
			logger.info(" 국외 :" + bookService.getCateCode1());
		}
		
	/*  이미지 출력 */
	@GetMapping("/display")
	public ResponseEntity<byte[]> getImage(String fileName) {
		System.out.println("fileName: " + fileName);
		logger.info("controller::: getImage()........." +fileName);
		File file = new File("c:\\upload\\" + fileName);
		
		ResponseEntity<byte[]> result = null;
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		System.out.println("result" + result);
			logger.info("result  : " + result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	/*  이미지 정보 반환 */
	@GetMapping(value="/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AttachImageVO>>getAttachList(int bookId) {
		
	logger.info("getAttachList............" + bookId);
		
		return new ResponseEntity(attachMapper.getAttachList(bookId),HttpStatus.OK);
		
	}
	
	
	/* 상품 검색 */
	@GetMapping("/search")
	public String searchGoodsGET(Criteria cri, Model model) {
		
		logger.info("cri : " + cri);
		
		List<BookVO> list = bookService.getGoodsList(cri);
		logger.info("pre list : " + list);
		if(!list.isEmpty()) {
			model.addAttribute("list", list);
			logger.info("list : " + list);
		} else {
			model.addAttribute("listCheck", "empty");
			
			return "search";
		}
		
		model.addAttribute("pageMaker", new PageDTO(cri, bookService.goodsGetTotal(cri)));
		
		return "search";
		
	}
	
}




