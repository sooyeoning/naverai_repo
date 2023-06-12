<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="js/jquery-3.6.4.min.js"></script>
<script>
	$(document).ready(function() {
		//구현
	});
</script>
</head>
<body>
	<%
	String[] languages = { "Kor", "Eng", "Jpn", "Chn" };
	String[] language_names = { "한국어", "영어", "일본어", "중국어" };
	%>
	<form action="sttresult" method="get">
		언어선택: <br>
		<% for (int i = 0; i < languages.length; i++) { %>
		<input type="radio" name="lang" value=<%=languages[i]%> > <%=language_names[i]%><br>
		<%}%>
		mp3파일선택: <br>
		<select name="mp3file">
		<c:forEach items="${filelist }" var="onefile">
			<option value="${onefile }">${onefile }</option>
		</c:forEach>
		</select>
		<input type="submit" value="텍스트로변환요청">
	</form>
</body>
</html>

<!-- 
radio 타입: name 이름으로 value값이 매개변수로 넘어감
select-option 타입: select name 이름으로 option의 value값
 -->