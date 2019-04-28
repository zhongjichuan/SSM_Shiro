<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<table bgcolor="bule">
	<tr>
	<td><a href="${ctx}/siteMesh/index">index</a></td>
	</tr>
	<tr>
	<td><a href="${ctx}/siteMesh/index2">index2</a></td>
	</tr>
	<tr>
	<td><a href="${ctx}/siteMesh/index3">index3</a></td>
	</tr>
	<ul>
		<li><a href="${ctx}/siteMesh/index">index</a></li>
		<li><a href="${ctx}/siteMesh/index2">index2</a></li>
		<li><a href="${ctx}/siteMesh/index3">index3</a></li>
	</ul>
</table>
</body>
</html>