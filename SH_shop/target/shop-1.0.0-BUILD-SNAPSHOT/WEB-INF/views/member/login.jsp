<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 페이지</title>
<script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
<link rel="stylesheet" href="/resources/css/member/login.css">
</head>
<body>

<div class="wrapper">

	<div class="wrap">
		<form id="login_form" method="post">
		<div class="logo_wrap">
			<span>Book Mall</span>
		</div>
		<div class="login_wrap"> 
			<div class="id_wrap">
					<div class="id_input_box">
					<input class="id_input" name="memberId">
				</div>
			</div>
			<div class="pw_wrap">
				<div class="pw_input_box">
					<input class="pw_iput" name="memberPw">
				</div>
			</div>
			<div class="login_button_wrap">
				<input type="submit" class="login_button" value="로그인">
			</div>			
		</div>
	</form>
	</div>
</div>
 <!-- msg 값을 EL 표현식으로 받아서 alert 창으로 띄움-->
<script>
    <c:if test="${not empty msg}">
        alert("${msg}");
    </c:if>
</script>

</body>
</html>