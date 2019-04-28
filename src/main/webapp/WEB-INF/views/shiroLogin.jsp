<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>shrio的登录页面</h1>
<form action="${pageContext.request.contextPath }/shiroController/validateLogin">
		<span style="color: red">${errorMessage}</span>
		<div>
			用户名：<input type="text" id="username" name="name" value="<shiro:principal property="name"/>"><br>
			密码: <input type="text" id="password" name="password"><br>
			<input type="checkbox" name="rememberMe" value="true"><label>记住我</label>
		</div>
		<input type="submit" value="登录">
	</form>
</body>
</html>