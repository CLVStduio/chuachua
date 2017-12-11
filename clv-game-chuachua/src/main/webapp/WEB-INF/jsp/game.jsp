<%@ taglib prefix="c" uri="/WEB-INF/tlds/c.tld"%>
<c:set var="ctx">${pageContext.request.contextPath}</c:set>
<c:set var="version">0.0.0.7</c:set>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>游戏等待中。。。</title>
<link rel="stylesheet" href="${ctx}/resources/css/style.css">
<link rel="stylesheet" href="${ctx}/resources/css/game.css">
<style type="text/css">
.logger {
	
}
</style>
</head>
<body>
<div id="match" class="match">
		<img class="quantu" alt="" src="${ctx }/resources/img/wait/quantu-nor.png">
		<img class="shuaxin" alt="" src="${ctx }/resources/img/wait/shuaxin-icon.gif">
		<img class="zzxz" alt="" src="${ctx }/resources/img/wait/zzxz.png">
</div>
<div id="game" class="game">
	<div id="Main" class="main">
		<img id="ariBan" class="airBan" src="${ctx }/resources/img/game/bang-long-air.png" />
		<img class="myBan" src="${ctx }/resources/img/game/bang-long-my.png" />
		<img class="vs" src="${ctx }/resources/img/game/vs.png" />
		<img class="ball" src="${ctx }/resources/img/game/qiu.png" />
		<p id="airName" class="name">绝世高手</p>
		<p id="myName" class="name">武林高手</p>
		<p id="time" class="time">3:00</p>
	</div>
</div>
</body>
<script type="text/javascript" src="${ctx }/resources/lib/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/sockjs.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/lib/stomp.min.js"></script>
<script type="text/javascript">
var ctx = "${ctx}";
$(function(){
});
</script>
<script type="text/javascript" src="${ctx }/resources/js/constants.js?version=${version }"></script>
<script type="text/javascript" src="${ctx }/resources/js/game.js?version=${version }"></script>
<script type="text/javascript" src="${ctx }/resources/js/webSocket.js?version=${version }"></script>
</html>