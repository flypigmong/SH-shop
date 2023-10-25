package com.sh.service;

import com.sh.model.Criteria;
import com.sh.model.Criteria2;
import com.sh.model.CusReplyDTO;
import com.sh.model.CusReplyPageDTO;

public interface CusReplyService {

	
	/* 고객센터 댓글 등록 */
	public int cusEnrollReply(CusReplyDTO dto);
	
	/* 고객센터 댓글 존재 체크*/
	public String checkCusReply(CusReplyDTO dto);
	
	/* 고객센터 댓글 페이징*/
	public CusReplyPageDTO cusReplyList(Criteria2 cri2);
	
	// 고객센터 댓글 삭제
	public int deleteCusReply(CusReplyDTO dto);
	
}
