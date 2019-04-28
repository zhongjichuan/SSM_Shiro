<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	欢迎，${sessionScope.user.name}
	<div>
		<table border="1" cellspacing="0" cellpadding="0">
			<tr>
				<td>name</td>
				<td>age</td>
				<td>mail</td>
				<td>password</td>
				<td>删除</td>
			</tr>
			<c:forEach items="${userList.list}" var="user">
				<tr>
					<td>${user.name }</td>
					<td>${user.age }</td>
					<td>${user.mail }</td>
					<td>${user.password }</td>
					<td>
					<c:if test="${sessionScope.user.name!= user.name }">
					<a href="deleteUser/${user.name }">删除</a>
					</c:if>
					</td>
				</tr>
			</c:forEach>
			<tr >
				<td colspan="5">
				当前页：${userList.pageNum }/ 总页数：${userList.pages }
				<a href="showUsers">首页</a>
				<c:if test="${not userList.isFirstPage }">
					<a href="showUsers?pageNum=${userList.pageNum -1}">上一页</a>
				</c:if>
				<c:if test="${not userList.isLastPage }">
					<a href="showUsers?pageNum=${userList.pageNum +1}">下一页</a>
				</c:if>
				<a href="showUsers?pageNum=${userList.pages }">尾页</a>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>