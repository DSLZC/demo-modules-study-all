<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	
	<h4>Login Page</h4>
	
	<form action="/user/login" method="POST" autocomplete="off">
		username: <input type="text" name="username" value="admin" autocomplete="off"/>
		<br><br>
		
		password: <input type="password" name="password" value="123456"/>
		<br><br>

		rememberMe: <input type="checkbox" name="rememberMe" value="1"/>
		<br><br>
		
		<input type="submit" value="Submit"/><b style="color: red">${error}</b>
	</form>
	<h2>登录用户：</h2>
	<p>超级管理员：<span>admin</span><span style="margin-left: 20px">123456</span></p>
	<p>管理员：<span>manager</span><span style="margin-left: 20px">123456</span></p>
	<p>普通用户：<span>user</span><span style="margin-left: 20px">123456</span></p>
	<p>访客：<span>visitor</span><span style="margin-left: 20px">123456</span></p>
</body>
</html>