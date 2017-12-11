<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>结算</title>
<link rel="stylesheet" href="${ctx}/resources/css/style.css">
<style type="text/css">
div {
	margin:40% 0;
}
h1 {
	color:#4f4f4f;
	font-size:266px;
	text-shadow: 0 0 0.2em #FFF, 0 0 0.2em #FFF,
        0 0 0.2em #FFF
}
h3 {
	margin:10% 0;
	color:white;
	font-size:133px;
	text-shadow: 0.1em 0.1em 0.2em #4f4f4f;
}
</style>
</head>
<body background="${ctx }/resources/img/background.jpg">
<center>
	<div>
		<h1>${winOrLost }</h1>
		<h3>${score }</h3>
		<img class="tuibang" alt="" src="${ctx }/resources/img/index/main-tuibang.png">
	</div>
</center>
</body>
<script type="text/javascript" src="${ctx }/resources/lib/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".tuibang").click(function(){
		window.location.href= '${ctx}/game';
	});
});
</script>
</html>