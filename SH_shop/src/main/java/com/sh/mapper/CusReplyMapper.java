package com.sh.mapper;

import java.util.List;

import com.sh.model.Criteria;
import com.sh.model.Criteria2;
import com.sh.model.CusReplyDTO;

public interface CusReplyMapper {

	// 고객센터 글 등록
	public int cusEnrollReply(CusReplyDTO dto);

	//고객센터 댓글 체크
	public Integer checkCusReply(CusReplyDTO dto);

	//고객센터 댓글 페이징
	public List<CusReplyDTO> getCusReplyList(Criteria2 cri);
	
	//고객센터 댓글 총 갯수(페이징)
	public int getCusReplyTotal(int postNo);
	
	//고객센터 댓글 수정
}
