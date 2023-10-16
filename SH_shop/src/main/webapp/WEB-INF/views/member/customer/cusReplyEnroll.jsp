<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글등록</title>
<link rel="stylesheet" href="/resources/css/member/join.css">
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
<style type="text/css">
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

	</div>

		<div class="input_wrap">			
			<div class="bookName_div">
				<h2>${postInfo.postTitle}</h2>
			</div>
			<div class="content_div">
				<h4>댓글</h4>
				<textarea name="content"></textarea>
			</div>
		</div>

		<div class="btn_wrap">
			<a class="cancel_btn">취소</a><a class="enroll_btn">등록</a>
		</div>
		
		
<script>
	
	/* 취소 버튼 */
	$(".cancel_btn").on("click", function(e){
		window.close();
	});	
	
	/* 등록 버튼 */
	$(".enroll_btn").on("click", function(e){

		const postNo = '${postInfo.postNo}';
		const writeId = '${writeId}';
		const memberId = '${memberId}';
		//const rating = $("select").val();
		const content = $("textarea").val();

		const data = {
				postNo : postNo,
				memberId : memberId,
				//rating : rating,
				content : content
		}	
		
		$.ajax({
			data : data,
			type : 'POST',
			url : '/cusReply/enroll',
			success : function(result){
				
				/* 댓글 초기화 */
				$(opener.location).attr("href", "javascript:replyListInit();");
				
				//창 닫기
				window.close();
				
				//소켓
				//if(readWriter =! writer)
					if(socket){
						//websocket에 보내기(reply,댓작성자,글작성자,글번호)
						let socketMsg = "reply," + memberId + "," + writeId +"," +postNo;
						console.log("소켓메시지 : " + socketMsg);
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
	};

	ws.onclose= function (event) {
		console.log('Info: connection closed. ');
	};
	
	ws.onerror = function (err) {
		console.log('Error: ', err); 
	};


};



//소켓 끝
</script> 	
</body>
</html>