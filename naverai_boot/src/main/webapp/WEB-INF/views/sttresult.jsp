<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/jquery-3.6.4.min.js"></script>
<script>
$(document).ready(function(){ //html(오디오 재생버튼) 로딩 후 
	document.getElementById("mp3").play(); //음성 play
});
</script>
</head>
<body>
<h3>${sttresult }</h3>

<!-- html -->
<audio id="mp3" src="/naverimages/${param.mp3file }" controls></audio>
<!-- 
이 위치에 추가될 경우 오디오 재생버튼 나온 후 오디오 자동 재생
<script>
document.getElementById("mp3").play();
</script> 
-->
</body>
</html>