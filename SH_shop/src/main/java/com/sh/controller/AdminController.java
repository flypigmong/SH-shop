package com.sh.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.model.AuthorVO;
import com.sh.model.BookVO;
import com.sh.model.Criteria;
import com.sh.model.PageDTO;
import com.sh.service.AdminService;
import com.sh.service.AuthorService;
import com.sun.source.tree.AssertTree;

import net.coobird.thumbnailator.Thumbnails;

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
	
	
		/* 상품 조회,수정 페이지 */
		@GetMapping({"/goodsDetail", "/goodsModify" })
		public void goodsGetInfoGET(int bookId, Criteria cri, Model model) throws JsonProcessingException {
			
			logger.info("goodsGetInfo().............." + bookId);
			
			ObjectMapper mapper = new ObjectMapper();
			
			/* 카테고리 리스트 데이터 가져오기 */
			model.addAttribute("cateList", mapper.writeValueAsString(adminService.cateList()));					
			
			/* 목록 페이지에 데이터 가져오기 위한 조건 정보 */
			model.addAttribute("cri", cri);
			
			/* 조회 페이지에 데이터 가져오기 위한 조건 정보 */
			model.addAttribute("goodsInfo", adminService.goodsGetDetail(bookId));
		
		}
		
		
		/* 상품 정보 수정 */
		@PostMapping("/goodsModify")
		public String goodsModifyPOST(BookVO vo,RedirectAttributes rttr) {
				
				logger.info("controller:::goodsModifyPOST............." + vo);
				int result = adminService.goodsModify(vo);
				rttr.addFlashAttribute("modify_result", result);
	
				return "redirect:/admin/goodsManage";
		}
		
		
		/* 상품 정보 삭제 */
		@PostMapping("/goodsDelete")
		public String goodsDeletePOST(int bookId, RedirectAttributes rttr) {
			
				logger.info("controller:::goodsDeletePOST.........");
				int result = adminService.goodsDelete(bookId);
				rttr.addFlashAttribute("delete_result", result);
				
				return "redirect:/admin/goodsManage";
		
		}
		
		/* 작가 정보 삭제 */
		@PostMapping("/authorDelete")
		public String authorDeletePOST(int authorId, RedirectAttributes rttr) {
			
			logger.info("controller:::authorDeletePOST..........");
			
			int result = 0; //0으로 초기화
			
			try {
				//참조되지않는 행 지울때 삭제 수행하고 작가목록 페이지로 1전송.			
				result = authorService.authorDelete(authorId); 
				
			} catch (Exception e) {
				
				e.printStackTrace();
				result = 2;  // 예외발생시 작가목록 페이지로 2 전송
				rttr.addFlashAttribute("delete_result", result);
				
				return "redirect:/admin/authorManage";
				
			}
			
			
			rttr.addFlashAttribute("delete_result", result); //1 or 2 전송
			logger.info("result " + result);
			return "redirect:/admin/authorManage";
			
		}	
		
		
		/* 첨부 파일 업로드 */
		@PostMapping("/uploadAjaxAction")
		public void uploadAjaxActionPOST(MultipartFile[] uploadFile) { //뷰가 전송한 데이터를 정상적으로 전달받는지 확인하기 위해서 일단 void로 지정
			
				logger.info("controller:::uploadAjaxActionPOST........................");
				String uploadFolder = "C:\\upload";  //파일 저장할 기본 경로
				
				/* 날짜 폴더 경로 */
				
				//오늘의 날짜를 지정된 형식의 문자열 데이터로 생성하기 위한 SimpleDateFormat 객체 생성
				SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd");
				
				//오늘의 날짜를 얻기위해 사용
				Date date = new Date();
				
				//String에 대입
				String str = myDate.format(date);
				
				//날짜를 2023-08-10이면 '-'를 '/'로 변경
				String datePath = str.replace("-", File.separator);
				
				/* 폴더 생성 */
				// 부모 경로 = uploadFolder
				// 자식 경로 = datePath
				File uploadPath = new File(uploadFolder, datePath);
				
				if(uploadPath.exists() == false) { //대상 파일 혹은 디렉터리가 없으면
					uploadPath.mkdirs(); //여러개(-s) 폴더 생성하는 메서드
				}
				
				for(MultipartFile multipartFile : uploadFile) {
					logger.info("-----------------------------------------------");
					logger.info("파일 이름 : " + multipartFile.getOriginalFilename());
					logger.info("파일 타입 : " +  multipartFile.getContentType());
					logger.info("파일 크기 : " +  multipartFile.getSize());			
					
					/* 파일 이름 */
					String uploadFileName = multipartFile.getOriginalFilename();	
					
					/* uuid 적용 파일 이름 */
					// String 타입으로 변경
					String uuid = UUID.randomUUID().toString(); 
					
					//파일 이름을 "UUID_파일 이름" 형식이 되도록 변경
					uploadFileName = uuid + "_" + uploadFileName;
					
					
					/* 파일 위치, 파일 이름을 합친 File 객체 */
					File saveFile = new File(uploadPath, uploadFileName);
					
					/* 파일 저장 */
					try {
						multipartFile.transferTo(saveFile);
					
					/* 방법1

					//썸네일 이미지 만들기
					File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);
					//BufferedImage 타입으로 변경
					BufferedImage buffered_orimage = ImageIO.read(saveFile);
					logger.info("buffered_orimage" + buffered_orimage);
					
					// 비율 
					double ratio = 3;
					// 넓이 높이
					int width = (int) (buffered_orimage.getWidth() / ratio);
					int height = (int) (buffered_orimage.getHeight() / ratio);			
					
					//BufferedImage 객체 생성하고 참조 변수 대입(도화지)
					BufferedImage buffered_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
					logger.info("buffered_image"+buffered_image );
					
					//그림을 그리기 위한 Graphic2D 객체 생성(도화지에 그림그리기)
					Graphics2D graphic = buffered_image.createGraphics();
					logger.info("graphic"+graphic );
					//썸네일을 지정한 크기로 변경하여 0,0 좌표부터 시작해서 넓이x높이(300x500) 으로 크기 변경(도화지에 그림그리기2)
					graphic.drawImage(buffered_image, 0, 0,width,height, null);
					//썸네일 파일 저장(파일로 저장할이미지,이미지 형식,저장될 경로와 이름으로 생성한 File객체(thumbnailFile)
					ImageIO.write(buffered_image, "jpg", thumbnailFile);
					*/
						
					/* 방법 2 :thumbnailator 라이브러리 사용 */ 
					File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);				
					BufferedImage buffered_orimage = ImageIO.read(saveFile);
					
					//비율 
					double ratio = 3;
					//넓이 높이
					int width = (int) (buffered_orimage.getWidth() / ratio);
					int height = (int) (buffered_orimage.getHeight() / ratio);					
				
					Thumbnails.of(saveFile)
			        .size(width, height)
			        .toFile(thumbnailFile);						
					} catch (Exception e) {
						e.printStackTrace();
					} 					
					
				}
		}
		
}