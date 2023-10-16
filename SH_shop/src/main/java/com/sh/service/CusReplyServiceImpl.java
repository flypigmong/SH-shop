package com.sh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.CusReplyMapper;
import com.sh.model.Criteria;
import com.sh.model.CusReplyDTO;
import com.sh.model.CusReplyPageDTO;
import com.sh.model.PageDTO;
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

	@Override
	public CusReplyPageDTO cusReplyList(Criteria cri) {
		CusReplyPageDTO dto = new CusReplyPageDTO();
		
		dto.setList(cusReplyMapper.getCusReplyList(cri));
		dto.setPageInfo(new PageDTO(cri, cusReplyMapper.getCusReplyTotal(cri.getBookId())));
		
		return dto;
	}

	
	
	
}
	

