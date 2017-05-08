<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	
	<h2>List Page</h2>

	<h3>JSP标签：</h3>
	Welcome: <shiro:principal></shiro:principal>

	<shiro:hasRole name="admin">
	<br><br>
	<a href="/page/admin.jsp">Admin Page</a>
	</shiro:hasRole>
	
	<shiro:hasRole name="user">
	<br><br>
	<a href="/page/user.jsp">User Page</a>
	</shiro:hasRole>

	<shiro:hasRole name="visitor">
		<br><br>
		<a href="/page/visitor.jsp">visitor Page</a>
	</shiro:hasRole>
	
	<br><br>
	<h3>Java注解：</h3>
	<a href="/page/list">List Page</a>
	<br><br>
	<a href="/page/admin">Admin Page</a>
	<br><br>
	<a href="/page/user">User Page</a>
	<br><br>
	<a href="/page/visitor">visitor Page</a>

	<br><br>
	<br><br>
	<a href="/user/logout">Logout</a>
	
</body>
</html>