<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 조회</title>
<link rel="stylesheet" href="/resources/css/member/get.css">
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous">
  </script>
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
                    <li><a href="/member/customer/list">고객센터</a></li>
                    <li><a href="/member/pwUpdateForm">비밀번호변경</a></li>             
                </ul>
            </div>
            <!-- top_subject_area -->
            <div class="admin_top_wrap">
                <div class="content_subject"><span>고객센터</span></div>
					
    </div>

	<div class="content_area">
		<div class="member_table_wrap">
			<div class="input_wrap">
				<label>게시판 번호</label>
				<input name="postNo" readonly="readonly" value='<c:out value="${goodsInfo.postNo}"/>' >
			</div>
			<div class="input_wrap">
				<label>게시판 제목</label>
				<input name="title" readonly="readonly" value='<c:out value="${goodsInfo.postTitle}"/>' >
			</div>
			<div class="input_wrap">
				<label>게시판 내용</label>
				<textarea rows="3" name="content" readonly="readonly"><c:out value="${goodsInfo.postContent}"/></textarea>
			</div>
			<div class="input_wrap">
				<label>게시판 작성자</label>
				<input name="writer" readonly="readonly" value='<c:out value="${goodsInfo.memberId}"/>' >
			</div>
			<div class="input_wrap">
				<label>게시판 등록일</label>
				<input name="regdater" readonly="readonly" value='<fmt:formatDate pattern="yyyy/MM/dd" value="${goodsInfo.postDate}"/>' >
			</div>
			<div class="input_wrap">
				<label>게시판 수정일</label>
				<input name="updateDate" readonly="readonly" value='<fmt:formatDate pattern="yyyy/MM/dd" value="${goodsInfo.updateDate}"/>' >
			</div>		
			<div class="btn_wrap">
				<a class="btn" id="list_btn">목록 페이지</a> 
				<c:if test="${memberId == writeId}">
					<a class="btn2" id="modify_btn">수정 하기</a>
				</c:if>
			</div>
			<form id="infoForm" action="/member/modify" method="get">
				<input type="hidden" id="postNo" name="postNo" value='<c:out value="${goodsInfo.postNo}"/>'>
			    <input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum}"/>'>
        		<input type="hidden" name="amount" value='<c:out value="${cri.amount}"/>'>  	
			</form>
	
			<div class="line">
				</div>
					<div class="content_bottom">
						<div class="reply_subject">
							<h2>댓글</h2>
					</div>
					
					<c:if test="${memberId != null}">
						<div class="reply_button_wrap">
							<button>댓글 쓰기</button>
						</div>
					</c:if>
					
				<div class="reply_not_div">
					
				</div>
				<ul class="reply_content_ul">
				
				</ul>
				<div class="repy_pageInfo_div">
				 
					<ul class="pageMaker">
		
					</ul>

				</div>
					
			</div> <!--  content_bottom -->
	
	
		</div><!--  member_table_wrap -->
	</div> <!--  "content_area" -->
	
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

	/* 댓글 리스트 출력*/
	$(document).ready(function(){
		
		// 리뷰 리스트 출력
		const postNo = '${goodsInfo.postNo}';	
	
		$.getJSON("/cusReply/list", {postNo : postNo}, function(obj){
		
			makeReplyContent(obj);
		
		});
	
	});

	/* 리뷰쓰기 */
	$(".reply_button_wrap").on("click", function(e){
		
		e.preventDefault();		
		
		const memberId = '${member.memberId}';
		const postNo = '${goodsInfo.postNo}';
		const writeId = '${writeId}';
	
		$.ajax({
			data : {
				postNo : postNo,
				memberId : memberId,
				writeId : writeId
			},
			url : '/cusReply/check',
			type : 'POST',
			success : function(result){

				if(result === '1') {
					alert("이미 등록된 리뷰가 존재합니다.")
				} else if(result === '0'){
					let popUrl = "/member/customer/cusReplyEnroll/" + memberId + "?postNo=" + postNo;
					console.log(popUrl);
					let popOption = "width = 490px, height=490px, top=300px, left=300px, scrollbars=yes";
	
					window.open(popUrl,"리뷰 쓰기",popOption);
				}
			}
		});
		
	});

	


	let form = $("#infoForm");
	
	$("#list_btn").on("click", function(e){
		form.find("#postNo").remove();
		form.attr("action", "/member/customer/list");
		form.submit();
	});
	
	$("#modify_btn").on("click", function(e){
			form.attr("action", "/member/customer/modify");
			form.submit();
	});	
	
	
	/* 댓글 데이터 서버 요청 및 댓글 동적 생성 메서드 */
	let replyListInit = function(){
		
		$.getJSON("/cusReply/list", cri2 , function(obj){
			
				makeReplyContent(obj);
				
				});
		}
		
	
	/* 댓글(리뷰) 동적 생성 메서드 */
	function makeReplyContent(obj){
		
		if(obj.list2.length === 0){
			$(".reply_not_div").html('<span>리뷰가 없습니다.</span>');
			$(".reply_content_ul").html('');
			$(".pageMaker").html('');
		} else {
			
			$(".reply_not_div").html('');
			
			const list = obj.list2;
			const pf = obj.pageInfo2;
			const userId = '${member.memberId}';	
		
			/* list */
			
			let reply_list = '';
			
			$(list).each(function(i,obj){
					console.log(obj);
					reply_list += '<li>';
					reply_list += '<div class="comment_wrap">';
					reply_list += '<div class="reply_top">';
					/* 아이디 */
					reply_list += '<span class="id_span">'+ obj.memberId+'</span>';
					/* 날짜 */
					reply_list += '<span class="date_span">'+ obj.regDate +'</span>';
					/* 평점 */
					//reply_list += '<span class="rating_span">평점 : <span class="rating_value_span">'+ obj.rating +'</span>점</span>';
					
					if(obj.memberId === userId){
						reply_list += '<a class="update_reply_btn" href="'+ obj.cusReplyId +'">수정</a><a class="delete_reply_btn" href="'+ obj.cusReplyId +'">삭제</a>';
					}
					reply_list += '</div>'; //<div class="reply_top">
					reply_list += '<div class="reply_bottom">';
					reply_list += '<div class="reply_bottom_txt">'+ obj.content +'</div>';
					reply_list += '</div>';//<div class="reply_bottom">
					reply_list += '</div>';//<div class="comment_wrap">
					reply_list += '</li>';
			});
			
			$(".reply_content_ul").html(reply_list);	
			
			/* 페이지 버튼 */
			
			let reply_pageMaker = '';
				/* prev */
				if(pf.prev){
					let prev_num = pf.pageStart -1;
					reply_pageMaker += '<li class="pageMaker_btn prev">';
					reply_pageMaker += '<a href="'+ prev_num +'">이전</a>';
					reply_pageMaker += '</li>';	
				}
				/* numbre btn */
				for(let i = pf.pageStart; i < pf.pageEnd+1; i++){
					reply_pageMaker += '<li class="pageMaker_btn ';
					if(pf.cri.pageNum === i){
						reply_pageMaker += 'active';
					}
					reply_pageMaker += '">';
					reply_pageMaker += '<a href="'+i+'">'+i+'</a>';
					reply_pageMaker += '</li>';
				}
				/* next */
				if(pf.next){
					let next_num = pf.pageEnd +1;
					reply_pageMaker += '<li class="pageMaker_btn next">';
					reply_pageMaker += '<a href="'+ next_num +'">다음</a>';
					reply_pageMaker += '</li>';	
				}		
				
				$(".pageMaker").html(reply_pageMaker);	
				
		}
	}
	
	
		/* 댓글 페이지 정보 */
		 const cri2 = {
			postNo : '${goodsInfo.postNo}',
			pageNum : 1,
			amount : 10
		}
	
		/* 댓글 페이지 이동 버튼 동작 */
		$(document).on('click', '.pageMaker_btn a', function(e){
			
			e.preventDefault();
			
			let page = $(this).attr("href");	
			cri2.pageNum = page;		
			
			replyListInit();
				
		 });
		
		
			/* 리뷰 삭제 버튼 */
			$(document).on('click', '.delete_reply_btn', function(e){
				
				e.preventDefault();
				let cusReplyId = $(this).attr("href");
				
				$.ajax({
					data : {
						cusReplyId : cusReplyId,
						postNo : '${goodsInfo.postNo}'
					},
					url : '/cusReply/delete',
					type : 'POST',
					success : function(result){
						replyListInit();
						alert('삭제가 완료되었습니다.');
					}
				});	
				
				
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
           $socketAlert.html(event.data);
           $socketAlert.css('display', 'block');
           
	   		setTimeout(function(){
				$socketAlert.css('display','none');
				
			}, 5000);

	};	
		
		

	ws.onclose= function (event) {
		console.log('Info: connection closed. ');
		//setTimeout(connetWs, 300); //웹소켓 재연결
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