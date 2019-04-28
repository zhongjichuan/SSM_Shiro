<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<style>
li{
list-style-type:none;
}
</style>
<script type="text/javascript" src="resources/JS/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
$(function(){
	
	$(".menu").hide(); 
	
	$("li").click(function(){
		var check_ul = $(".check_ul");
		var ul = $(this).find('ul');
		if(ul.is(":hidden")){
			check_ul.slideUp();//带有滑动效果
			ul.slideDown();
		}else{
			ul.slideUp();
		}
	});
});

</script>
<h1>普通的登录页面</h1>
	<form action="${pageContext.request.contextPath }/LoginController/login">
		<div>
			用户名：<input type="text" id="username" name="name"><br>
			密码: <input type="text" id="password" name="password"><br>
		</div>
		<input type="submit" value="登录">
	</form>
		<hr>
		<ul>
			<li id="li">数字
				<ul class="menu check_ul" id="ul">
				<li><a href="#">111</a></li>
				<li>222</li>
				<li>333</li>
				<li>444</li>
				<li>555</li>
				</ul>
			</li>
			<li >字母
				<ul  class="menu check_ul">
				<li>aa</li>
				<li>aa</li>
				<li>aa</li>
				<li>aa</li>
				<li>aa</li>
				</ul>
			</li>
			<li >字母数字
				<ul  class="menu check_ul">
				<li>aa111</li>
				<li>aa222</li>
				<li>aa333</li>
				<li>aa444</li>
				<li>aa555</li>
				</ul>
			</li>
		</ul>
</body>
</html>