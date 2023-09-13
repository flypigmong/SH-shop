package com.sh.service;

import com.sh.model.ReplyDTO;

public interface ReplyService {

	/* 댓글 등록 */
	public int enrollReply(ReplyDTO dto);
	
	/* 댓글 존재 체크 */
	public String checkReply(ReplyDTO dto);
	
}
