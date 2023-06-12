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
	//구현
	let json = JSON.parse('${response}');
	$('#second').html("답변내용: " + json.bubbles[0].data.description);
});
</script>
</head>
<body>
<h3>질문내용: ${param.request }</h3>
<h3 id="second"></h3>
<h3>모든 답변: ${response }</h3>
</body>
</html>