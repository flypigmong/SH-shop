<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 관리</title>
<link rel="stylesheet" href="../resources/css/admin/memberManage.css">
 
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
</head>
</head>
<body>
 
 		 <%@include file="../includes/admin/header.jsp" %>
  
                <div class="admin_content_wrap">
                    <div class="admin_content_subject"><span>멤버 관리</span></div>
 					<div class="member_table_wrap">
 					<!-- 게시물 O -->
					<c:if test="${listCheck != 'empty'}">				
                    	<table class="member_table">
                    		<thead>
                    			<tr>
                    				<td class="th_column_1">회원 아이디</td>
                    				<td class="th_column_2">회원 이름</td>
                    				<td class="th_column_3">회원 메일</td>
                    				<td class="th_column_4">회원 주소1</td>
                    				<td class="th_column_5">회원 주소2</td>
                    				<td class="th_column_6">회원 주소3</td>
                    				<td class="th_column_7">가입 날짜</td>
                      				<td class="th_column_8">돈</td>
                    				<td class="th_column_9">포인트</td>                  			
                    			</tr>
                    		</thead>
                    		<c:forEach items="${list}" var="list">
                    		<tr>
                    			<td><c:out value="${list.memberId}"></c:out> </td>
                    			<td><c:out value="${list.memberName}"></c:out></td>
                    			<td><c:out value="${list.memberMail}"></c:out></td>
                    			<td><c:out value="${list.memberAddr1}"></c:out></td>
                    			<td><c:out value="${list.memberAddr2}"></c:out></td>
                    			<td><c:out value="${list.memberAddr3}"></c:out></td>
                    			<td><fmt:formatDate value="${list.regDate}" pattern="yyyy-MM-dd"/></td>
                    			<td><c:out value="${list.money}"></c:out></td>
                    			<td><c:out value="${list.point}"></c:out></td>
                    		</tr>
                    		</c:forEach>
                    	</table>     
                    	</c:if>
 
 						<!--  게시물 x -->
                 		<c:if test="${listCheck == 'empty'}">
                			<div class="table_empty">
                				등록된 작가가 없습니다.
                			</div>
                		</c:if>                     	           			
                    </div>  
                    
                     <!-- 검색 영역 -->
                    <div class="search_wrap">
                    	<form id="searchForm" action="/admin/memberManage" method="get">
                    		<div class="search_input">
                    			<input type="text" name="keyword" value='<c:out value="${pageMaker.cri.keyword}"></c:out>'>
                    			<input type="hidden" name="pageNum" value='<c:out value="${pageMaker.cri.pageNum }"></c:out>'>
                    			<input type="hidden" name="amount" value='${pageMaker.cri.amount}'>
                    			<button class='btn search_btn'>검 색</button>
                    		</div>
                    	</form>
                    </div>                    
                    
                    
 					<!-- 페이지 이동 인터페이스 영역 -->
                    <div class="pageMaker_wrap" >
                    
	                    <ul class="pageMaker">
	                    
	                    	<!-- 이전 버튼 -->
	                    	<c:if test="${pageMaker.prev}">
	                    		<li class="pageMaker_btn prev">
	                    			<a href="${pageMaker.pageStart - 1}">이전</a>
	                    		</li>
	                    	</c:if>
	                    	
	                    	<!-- 페이지 번호 -->
	                    	<c:forEach begin="${pageMaker.pageStart}" end="${pageMaker.pageEnd}" var="num">
	                    		<li class="pageMaker_btn ${pageMaker.cri.pageNum == num ? "active":""}">
	                    			<a href="${num}">${num}</a>
	                    		</li>
	                    	</c:forEach>
	                    	
	                    	<!-- 다음 버튼 -->
	                    	<c:if test="${pageMaker.next}">
	                    		<li class="pageMaker_btn next">
	                    			<a href="${pageMaker.pageEnd + 1 }">다음</a>
	                    		</li>
	                    	</c:if>
	                    	
	                    </ul>
	                    
                    </div>
                              
					<form id="moveForm" action="/admin/memberManage" method="get">
						<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}">
						<input type="hidden" name="amount" value="${pageMaker.cri.amount}">
						<input type="hidden" name="keyword" value="${pageMaker.cri.keyword}">
					</form>                              
                                      
                </div>               
                  
  			<%@include file="../includes/admin/footer.jsp" %>
 
</body>
 
<script>

	    
	let moveForm = $('#moveForm');
	
	/* 페이지 이동 버튼 */
	$(".pageMaker_btn a").on("click", function(e){
	    
	    e.preventDefault();
	    
	    moveForm.find("input[name='pageNum']").val($(this).attr("href"));
	    
	    moveForm.submit();


	});



</script>
</html>
