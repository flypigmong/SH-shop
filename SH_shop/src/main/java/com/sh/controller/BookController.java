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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sh.mapper.AttachMapper;
import com.sh.model.AttachImageVO;
import com.sh.model.BookVO;
import com.sh.model.Criteria;
import com.sh.model.PageDTO;
import com.sh.model.ReplyDTO;
import com.sh.service.BookService;
import com.sh.service.ReplyService;



@Controller
public class BookController {

	private static final Logger logger = LoggerFactory.getLogger(BookController.class);

	@Autowired
	private AttachMapper attachMapper;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReplyService replyService;
	
	
	/* 메인 페이지 이동 */
	@GetMapping(value={"/" , "/main"})
		public String mainPageGET(Model model) {
			logger.info("메인 페이지 진입");
			
			model.addAttribute("cate1", bookService.getCateCode1()); // 카테고리
			model.addAttribute("cate2", bookService.getCateCode2());
			model.addAttribute("ls", bookService.likeSelect()); // 메인페이지 List 정보 담긴 객체
			logger.info(" 국내 :" + bookService.getCateCode1());
			logger.info(" 국외 :" + bookService.getCateCode1());
			
			return "main";
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
		logger.info("::pageMaker" + new PageDTO(cri, bookService.goodsGetTotal(cri)));
		model.addAttribute("filter_info", bookService.getCateInfoList(cri)); // 상품목록 페이지에 필터 정보 전달
		logger.info("::filter_info" + bookService.getCateInfoList(cri));
		String[] typeArr = cri.getType().split("");
		
		for (String s : typeArr) {   // type이 "A","AC","T", "TC" 인 경우에만 호출
			if (s.equals("T") || s.equals("A")) { //"T" 나 "A"를 포함
				model.addAttribute("filter_info", bookService.getCateInfoList(cri));
			}
			
		}
		
		return "search";
		
	}
	
	
	/* 상품 상세 */
	@GetMapping("/goodsDetail/{bookId}")
	public String goodsDetailGet(@PathVariable("bookId") int bookId, Model model) {
		
		logger.info("goodsDetailGET().........");
		
		model.addAttribute("goodsInfo", bookService.getGoodsInfo(bookId));
		
		
		return "/goodsDetail";	
	}
	
	
	/* 리뷰 쓰기 */
	@GetMapping("/replyEnroll/{memberId}")
	public String replyEnrollWindowGET(@PathVariable("memberId")String memberId, int bookId, Model model) {
		BookVO book = bookService.getBookIdName(bookId);
		model.addAttribute("bookInfo", book);
		model.addAttribute("memberId", memberId);
		
		return "/replyEnroll";
	}
	
	
	/* 리뷰 수정 팝업창 */
	@GetMapping("/replyUpdate")
	public String replyUpdateWindowGET(ReplyDTO dto, Model model) {
		BookVO book = bookService.getBookIdName(dto.getBookId());
		model.addAttribute("bookInfo", book);
		model.addAttribute("replyInfo", replyService.getUpdateReply(dto.getReplyId()));
		model.addAttribute("memberId", dto.getMemberId());
		
		return "/replyUpdate";
	}
	
}




