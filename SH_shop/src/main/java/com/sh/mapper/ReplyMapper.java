package com.sh.mapper;

import com.sh.model.ReplyDTO;

public interface ReplyMapper {

	/* 댓글 등록 */
	public int enrollReply(ReplyDTO dto);
	
}
