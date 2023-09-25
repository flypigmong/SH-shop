<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경 페이지</title>
<link rel="stylesheet" href="/resources/css/member/pwUpdateForm.css">
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
</head>
<body>

    <div class="wrapper">
        <div class="wrap">
            <!-- gnv_area -->    
            <div class="top_gnb_area">
                <ul class="list">    
                    <li><a href="/main">메인 페이지</a></li>
                    <li><a href="/member/logout.do">로그아웃</a></li>
                    <li>고객센터</li>            
                </ul>
            </div>
            <!-- top_subject_area -->
            <div class="admin_top_wrap">
                <div class="content_subject">
                	<span>비밀번호 변경</span>
                </div>
    		</div>   
    
	<form id="update_form" method="post" action="/member/pwUpdate">
	   
	    <div class="content_area">
			
			<div class="id_wrap">
				<div class="id_name">아이디:${member.memberId}</div>
			</div>
			<div class="pw_wrap">
				<div class="pw_name">기존 비밀번호</div>
		    	 <div class="pw_input_box">
		    		<input class="pw_input" name="currentPw">  
				</div>
				<span class="original_pw_ck">기존 비밀번호를 입력해주세요.</span>
			</div>
			
			<div class="pwck_wrap">
		    	<div class="pwck_name">새 비밀번호</div>
		    	<div class="pwck_input_box">
		    		<input class="newPw_input" name="newPw">
				</div>
				<span class="final_pw_ck">새 비밀번호를 입력해주세요</span>
			</div>
			
			<div class="pwck_wrap2">
				<div class="pwck_name2">새 비밀번호 확인</div>
				<div class="pwck_input_box2">
					<input class="newPw_input2">
				</div>
				<span class="final_pwck_ck">비밀번호 확인을 입력해주세요.</span>
				<span class="pwck_input_re_1">비밀번호가 일치합니다.</span>
                <span class="pwck_input_re_2">새 비밀번호가 일치하지 않습니다.</span>
			</div>
			
			
	    	<div class="update_button_wrap">
	    		<input type="submit" class="update_button" value="수정하기">
	    	</div>
	    	
	    </div>
	    
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
    
    
<script>
	
<c:if test="${not empty msg1}">
	alert("${msg1}");
</c:if>

/*
<c:choose> 
	<c:when test="${sessionScope.msg1 eq '비밀번호 변경성공!'}">
		alert("${sessionScope.msg1}");
		response.sendRedirect("/main"); 
	</c:when>
	<c:when test="${sessionScope.msg1 eq '기존 비밀번호가 틀립니다.'}">
		alert("${sessionScope.msg1}");  
	</c:when>
</c:choose>
*/

/*
<c:if test="${not empty sessionScope.msg1}"> 
	alert("${sessionScope.msg1}"); // EL 표현식 사용
	session.removeAttribute("msg1"); // 세션에서 msg1 삭제 
		<c:if test="${sessionScope.msg1 eq '비밀번호 변경성공!'}">
			response.sendRedirect("/main"); // main으로 리다이렉트
		</c:if> 
</c:if>
*/

/*
<c:if test="${not empty msg1}">
	alert("${msg1}");
	//<c:remove var="msg1" scope="session"/> // 세션에서 msg 메시지 삭제
</c:if>
*/
	
	

    //<c:if test="${not empty msg1}">
        //alert("${msg1}");
        //session.setAttribute("msg1" , null);
       // <c:remove var="msg1" scope="session"/> // 세션에서 msg 메시지 삭제
   // </c:if>
    
 

	/* 유효성 검사 통과유무 변수 */

	var pwCheck = false;            // 비번
	var pwckCheck = false;            // 비번 확인
	var pwckcorCheck = false;        // 비번 확인 일치 확인
	//var pwReg = /^[A-Za-z0-9]{8,}$/; // 8자리 이상의 영문자와 숫자로만 구성된 비밀번호
	//if(!pwReg.test(new_pw)){
    //비밀번호 형식이 잘못되었을 때의 처리
	//}
	
$(document).ready(function(){
	//비밀번호 변경 버튼(비밀번호 변경 기능 작동)
	$(".update_button").click(function(){
    
	        /* 입력값 변수 */
	        var ori_pw = $('.pw_input').val(); // 기존 비밀번호 입력란
	        var new_pw = $('.newPw_input').val();  // 비밀번호 입력란
	        var new_pwck = $('.newPw_input2').val(); // 새 비밀번호 확인 입력란
	
	        
	        /* 기존 비밀번호 유효성 검사 */
	        if(ori_pw == ""){
	            $('.original_pw_ck').css('display','block');
	            OriPwCheck = false;
	        }else{
	            $('.original_pw_ck').css('display', 'none');
	            OriPwCheck = true;
	        }
	        
	        /* 새 비밀번호 유효성 검사 */
	        if(new_pw == ""){
	            $('.final_pw_ck').css('display','block');
	            pwCheck = false;
	        }else{
	            $('.final_pw_ck').css('display', 'none');
	            pwCheck = true;
	        }	     
	        
	        /* 새 비밀번호 확인 유효성 검사 */
	        if(new_pwck == ""){
	            $('.final_pwck_ck').css('display','block');
	            pwckCheck = false;
	        }else{
	            $('.final_pwck_ck').css('display', 'none');
	            pwckCheck = true;
	        }	   
	        
       
	        /* 최종 유효성 검사 */
	        if(OriPwCheck&&pwCheck&&pwckCheck&&pwckcorCheck){
	        	
	        	//$("#update_form").attr("action", "/member/pwUpdate");
		        $("#update_form").submit();
	 			
	        }  
	     		return false;

	   }); 
});
    
    
    
    /* 비밀번호 확인 일치 유효성 검사 */
    
    $('.newPw_input2').on("propertychange change keyup paste input", function(){
     
        var pw = $('.newPw_input').val();
        var pwck = $('.newPw_input2').val();
        $('.final_pwck_ck').css('display', 'none');
     
        if(pw == pwck){
            $('.pwck_input_re_1').css('display','block');
            $('.pwck_input_re_2').css('display','none');
            pwckcorCheck = true;
        }else{
            $('.pwck_input_re_1').css('display','none');
            $('.pwck_input_re_2').css('display','block');
            pwckcorCheck = false;
        }  
        
    });    
    
    
</script>
</body>
</html>