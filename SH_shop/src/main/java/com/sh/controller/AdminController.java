package com.sh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sh.model.AuthorVO;
import com.sh.service.AuthorService;

@Controller
@RequestMapping("/admin")
public class AdminController {
 
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    
    @Autowired 
    private AuthorService authorService;
    
    /* 관리자 메인 페이지 이동 */
    @GetMapping(value="main")
    public void adminMainGET() throws Exception{
        
        logger.info("관리자 페이지 이동");
        
    }
    
    /* 상품 등록 페이지 접속 */
    @GetMapping(value = "goodsManage")
    public void goodsManageGET() throws Exception{
        logger.info("상품 등록 페이지 접속");
    }
    
    /* 상품 등록 페이지 접속 */
    @GetMapping(value = "goodsEnroll")
    public void goodsEnrollGET() throws Exception{
        logger.info("상품 등록 페이지 접속");
    }
    
    /* 작가 등록 페이지 접속 */
    @GetMapping(value = "authorEnroll")
    public void authorEnrollGET() throws Exception{
        logger.info("작가 등록 페이지 접속");
    }
    
    /* 작가 관리 페이지 접속 */
    @GetMapping(value = "authorManage")
    public void authorManageGET() throws Exception{
        logger.info("작가 관리 페이지 접속");
    }     
    
    @PostMapping(value="authorEnroll.do")
    public String authorEnrollPOST(AuthorVO author, RedirectAttributes rttr) throws Exception{
 
    	logger.info("authorEnroll :" +  author);
    	
    	authorService.authorEnroll(author);      // 작가 등록 쿼리 수행
    	
    	rttr.addFlashAttribute("enroll_result", author.getAuthorName());
    	
    	return "redirect:/admin/authorManage";
    	
    }
    
}