package com.sh.model;

import java.util.Date;

import lombok.Data;

@Data
public class AdminAnswerDTO {
	
	/* 멤버 id */
	private String adminId;
	
	/* 게시글 번호 */
	private int answerNo;
	
	/* 게시글 제목 */
	private String answerTitle;
	
	/* 게시글 내용 */
	private String answerContent;
	
	/* 게시글 날짜 */
	private Date answerDate;

	/* 게시글 수정날짜 */
	private Date answerUpdateDate;
}
