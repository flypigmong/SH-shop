<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록</title>
</head>
<body>

<h1>게시판 등록</h1>
<form action="/member/PostEnroll" method="post">
    <div class="input_wrap">
        <label>PostTitle</label>
        <input name="title">
    </div>
    <div class="input_wrap">
        <label>PostContent</label>
        <textarea rows="3" name="content"></textarea>
    </div>
    <div class="input_wrap">
        <label>memberId</label>
        <input name="writer">
    </div>
    <button class="btn">등록</button>
</form>
</body>
</html>