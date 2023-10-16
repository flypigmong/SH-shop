<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고객센터</title>
<link rel="stylesheet" href="/resources/css/member/list.css">
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
</head>
<body>
	
    <div class="wrapper">
        <div class="wrap">
            <!-- gnv_area -->    
            <div id="socketAlert" class="top_gnb_area2" role="alert" style="display:none;">
     			</div>
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

    </div>     

<!--  dfdfdfd -->
<div class="content_area">
	<div class="member_table_wrap">
	<a href="/member/customer/postEnroll" class="top_btn">글 등록</a>
    <table class="member_table">
   	  <thead> 
        <tr>
            <td class="th_column_1">postNo</td>
            <td class="th_column_2">memberId</td>
            <td class="th_column_3">postTitle</td>
            <!-- <td class="th_column_4">postContent</td> -->
            <td class="th_column_4">postDate</td>
            <td class="th_column_5">updateDate</td>
        </tr>
        </thead>
        <c:forEach items="${list}" var="list">
        	<tr>
	            <td><c:out value="${list.postNo}"></c:out></td>
	            <td>${list.memberId}</td>
	            <td>
	            		<a class="move" href='<c:out value="${list.postNo}"/>'>
	            				<c:out value="${list.postTitle}"/>
	            		</a>
	            </td>
            	<!-- <td><c:out value="${list.postContent}"></c:out></td> -->
	            <td><fmt:formatDate value="${list.postDate}" pattern="yyyy-MM-dd"/></td>
	            <td><fmt:formatDate value="${list.updateDate}" pattern="yyyy-MM-dd"/></td>
        </tr>
        </c:forEach>
    </table>
    <form id="moveForm" method="get">
    </form>
</div> <!--  member_table_wrap -->
</div> <!--  "content_area" -->
<!--  dfdfdfd -->

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


	$(document).ready(function(){
		
		let result = '<c:out value="${result}"/>';
		
		checkAlert(result);
		
		function checkAlert(result){
			
			if(result === ''){
				return;
			}
			
			if(result === "enroll success"){
				alert("등록이 완료되었습니다.");
			}
			
	        if(result === "modify success"){
	            alert("수정이 완료되었습니다.");
	        }
	        
		}	
		
	}); //$(document.)


    let moveForm = $("#moveForm");
 
    $(".move").on("click", function(e){
        e.preventDefault();
        
        moveForm.empty();
        moveForm.append("<input type='hidden' name='postNo' value='"+ $(this).attr("href")+ "'>");
        moveForm.attr("action", "/member/customer/get");
        moveForm.submit();
    });
    
    
</script>

<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>

<script type="text/javascript">

	var socket = null;
	$(document).ready(function(){
		connetWs();
	})


function connetWs(){
	console.log("ttttt")
	var ws = new SockJS("/replyEcho");
	socket = ws;
	//var ws = new WebSocket("ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/replyEcho");

	ws.onopen = function() {
		console.log('Info: connection opened. ');
	};

	ws.onmessage = function (event) {
		console.log("ReceiveMessage:", event.data+ '\n');
            
           let $socketAlert = $('div#socketAlert');
           $socketAlert.text(event.data);
           $socketAlert.css('display', 'block');
           
	   		setTimeout(function(){
				$socketAlert.css('display','none');
				
			}, 5000);

	};
	

	ws.onclose= function (event) {
		console.log('Info: connection closed. ');
	};
	
	ws.onerror = function (err) {
		console.log('Error: ', err); 
	};

/* 	$(".enroll_btn").on("click", function(evt){
		evt.preventDefault();
		if (socket.readyState !== 1) 
			return;
		let msg = $('input#msg').val();
		ws.send(msg);
	}); 
	*/

};



//소켓 끝
</script> 		
</body>

</html>