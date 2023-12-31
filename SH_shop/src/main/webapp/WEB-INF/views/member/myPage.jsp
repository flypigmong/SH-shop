<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이룸</title>
<link rel="stylesheet" href="/resources/css/member/myPage.css">
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
                    <li><a href="/member/customer/list">고객센터</a></li>
                    <li><a href="/member/pwUpdateForm">비밀번호변경</a></li>             
                </ul>
            </div>
            <!-- top_subject_area -->
            <div class="admin_top_wrap">
                <div class="content_subject"><span>마이룸</span></div>

    </div>     

<div class="content_area">
	<div class="member_table_wrap">
    <table class="member_table">
   	  <thead> 
        <tr>
            <td class="th_column_1">회원아이디</td>
            <td class="th_column_2">회원이름</td>
            <td class="th_column_3">회원메일</td>
            <td class="th_column_4">회원주소1</td>
            <td class="th_column_5">회원주소2</td>
            <td class="th_column_6">회원주소3</td>
            <td class="th_column_7">돈</td>
            <td class="th_column_8">포인트</td>
        </tr>
        </thead>
        <tr>
            <td>${memberInfo.memberId}</td>
            <td>${memberInfo.memberName}</td>
            <td>${memberInfo.memberMail}</td>
            <td>${memberInfo.memberAddr1}</td>
            <td>${memberInfo.memberAddr2}</td>
            <td>${memberInfo.memberAddr3}</td>
            <td>${memberInfo.money}</td>
            <td>${memberInfo.point}</td>
        </tr>
    </table>
</div>
</div>


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



	
	
</body>
</html>