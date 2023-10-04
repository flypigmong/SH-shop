package com.sh.model;

import java.util.Date;

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
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getPostNo() {
		return postNo;
	}

	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return "CustomerCenterDTO [memberId=" + memberId + ", postNo=" + postNo + ", postTitle=" + postTitle
				+ ", postContent=" + postContent + ", postDate=" + postDate + ", updateDate=" + updateDate + "]";
	}


	
	
	
	
}

