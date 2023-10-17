package com.sh.model;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerCenterDTO {

	/* 멤버 id */
	private String memberId;
	
	/* 게시글 번호 */
	private int postNo;
	
	/* 게시글 제목 */
	private String postTitle;
	
	/* 게시글 내용 */
	private String postContent;
	
	/* 게시글 날짜 */
	private Date postDate;

	/* 게시글 수정날짜 */
	private Date updateDate;
	
}

