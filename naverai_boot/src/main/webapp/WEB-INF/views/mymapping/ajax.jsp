<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/jquery-3.6.4.min.js"></script>
<script>
$(document).ready(function(){
	$("#ajaxbtn").on('click', function(){
		$.ajax({
			url: "/myoutputajax",
			data: {'question': $("#question").val()},
			type: "get",
			dataType: "json",
			success: function(server){
				//controller에서 준 답변: {"response": 텍스트답변, "mp3": mp3 파일명}
				$("#response").html(server.response);
				$("#mp3").attr("src", "/naverimages/"+server.mp3);
			},
			error: function(e){
				alert(e);
			}
			
		});//ajax
	})//on end
});
</script>
</head>
<body>
<!-- 
non-ajax
<form action="/myoutput">
키보드 입력 텍스트 : <input type="text" name="question">
<input type=submit value="대화">
</form> 
-->

질문 : <input type="text" name="question" id="question">
<input type=button id="ajaxbtn" value="대화">

<h3>답변(텍스트): <div id="response"></div></h3>
<audio id="mp3" src="" controls></audio>
</body>
</html>