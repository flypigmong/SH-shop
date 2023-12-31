package com.sh.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.sh.mapper.MemberMapper;
import com.sh.model.AttachImageVO;
import com.sh.model.AuthorVO;
import com.sh.model.BookVO;
import com.sh.model.Criteria;
import com.sh.model.MemberVO;
import com.sh.model.OrderCancelDTO;
import com.sh.model.OrderDTO;
import com.sh.model.PageDTO;
import com.sh.service.AdminService;
import com.sh.service.AuthorService;
import com.sh.service.MemberService;
import com.sh.service.OrderService;
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
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private MemberService memberService;
    
	@Autowired
	MemberMapper membermapper;
	
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
		cri.setAmount(5);
		
		List list = authorService.authorGetList(cri);
		logger.info("list " + list);
		int total = authorService.authorGetTotal(cri);
		logger.info("total" + total);
		
		logger.info("cri.setAmount() : " + cri.getAmount());
		
		if(!list.isEmpty()) {
			model.addAttribute("list",list);	// 작가 존재 경우
		} else {
			model.addAttribute("listCheck", "empty");	// 작가 존재하지 않을 경우
		}
		
		
		/* 페이지 이동 인터페이스 데이터 */
		model.addAttribute("pageMaker", new PageDTO(cri, total));		
		logger.info("model " + model.toString());
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
				
				logger.info("goodsModifyPOST............." + vo);
				int result = adminService.goodsModify(vo);
				rttr.addFlashAttribute("modify_result", result);
	
				return "redirect:/admin/goodsManage";
		}
		
		
		/* 상품 정보 삭제 */
		@PostMapping("/goodsDelete")
		public String goodsDeletePOST(int bookId, RedirectAttributes rttr) {
			
				logger.info("goodsDeletePOST.........");
				
				/* 서버파일 삭제 */
				// 이미지 정보 가져와서 fileList에 저장
				List<AttachImageVO> fileList = adminService.getAttachInfo(bookId);
				
				if(fileList != null) {
							
				    List<Path> pathList = new ArrayList();
							
				  
				    for (AttachImageVO vo : fileList) {
								
				        // 원본 이미지
				        Path path = Paths.get("C:\\upload", vo.getUploadPath(), vo.getUuid() + "_" + vo.getFileName());
				        pathList.add(path);
								
				        // 섬네일 이미지
				        path = Paths.get("C:\\upload", vo.getUploadPath(), "s_" + vo.getUuid()+"_" + vo.getFileName());
				        pathList.add(path);
								
				    }
							
				    
				    for (Path path : pathList) {
				        path.toFile().delete();
				    }
							
				}
				
				int result = adminService.goodsDelete(bookId);
				rttr.addFlashAttribute("delete_result", result);
				
				return "redirect:/admin/goodsManage";
		
		}
		
		/* 작가 정보 삭제 */
		@PostMapping("/authorDelete")
		public String authorDeletePOST(int authorId, RedirectAttributes rttr) {
			
			logger.info("authorDeletePOST..........");
			
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
		@PostMapping(value="/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
		public ResponseEntity<List<AttachImageVO>> uploadAjaxActionPOST(MultipartFile[] uploadFile) { //뷰가 전송한 데이터를 정상적으로 전달받는지 확인하기 위해서 일단 void로 지정
			
				logger.info("uploadAjaxActionPOST........................");
				
				/* 이미지 파일 체크... */
				for(MultipartFile multipartFile : uploadFile) {
					File checkFile = new File(multipartFile.getOriginalFilename()); //전달받은 파일 File 객체로 만들고 File 참조 변수에 대입...
					String type = null; //MIME TYPE을 저장할 String 타입의 type 변수...
					
					try {
						//반환하는 MIME TYPE 데이터를 type 변수에 대입...(checkFile을 Path객체로 만들기위해 toPath 메서드사용)
						type = Files.probeContentType(checkFile.toPath());
						logger.info("MIME TYPE : " + type); //MIME TYPE 확인(image면 image 출력)
					} catch (IOException e) {
						e.printStackTrace();
					} 
					// image가 아니면
					 if(!type.startsWith("image")) {
						
						 List<AttachImageVO> list = null;
						 return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
					 }
				}
				
				String uploadFolder = "C:\\upload";  //파일 저장할 기본 경로
				
				/* 날짜 폴더 경로 */
				
				SimpleDateFormat myDate = new SimpleDateFormat("yyyy-MM-dd");//오늘의 날짜를 지정된 형식의 문자열 데이터로 생성하기 위한 SimpleDateFormat 객체 생성
				Date date = new Date();	//오늘의 날짜를 얻기위해 사용
				String str = myDate.format(date);//String에 대입
				String datePath = str.replace("-", File.separator);//날짜를 2023-08-10이면 '-'를 '/'로 변경
				
				/* 폴더 생성 */
				// 부모 경로 = uploadFolder
				// 자식 경로 = datePath
				File uploadPath = new File(uploadFolder, datePath);
				
				if(uploadPath.exists() == false) { //대상 파일 혹은 디렉터리가 없으면
					uploadPath.mkdirs(); //여러개(-s) 폴더 생성하는 메서드
				}
				
				/* 이미저 정보 담는 객체 */
				List<AttachImageVO> list = new ArrayList();
				
				for(MultipartFile multipartFile : uploadFile) {
					
					/* 이미지 정보 객체생성 */
					AttachImageVO imageVo = new AttachImageVO();
					
					logger.info("-----------------------------------------------");
					logger.info("파일 이름 : " + multipartFile.getOriginalFilename());
					logger.info("파일 타입 : " +  multipartFile.getContentType());
					logger.info("파일 크기 : " +  multipartFile.getSize());			
					
					/* 파일 이름 */
					String uploadFileName = multipartFile.getOriginalFilename();	
					imageVo.setFileName(uploadFileName);
					imageVo.setUploadPath(datePath);
					
					
					/* uuid 적용 파일 이름 */
					// String 타입으로 변경하고 AttachImageVO 객체에 저장
					String uuid = UUID.randomUUID().toString(); 
					imageVo.setUuid(uuid);
					
					//파일 이름을 "UUID_파일 이름" 형식이 되도록 변경
					uploadFileName = uuid + "_" + uploadFileName;
					
					
					/* 파일 위치, 파일 이름을 합친 File 객체 */
					File saveFile = new File(uploadPath, uploadFileName);
					
					/* 파일 저장 */
					try {
						multipartFile.transferTo(saveFile);
					
					/* 방법 2 :thumbnailator 라이브러리 사용 */ 
					File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);				
					BufferedImage buf_oriImage = ImageIO.read(saveFile);
					
					//비율 
					double ratio = 3;
					//넓이 높이
					int width = (int) (buf_oriImage.getWidth() / ratio);
					int height = (int) (buf_oriImage.getHeight() / ratio);					
				
					Thumbnails.of(saveFile)
			        .size(width, height)
			        .toFile(thumbnailFile);						
					} catch (Exception e) {
						e.printStackTrace();
					} 					
					
					list.add(imageVo);
				}
				// List<AttachImageVO> 이고 상태코드가 OK(200)인 ResponseEntity 객체가 생성 (Http의 바디에 추가될 데이터)
				ResponseEntity<List<AttachImageVO>> result = new ResponseEntity<List<AttachImageVO>>(list, HttpStatus.OK);
			
				return result;
		}
		
		/* 이미지 파일 삭제 */
		@PostMapping("/deleteFile")
		public ResponseEntity<String> deleteFile(String fileName){
				logger.info("deleteFile......" + fileName);
				File file = null;
				
			try {
				/* 썸네일 파일 삭제 */
				file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
				file.delete();
				
				/* 원본 파일 삭제 */
				String originFileName = file.getAbsolutePath().replace("s_", "");
				logger.info("originName : " + originFileName);
				file = new File(originFileName);
				file.delete();
			} catch (Exception e) {
				 e.printStackTrace();
				 return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);
			}
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}
		
		
		/* 주문 현황 페이지 */
		@GetMapping("/orderList")
		public String orderListGET(Criteria cri, Model model) {

			List<OrderDTO> list = adminService.getOrderList(cri);
			
			if(!list.isEmpty()) {
				model.addAttribute("list", list);
				model.addAttribute("pageMaker", new PageDTO(cri, adminService.getOrderTotal(cri)));
			} else {
				model.addAttribute("listCheck", "empty");
			}
			
			return "/admin/orderList";
		}
		
		/* 주문 삭제(취소) */
		@PostMapping("/orderCancle")
		public String orderCancelPOST(OrderCancelDTO dto, HttpServletRequest request) {
		
			orderService.orderCancle(dto);

			MemberVO member = new MemberVO();
			member.setMemberId(dto.getMemberId());
			System.out.println("memberId를 넣은 후 : " + member);

			HttpSession session = request.getSession(); 
			System.out.println("session22222: " + session);

			
			try {
				MemberVO memberLogin = membermapper.memberLogin(member);
				System.out.println("memberLogin2222 : " + memberLogin);
				
				memberLogin.setMemberPw("");
				System.out.println("setMemberPw이후 :" + member );
				
				session.setAttribute("member", memberLogin);
				System.out.println("member44444 : " + memberLogin);
	
			
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			/*
			 * MemberVO sessionMember = (MemberVO) session.getAttribute("member"); // 세션에서
			 * memberVO 객체 가져오기
			 * 
			 * sessionMember.setMoney(memberVO.getMoney()); // 세션에 저장된 memberVO의 돈 변경하기
			 * sessionMember.setPoint(memberVO.getPoint()); // 세션에 저장된 memberVO의 포인트 변경하기
			 */			
			return "redirect:/admin/orderList?keyword=" + dto.getKeyword() + "&amount=" + dto.getAmount() + "&pageNum=" + dto.getPageNum();
			
		}
		
	    //회원 관리 페이지 접속 
	    @GetMapping(value="/memberManage")
	    public void memberManageGET(Criteria cri, Model model) throws Exception{
	    	logger.info("멤버 관리 페이지 접속");
	  
	    	// 멤버 목록 출력 데이터 
	    	List list = memberService.memberGetList(cri);
	    	model.addAttribute("list", list);
	    
	    	//페이지 이동 인터페이스 데이터 
	        int total = memberService.memberGetTotal(cri);
	        
	        PageDTO pageMaker = new PageDTO(cri, total);
	        
	        model.addAttribute("pageMaker", pageMaker);
	    }
	    
	    
}