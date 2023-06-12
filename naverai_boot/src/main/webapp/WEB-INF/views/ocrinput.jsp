<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:forEach items="${filelist }" var="filename">
 <h3>
 <a href="/ocrresult?image=${filename }"> ${filename }</a>
 </h3>
<a href="/ocrresult?image=${filename }"> <img src="/naverimages/${filename }" width="100" height="100"></a>
</c:forEach>

</body>
</html>

<!-- a태그 get방식밖에 못 쓴다 -->
<!-- jsp -> controller 전달하는 매개변수명 다를경우 controller에서 
@RequestParam 사용 -->