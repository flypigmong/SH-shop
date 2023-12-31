package com.sh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.CusReplyMapper;
import com.sh.model.Criteria;
import com.sh.model.Criteria2;
import com.sh.model.CusReplyDTO;
import com.sh.model.CusReplyPageDTO;
import com.sh.model.PageDTO;
import com.sh.model.PageDTO2;
import com.sh.model.ReplyPageDTO;

@Service
public class CusReplyServiceImpl implements CusReplyService{

	@Autowired
	private CusReplyMapper cusReplyMapper;

	@Override
	public int cusEnrollReply(CusReplyDTO dto) {
		
		int result = cusReplyMapper.cusEnrollReply(dto);
		
		return result;
	}

	@Override
	public String checkCusReply(CusReplyDTO dto) {
		Integer result = cusReplyMapper.checkCusReply(dto);
		
		if(result == null) {
			return "0";
		} else {
			return "1";
		}
	}

	//댓글 페이징
	@Override
	public CusReplyPageDTO cusReplyList(Criteria2 cri) {
		CusReplyPageDTO dto = new CusReplyPageDTO(); //페이징 댓글정보
		
		dto.setList2(cusReplyMapper.getCusReplyList(cri));
		dto.setPageInfo2(new PageDTO2(cri, cusReplyMapper.getCusReplyTotal(cri.getPostNo())));
		//페이징 총 개수
		return dto;
	}

	//댓글 삭제
	@Override
	public int deleteCusReply(CusReplyDTO dto) {
		
		int result = cusReplyMapper.deleteCusReply(dto.getCusReplyId());
		return result;
		
	}

	

}
	

