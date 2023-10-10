<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome BookMall</title>
<link rel="stylesheet" href="resources/css/main.css">
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
<link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
</head>
<body>

<div class="wrapper">
	<div class="wrap">
		<div class="top_gnb_area">
            <ul class="list">
            		<c:if test = "${member == null}">
                <li >
                    <a href="/member/login">로그인</a>
                </li>
                <li>
                    <a href="/member/join">회원가입</a>
                </li>
                </c:if>
                 <c:if test="${member != null }">   
                     <c:if test="${member.adminCk == 1 }">
                        <li><a href="/admin/main">관리자 페이지</a></li>
                    </c:if>                                 
                    <li>
                        <a id="gnb_logout_button">로그아웃</a>
                    </li>
                    <li>
                       <a href="/member/myPage/${member.memberId}">마이룸</a>
                    </li>
                    <li>
                        <a href="/cart/${member.memberId}">장바구니</a>
                    </li>
                    <li>
  						    <div id="msgStack"></div>
               		</li>
                </c:if>                   
                <li>
                    <a href="/member/customer/list">고객센터</a>
                </li>            
            </ul>   			
		</div>
		<div class="top_area">
			<div class="logo_area">
				<a href="/main"><img src="resources/img/mLogo.png"></a>
			</div>
			<div class="search_area">
               		<form id="searchForm" action="/search" method="get">
               			<div class="search_input">
               				<select name="type">
               					<option value="T">책 제목</option>
               					<option value="A">작가</option>
               				</select>	
               				<input type="text" name="keyword">
                   			<button class='btn search_btn'>검 색</button>                				
               			</div>
               		</form>				
			</div>
			<div class="login_area">
			
				<!-- 로그인 하지 않은 상태 -->
				<c:if test = "${member == null }">
					<div class="login_button"><a href="/member/login">로그인</a></div>
					<span><a href="/member/join">회원가입</a></span>
				</c:if>
				
                <!-- 로그인한 상태 -->
                <c:if test="${ member != null }">
                     <div class="login_success_area">
                        <span>회원 : ${member.memberName}</span>
                        <span>충전금액 : <fmt:formatNumber value="${member.money}" pattern="\#,###.##"/></span>
                        <span>포인트 : <fmt:formatNumber value="${member.point}" pattern="#,###" /></span>
                	    <a href="/member/logout.do">로그아웃</a>
                    </div>           		
                </c:if>				
				
			</div>
			<div class="clearfix"></div>			
		</div>
			<div class="navi_bar_area">
			<div class="dropdown">
			    <button class="dropbtn">국내 
			      <i class="fa fa-caret-down"></i>
			    </button>
			    <div class="dropdown-content">
					<c:forEach items="${cate1}" var="cate">
						 <a href="search?type=C&cateCode=${cate.cateCode}">${cate.cateName}</a>
					</c:forEach> 		      		      
			    </div>			
			</div>
			<div class="dropdown">
			    <button class="dropbtn">국외 
			      <i class="fa fa-caret-down"></i>
			    </button>
			    <div class="dropdown-content">
			    	<c:forEach items="${cate2}" var="cate">
						 <a href="search?type=C&cateCode=${cate.cateCode}">${cate.cateName}</a>
					</c:forEach> 	 		      		      
			    </div>			
			</div>
		</div>
		<div class="content_area">
			
			<div class="slide_div_wrap">
				<div class="slide_div">
					<div>
						<a>
							<img src="../resources/img/115a8ff198e34e4fb24643289ae37cc8.jpg">
						</a>
					</div>
					<div>
						<a>
							<img src="../resources/img/7f09544e0c0840f79c3e4edda64e52f8.jpg">
						</a>
					</div>
					<div>
						<a>
							<img src="../resources/img/cbe0ed1ed35e4208948683dc60139a6a.jpg">
						</a>
					</div>				
				</div>	
			</div>		
			
			
			<div class="ls_wrap">
				<div class="ls_div_subject">
					평점순 상품
				</div>
				<div class="ls_div">
					<c:forEach items="${ls}" var="ls">
						<a href="/goodsDetail/${ls.bookId}">
							<div class="ls_div_content_wrap">
								<div class="ls_div_content">
									<div class="image_wrap" data-bookid="${ls.imageList[0].bookId}" data-path="${ls.imageList[0].uploadPath}" data-uuid="${ls.imageList[0].uuid}" data-filename="${ls.imageList[0].fileName}">
										<img>
									</div>				
									<div class="ls_category">
										${ls.cateName}
									</div>
									<div class="ls_rating">
										<img src="./resources/img/star.jpg" style="width:13px; height:13px;  display: inline-block; vertical-align:middle;"/>
											${ls.ratingAvg}
									</div>
									<div class="ls_bookName">
										${ls.bookName}
									</div>							
								</div>
							</div>
						</a>					
					</c:forEach>					
				</div>
			</div>
			
			
		</div>
		
		
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
                    <img src="resources/img/Logo.png">
                </div>
                <div class="footer_right">
                    (주) SHBook    대표이사 : OOO
                    <br>
                    사업자등록번호 : ooo-oo-ooooo
                    <br>
                    대표전화 : oooo-oooo(발신자 부담전화)
                    <br>
                    <br>
                    COPYRIGHT(C) <strong>ABC@MAIL.com</strong>    ALL RIGHTS RESERVED.
                </div>
                <div class="clearfix"></div>
            </div>
        </div> <!-- class="footer" -->    		
		
	</div>		<!-- class="wrap" -->
