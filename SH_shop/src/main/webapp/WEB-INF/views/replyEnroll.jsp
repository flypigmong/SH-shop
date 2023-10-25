<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글&평점</title>
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
   <style type="text/css"> /* 리뷰등록 팝업 css */
  	/* 창 여분 없애기 */
  	body{
  		margin : 0;
  	}
  	/* 전체 배경화면 색상 */
  	.wrapper_div{
		background-color: #f5f5f5;
	    height: 100%;  	
  	}
 	/* 팝업창 제목 */
  	.subject_div{
	    width: 100%;
	    background-color: #7b8ed1;
	    color: white;
	    padding: 10px;
	    font-weight: bold;
  	}
  	
  	/* 컨텐츠, 버튼 영역 padding */
  	.input_wrap{
  		padding: 30px;
  	}
  	.btn_wrap{
  		padding: 5px 30px 30px 30px;
  		text-align: center;
  	}
  	
  	/* 버튼 영역 */
  	.cancel_btn{
  		margin-right:5px;
  	    display: inline-block;
	    width: 130px;
	    background-color: #5e6b9f;
	    padding-top: 10px;
	    height: 27px;
	    color: #fff;
	    font-size: 14px;
	    line-height: 18px;  	
  	}
  	.enroll_btn{
   	    display: inline-block;
	    width: 130px;
	    background-color: #7b8ed1;
	    padding-top: 10px;
	    height: 27px;
	    color: #fff;
	    font-size: 14px;
	    line-height: 18px;   	
  	}

	/* 책제목 영역 */
	.bookName_div h2{
		margin : 0;
	}
  	/* 평점 영역 */
  	.rating_div{
  		padding-top: 10px;
  	}
  	.rating_div h4{
  		margin : 0;
  	}
  	select{
  	margin: 15px;
    width: 100px;
    height: 40px;
    text-align: center;
    font-size: 16px;
    font-weight: 600;  	
  	}
  	/* 리뷰 작성 영역 */
  	.content_div{
  		padding-top: 10px;
  	}
  	.content_div h4{
  		margin : 0;
  	}
  	textarea{
		width: 100%;
	    height: 100px;
	    border: 1px solid #dadada;
	    padding: 12px 8px 12px 8px;
	    font-size: 15px;
	    color: #a9a9a9;
	    resize: none;
	    margin-top: 10px;  	
  	}
  
  </style>
</head>
<body>

	<div class="wrapper_div">
		<div class="subject_div">
			리뷰 등록
	</div>

		<div class="input_wrap">			
			<div class="bookName_div">
				<h2>${bookInfo.bookName}</h2>
			</div>
			<div class="rating_div">
				<h4>평점</h4>
				<select name="rating">
					<option value="0.5">0.5</option>
					<option value="1.0">1.0</option>
					<option value="1.5">1.5</option>
					<option value="2.0">2.0</option>
					<option value="2.5">2.5</option>
					<option value="3.0">3.0</option>
					<option value="3.5">3.5</option>
					<option value="4.0">4.0</option>
				</select>
			</div>
			<div class="content_div">
				<h4>리뷰</h4>
				<textarea name="content"></textarea>
			</div>
		</div>
		
		<div class="btn_wrap">
			<a class="cancel_btn">취소</a>
			<a class="enroll_btn">등록</a>
		</div>
		
		                	
           	<form id="moveForm" action="/admin/goodsManage" method="get" >
				<input type="hidden" name="pageNum" value="${cri.pageNum}">
				<input type="hidden" name="amount" value="${cri.amount}">
				<input type="hidden" name="keyword" value="${cri.keyword}">
           	</form>
	</div>
	

<script>

	/* 취소 버튼 */
	$(".cancel_btn").on("click", function(e){
		window.close();
	});	

	
	/* 등록 버튼 */
	$(".enroll_btn").on("click", function(e){
		
		//console.debug("reply.socket", socket)
		
		const bookId = '${bookInfo.bookId}';
		const memberId = '${memberId}';
		const rating = $("select").val();
		const content = $("textarea").val();

		const data = {
				bookId : bookId,
				memberId : memberId,
				rating : rating,
				content : content
		}		
		
		$.ajax({
			data : data,
			type : 'POST',
			url : '/reply/enroll',
			success : function(result){

				/* 댓글 초기화 */
				$(opener.location).attr("href", "javascript:replyListInit();");
				
				//location.reload();
				window.close();
						
				//소켓
				//console.log(content);
				console.debug("reply.js::::socket", content)
				
				if(socket){ //websocket에 연결되었을때만
					let socketMsg = "reply," + memberId + "," +  bookId + "," + content;
					socket.send(socketMsg);
				}
				
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
        // setTimeout을 주어 정해진 시간만 화면에 출력 
        setTimeout(function(){ 
           websocketQna.style.display = "none"; 
        }, 9000); //3000 : 3초 
     }	
	
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