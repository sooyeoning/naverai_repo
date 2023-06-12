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
	<!-- map으로 구성해도 좋을듯 -->
	<%
	String speakers[] = 
	{"mijin", "jinho", "clara", "matt", "shinji", "meimei", "liangliang", "jose",
			"carmen", "nnaomi", "nhajun", "ndain"};

	String[] speakerinforms = {
			"미진:한국어, 여성음색", "진호:함국어, 남성음색", 
			"클라라 : 영어, 여성 음색", "매트 : 영어, 남성 음색",
			"신지: 일본어, 남성 음색", "메이메이 : 중국어, 여성 음색",
			"량량 : 중국어, 남성 음색", "호세 : 스페인어, 남성 음색",
			"카르멘 : 스페인어, 여성 음색", 	"나오미 : 일본어, 여성 음색",
			"하준 : 한국어, 아동 음색 (남)", "다인 : 한국어, 아동음색 (여)"
			};
	%>
	
	<form action="ttsresult" method="get">
		음색 선택: <br>
		<% for (int i = 0; i < speakers.length; i++) { %>
		<input type="radio" name="speaker" value=<%=speakers[i]%> > <%=speakerinforms[i]%><br>
		<%}%>
		text파일선택: <br>
		<select name="text">
		<c:forEach items="${filelist }" var="onefile">
			<option value="${onefile }">${onefile }</option>
		</c:forEach>
		</select>
		<input type="submit" value="mp3변환요청">
	</form>
</body>
</html>

<!-- 
radio 타입: name 이름으로 value값이 매개변수로 넘어감
select-option 타입: select name 이름으로 option의 value값
 -->