</div>		<!-- class="wrapper" -->
	<script>
	
	    /* gnb_area 로그아웃 버튼 작동 */
	    $("#gnb_logout_button").click(function(){
	        //alert("버튼 작동");
        $.ajax({
            type:"POST",
            url:"/member/logout.do",
            success:function(data){
                // alert("로그아웃 성공");
                document.location.reload();     
            } 
        }); // ajax 	        
	 });	
	    
	    
	    
	    $(document).ready(function(){
	    	
	    	$(".slide_div").slick(
			    	{
			    		dots: true,   //점 기능 사용 
			    		autoplay : true, // 자동 넘김 기능 사용
			    		autoplaySpeed: 5000 // 자동 넘김 속도(1밀리초=0.001초)
			    	}
			 );
	    	
	    	
			$(".ls_div").slick({
				
				slidesToShow: 4,  // 몇 개의 슬라이드를 한 화면에 보일지
				slidesToScroll: 4,  // 다음 슬라이드 이동 시 몇 개씩 이동할 것인지
				prevArrow : "<button type='button' class='ls_div_content_prev'>이전</button>",		// 이전 화살표 모양 설정
				nextArrow : "<button type='button' class='ls_div_content_next'>다음</button>",		// 다음 화살표 모양 설정				
			});
	    	
		});
		    
	    
		/* 이미지 삽입 */
		$(".image_wrap").each(function(i, obj){
			
			const bobj = $(obj);
			
			if(bobj.data("bookid")){
				const uploadPath = bobj.data("path");
				const uuid = bobj.data("uuid");
				const fileName = bobj.data("filename");
				
				const fileCallPath = encodeURIComponent(uploadPath + "/s_" + uuid + "_" + fileName);
				
				$(this).find("img").attr('src', '/display?fileName=' + fileCallPath);
			} else {
				$(this).find("img").attr('src', '/resources/img/goodsNoImage.png');
			}
			
		});
	    
	    
		
		// 웹소켓 연결
		var socket  = null;

		// comma 
		function pointToNumFormat(num) {
	    	return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		}

		function onProfile(){
			var memberLayer = document.getElementById("memberLayer");
			memberLayer.classList.add("visible");
		}
		function outProfile(){
			var memberLayer = document.getElementById("memberLayer");
			memberLayer.classList.remove("visible");
		}
		
		// 프로필 클릭 
		function openMemberPopup(){
				var popupOpener;
				let m_id = '${m_id}';
				popupOpener = window.open("${contextPath}/member/searchMemberInfoPopup.do?m_id="+m_id, "popupOpener", "resizable=no,top=0,left=0,width=450,height=500");
		}
		
		$(document).ready(function(){
			 sock = new SockJS("<c:url value="/echo-ws"/>");
			 socket = sock;

			// 데이터를 전달 받았을때 
			sock.onmessage = onMessage;
			
			
			
			// 실시간 알림 받았을 시
			function onMessage(evt){
				var data = evt.data;
				// toast
				let toast = "<div class='toast' role='alert' aria-live='assertive' aria-atomic='true'>";
				toast += "<div class='toast-header'><i class='fas fa-bell mr-2'></i><strong class='mr-auto'>알림</strong>";
				toast += "<small class='text-muted'></small><button type='button' class='ml-2 mb-1 close' data-dismiss='toast' aria-label='Close'>";
				toast += "<span aria-hidden='true'>&times;</span></button>";
				toast += "</div> <div class='toast-body'>" + data + "</div></div>";
				$("#msgStack").append(toast);
				$(".toast").toast({"animation": true, "autohide": false});
//		 		$(".toast").toast({"animation": true, "autohide": true, "delay": 5000});
				$('.toast').toast('show');
				// 알림 카운트 추가
				$("#newNoticeCnt").text($("#newNoticeCnt").text()*1+1);
			};				
</script>
</body>
</html>