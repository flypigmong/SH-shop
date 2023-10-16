package com.sh.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CusReplyDTO {
	
	private int cusReplyId;
	
	private int postNo;
	
	private String memberId;
	
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
	private Date regDate;
	
	private String content;
	
	
}
