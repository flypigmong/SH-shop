package com.sh.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.model.AuthorVO;
import com.sh.model.BookVO;
import com.sh.model.Criteria;
import com.sh.model.PageDTO;
import com.sh.service.AdminService;
import com.sh.service.AuthorService;

@Controller
@RequestMapping("/admin")
public class AdminController {
 
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    
    @Autowired 
    private AuthorService authorService;
    
    @Autowired
    private AdminService adminService;
    
    /* 관리자 메인 페이지 이동 */
    @GetMapping(value="main")
    public void adminMainGET() throws Exception{
        
        logger.info("관리자 페이지 이동");
        
    }
    
    /* 상품 등록 페이지 접속 */
    @GetMapping(value = "goodsManage")
    public void goodsManageGET(Criteria cri, Model model) throws Exception{
        
    	logger.info("상품 목록 페이지 접속");
    	
		/* 상품 리스트 데이터 */
		List list = adminService.goodsGetList(cri);
		
		if(!list.isEmpty()) {
			model.addAttribute("list", list);
		} else {
			model.addAttribute("listCheck", "empty");
			return;
		}
		
		/* 페이지 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, adminService.goodsGetTotal(cri)));
    }
    
    /* 상품 등록 페이지 접속 */
    @GetMapping(value = "goodsEnroll")
    public void goodsEnrollGET(Model model) throws Exception{
        logger.info("상품 등록 페이지 접속");
        
        ObjectMapper objm = new ObjectMapper(); //jackson 사용하기 위해 ObjectMapper클래스를 인스턴스화
        
        List list = adminService.cateList();
        
        String cateList = objm.writeValueAsString(list);
        
        model.addAttribute("cateList", cateList);
        
        logger.info("변경 전............" + list);
        logger.info("변경 후............" + cateList);
        
    }
    
    
    /* 작가 등록 페이지 접속 */
    @GetMapping(value = "authorEnroll")
    public void authorEnrollGET() throws Exception{
        logger.info("작가 등록 페이지 접속");
    }
    
    /* 작가 관리 페이지 접속 */
    @GetMapping(value = "authorManage")
    public void authorManageGET(Criteria cri, Model model) throws Exception{
       
        logger.info("작가 관리 페이지 접속.........." + cri);
        
        /* 작가 목록 출력 데이터 */
        List list = authorService.authorGetList(cri);
        
		if(!list.isEmpty()) {
			model.addAttribute("list",list);	// 작가 존재 경우
		} else {
			model.addAttribute("listCheck", "empty");	// 작가 존재하지 않을 경우
		}
        
        /* 페이지 이동 인터페이스 데이터 */
        int total = authorService.authorGetTotal(cri);
        
        PageDTO pageMaker = new PageDTO(cri, total);
        
        model.addAttribute("pageMaker", pageMaker);
        
        /* 페이지 이동 인터페이스 데이터  코드 한 줄로 표현...
        model.addAttribute("pageMaker", new PageDTO(cri, authorService.authorGetTotal(cri)));
    	*/
        
    	logger.info("작가 관리 페이지 접속");
    }     
    
    @PostMapping(value="authorEnroll.do")
    public String authorEnrollPOST(AuthorVO author, RedirectAttributes rttr) throws Exception{
 
    	logger.info("authorEnroll :" +  author);
    	
    	authorService.authorEnroll(author);      // 작가 등록 쿼리 수행
    	
    	rttr.addFlashAttribute("enroll_result", author.getAuthorName());
    	
    	return "redirect:/admin/authorManage";
    	
    }
    
    
	/* 작가 상세,수정 페이지 */
	@GetMapping({"/authorDetail", "/authorModify"})
	public void authorGetInfoGET(int authorId, Criteria cri, Model model) throws Exception {
		
		logger.info("authorDetail......." + authorId);
		
		/* 작가 관리 페이지 정보 */
		model.addAttribute("cri", cri);
		
		/* 선택 작가 정보 */
		model.addAttribute("authorInfo", authorService.authorGetDetail(authorId));
		
	}
    
	
	/* 작가 정보 수정 */
	@PostMapping("/authorModify")
	public String authorModifyPOST(AuthorVO author, RedirectAttributes rttr) throws Exception{ //뷰에서 전달받은 데이터를 전달받기 위해 AuthorVO를 파라미터로 부여 
		
		logger.info("authorModifyPOST......." + author);
		
		int result = authorService.authorModify(author);
		
		rttr.addFlashAttribute("modify_result", result);
		
		return "redirect:/admin/authorManage";
		
	}
	
	/* 상품 등록 */
	@PostMapping("/goodsEnroll")
	public String goodsEnrollPOST(BookVO book, RedirectAttributes rttr) {
		
		logger.info("goodsEnrollPOST......" + book);
		adminService.bookEnroll(book);
		rttr.addFlashAttribute("enroll_result", book.getBookName());
		
		return "redirect:/admin/goodsManage";
	}
	
	

	/* 작가 검색 팝업창 */
	@GetMapping("/authorPop")
	public void authorPopGET(Criteria cri, Model model) throws Exception {
	
		/* 게시물 출력 데이터 */
		List list = authorService.authorGetList(cri);
		
		cri.setAmount(5);
		
		if(!list.isEmpty()) {
			model.addAttribute("list",list);	// 작가 존재 경우
		} else {
			model.addAttribute("listCheck", "empty");	// 작가 존재하지 않을 경우
		}
		
		
		/* 페이지 이동 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, authorService.authorGetTotal(cri)));		
		logger.info("authorPopGET...........");
		
	}
	
	
		/* 상품 조회 페이지 */
		@GetMapping("/goodsDetail")
		public void goodsGetInfoGET(int bookId, Criteria cri, Model model) {
			
			logger.info("goodsGetInfo().............." + bookId);
			
			/* 목록 페이지에 데이터 가져오기 위한 조건 정보 */
			model.addAttribute("cri", cri);
			
			/* 조회 페이지에 데이터 가져오기 위한 조건 정보 */
			model.addAttribute("goodsInfo", adminService.goodsGetDetail(bookId));
		
		}
}