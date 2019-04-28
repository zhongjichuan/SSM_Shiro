<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
		body, html{width: 100%;height: 100%; margin:0;font-family:"微软雅黑";}
		#l-map{height:300px;width:100%;}
		#r-result{width:100%;}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=3ap5h6hGPzI79D7mzot2n86V0bbyYtfu"></script>
	
</head>
<body>
	<h1>shrio的main页面</h1>
	<h1>
		登录成功,
		<shiro:principal />
		[<shiro:principal  property="name"/>]
		
		<a href="${pageContext.request.contextPath }/shiroController/logout">退出</a>
	</h1>
	<hr>
	<div>
		<h2>测试授权</h2>

		<shiro:hasRole name="admin">
			<a href="#">增加</a>
		</shiro:hasRole><br>
		<shiro:hasPermission name="user:delete2">
			<a href="#">删除</a>
    用户[<shiro:principal />]拥有权限user:delete<br />
		</shiro:hasPermission>
		<shiro:hasPermission name="user:delete1">
			<a href="#">删除</a>
    用户[<shiro:principal />]拥有权限user:delete<br />
		</shiro:hasPermission>
		<shiro:hasPermission name="user:delete1">
			<a href="#">删除</a>
    用户[<shiro:principal />]拥有权限user:delete<br />
		</shiro:hasPermission>
		<shiro:hasPermission name="user:delete">
			<a href="#">删除</a>
    用户[<shiro:principal />]拥有权限user:delete<br />
		</shiro:hasPermission>
	</div>
	<hr>
	<div>
	测试权限注解<br>
	<a href="${pageContext.request.contextPath }/shiroController/update">修改</a><br>
	</div>
	<a href="${pageContext.request.contextPath }/shiroController/sessionTest">Session_shiro</a><br>
	<a href="${pageContext.request.contextPath }/shiroController/rememberMeTest">-RememberMe Test -</a><br>
	<hr>
	<div id="l-map"></div>
	<div id="r-result"></div>
</body>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("l-map");            // 创建Map实例
	map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
	var local = new BMap.LocalSearch(map, {
		renderOptions: {map: map, panel: "r-result"}
	});
	local.search("餐饮");
</script>
</html>