package com.sh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sh.mapper.ReplyMapper;
import com.sh.model.Criteria;
import com.sh.model.PageDTO;
import com.sh.model.ReplyDTO;
import com.sh.model.ReplyPageDTO;
import com.sh.model.UpdateReplyDTO;

@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private ReplyMapper replyMapper;
	
	/* 댓글 등록 */
	@Override
	public int enrollReply(ReplyDTO dto) {
		
		int result = replyMapper.enrollReply(dto);
		
		setRating(dto.getBookId()); // 상품평점 평균값 구하기
		
		return result;
	}

	/* 댓글 체크*/
	@Override
	public String checkReply(ReplyDTO dto) {
		
		Integer result = replyMapper.checkReply(dto);
		
		if(result == null) {
			return "0";
		} else {
			return "1";
		}
		
	}
	
	/* 댓글 페이징 */
	@Override
	public ReplyPageDTO replyList(Criteria cri) {
		
		ReplyPageDTO dto = new ReplyPageDTO();
		
		dto.setList(replyMapper.getReplyList(cri));
		dto.setPageInfo(new PageDTO(cri, replyMapper.getReplyTotal(cri.getBookId())));
		
		return dto;
	}
	
	/* 댓글 수정 */
	@Override
	public int updateReply(ReplyDTO dto) {
		
		int result = replyMapper.updateReply(dto);
		
		setRating(dto.getBookId()); // 상품평점 평균값 구하기
		
		return result;
	}

	
	@Override
	public ReplyDTO getUpdateReply(int replyId) {
		
		return replyMapper.getUpdateReply(replyId);
	}

	/* 댓글 삭제 */
	@Override
	public int deleteReply(ReplyDTO dto) {
		
		int result = replyMapper.deleteReply(dto.getReplyId());
		
		setRating(dto.getBookId()); // 상품평점 평균값 구하기
		
		return result;
	}

	
	public void setRating(int bookId) {
		
		Double ratingAvg = replyMapper.getRatingAverage(bookId);
		
		if(ratingAvg == null) {
			ratingAvg = 0.0;
		}
		
		//평균값의 소수점 첫째 자리까지 표시
		ratingAvg = (double) (Math.round(ratingAvg*10));
		ratingAvg = ratingAvg / 10;
		
		UpdateReplyDTO urd = new UpdateReplyDTO();
		urd.setBookId(bookId);
		urd.setRatingAvg(ratingAvg);
		
		replyMapper.updateRating(urd);
		
	}
	
}
