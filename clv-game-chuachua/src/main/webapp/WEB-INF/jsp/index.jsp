<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>

<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>大厅</title>
<link rel="stylesheet" href="${ctx}/resources/css/style.css">
<style type="text/css">
.name {
	width:60%;
	margin:40% 20% 0 20%;
}
.yuanqiu {
	width:14%;
	margin:15% 43%;
}
.tuibang {
	width:60%;
	margin:0 20%;
}
</style>
</head>
<body background="${ctx }/resources/img/background.jpg">
	<div>
		<img class="name" alt="" src="${ctx }/resources/img/index/chuachua.png">
		<img class="yuanqiu" alt="" src="${ctx }/resources/img/index/main-yuanqiu.png">
		<img class="tuibang" alt="" src="${ctx }/resources/img/index/main-tuibang.png">
	</div>
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