<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록</title>
<link rel="stylesheet" href="/resources/css/member/postEnroll.css">
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
</head>
<body>

<div class="admin_content_wrap">
    <div class="wrapper">
        <div class="wrap">
            <!-- gnv_area -->    
            <div class="top_gnb_area">
                <ul class="list">    
                    <li><a href="/main">메인 페이지</a></li>
                    <li><a href="/member/logout.do">로그아웃</a></li>
                    <li><a href="/member/customer">고객센터</a></li>
                    <li><a href="/member/pwUpdateForm">비밀번호변경</a></li>             
                </ul>
            </div>
            <!-- top_subject_area -->
            <div class="admin_top_wrap">
                <div class="content_subject"><span>고객센터</span></div>
					<div class="admin_content_subject"><span>글 등록</span></div>
    </div>




			<form action="/member/customer/postEnroll" method="post" id="enrollForm">
			    <div class="input_wrap">
			        <label>제목</label>
			        <input name="postTitle">
			    </div>
			    <div class="input_wrap">
			        <label>내용</label>
			        <textarea rows="3" name="postContent"></textarea>
			    </div>
			    <div class="input_wrap">
			        <label>작성자</label>
			        <input name="memberId" readonly="readonly" value="${member.memberId}">
			    </div>
			</form>
			 	<div class="btn_wrap">
		    		<button id="enrollBtn" class="btn">등록</button>
		    		<button id="listBtn" class="btn2">목록 페이지</button>
				</div>


			<form id="moveForm" method="get">
				
				<input type="hidden" name="memberId" value='<c:out value="${member.memberId}" />'>
			
			</form>


              <div class="clearfix"></div>
            
        
        <!-- Footer 영역 -->
        <div class="footer_nav">
            <div class="footer_nav_container">
                <ul>
                    <li>회사소개</li>
                    <span class="line">|</span>
                    <li>이용약관</li>
                    <span class="line">|</span>
                    <li>고객센터</li>
                    <span class="line">|</span>
                    <li>광고문의</li>
                    <span class="line">|</span>
                    <li>채용정보</li>
                    <span class="line">|</span>
                </ul>
            </div>
        </div> <!-- class="footer_nav" -->
        
        <div class="footer">
            <div class="footer_container">
                
                <div class="footer_left">
                    <img src="/resources/img/Logo.png">
                </div>
                <div class="footer_right">
                    (주) SHBook    대표이사 : OOO
                    <br>
                    사업자등록번호 : ooo-oo-ooooo
                    <br>
                    대표전화 : oooo-oooo(발신자 부담전화)
                    <br>
                    <br>
                    COPYRIGHT(C) <strong>SHbook.com</strong>    ALL RIGHTS RESERVED.
                </div>
                <div class="clearfix"></div>
            </div>
        </div> <!-- class="footer" -->        
        
    </div>    <!-- class="wrap" -->
</div>    <!-- class="wrapper" -->
</div> <!--  admin_content_wrap -->

<script>
  
	<c:if test="${not empty msg}">
		alert("${msg}");
	</c:if>
  
  
  	let enrollForm= $("#enrollForm");
  
   // 등록 버튼 
	   $("#enrollBtn").click(function(){
		   
	   			enrollForm.submit();
	  			
	   });
  
   /*
		$("#enrollBtn").click(function(){
		    
		    let result = '<c:out value="${result}"/>';
		    
		    checkAlert(result);
		    
		    function checkAlert(result){
		        
		        if(result == "enroll success"){ // 
		            alert("등록이 완료되었습니다.");
		        }
		        
		    }    
		    
		});
 	*/
 	
	/* 목록 버튼 */
	$("#listBtn").click(function(){
	    location.href="/member/customer/list"
	});
  
   /*
   //목록 버튼 
	let moveForm = $("#moveForm");
	
	$(".list_btn").on("click",function(){ 
		
		e.preventDefault();
		
		$("input[name=memberId]").remove();
		moveForm.attr("action", "/member/customer/list")
		moveForm.submit();
		
	});
	*/
</script>
</body>
</html>