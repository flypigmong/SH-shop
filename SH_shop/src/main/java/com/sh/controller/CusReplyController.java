package com.sh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sh.model.Criteria;
import com.sh.model.Criteria2;
import com.sh.model.CusReplyDTO;
import com.sh.model.CusReplyPageDTO;
import com.sh.service.CusReplyService;

@RestController
@RequestMapping("/cusReply")
public class CusReplyController {

	private static final Logger logger = LoggerFactory.getLogger(CusReplyController.class);
	
	@Autowired
	private CusReplyService cusReplyService;
	
	//고객센터 댓글 등록
	@PostMapping("/enroll")
	public void CusEnrollReplyPost(CusReplyDTO dto) {
		cusReplyService.cusEnrollReply(dto);

	}
	
	/* 댓글 체크 */
	/* memberId, bookId 파라미터 */
	/* 존재 : 1 / 존재x : 0 */
	@PostMapping("/check")
	public String CusReplyCheckPOST(CusReplyDTO dto) {
		return cusReplyService.checkCusReply(dto);
	}
	
	/* 고객센터 댓글 페이징 */
	@GetMapping(value="/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CusReplyPageDTO replyListPOST(Criteria2 cri2) {
		logger.info("cri22222222222222" + cri2);
		return cusReplyService.cusReplyList(cri2);
		
	}
	
	//댓글 삭제
	@PostMapping("/delete")
	public void CusReplyDeletePOST(CusReplyDTO dto) {
		cusReplyService.deleteCusReply(dto);
	}
}
