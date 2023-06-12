
<%@page import="java.math.BigDecimal"%>
<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- controller에서 받은 것으로 String 형태 -->
<h1>${faceresult }</h1>

<!-- 자바언어 String -> 내용형식(js 객체형태인 json) -->

<%
String faceresult = (String)request.getAttribute("faceresult"); //json 객체로 만들어야 해서 변수로 받음
JSONObject total = new JSONObject(faceresult);

//{"info":{"size":{"width":1546,"height":1546},"faceCount":2}
JSONObject info = (JSONObject)total.get("info");
int faceCount = (Integer)info.get("faceCount");

//"faces":[{"celebrity":{"value":"티파니","confidence":0.260287}},{"celebrity":{"value":"박광현","confidence":0.461609}}]
JSONArray faces = (JSONArray)total.get("faces"); //faces 값이 배열이므로 []
for(int i=0; i<faces.length(); i++){
	JSONObject oneFace = (JSONObject)faces.get(i);//배열 각각의 인덱스 정보
	JSONObject celebrity=(JSONObject)oneFace.get("celebrity");
	String name = (String)celebrity.get("value");
	BigDecimal confidence = (BigDecimal)celebrity.get("confidence");
	
	//정확도 70% 이상인 경우만 출력
	double confidenceDoublce = confidence.doubleValue();//0.461609
	if(confidenceDoublce >= 0.7){
	out.println("<h3>"+name+" 유명인을 "+ Math.round(confidenceDoublce*100) +"% 닮았습니다</h3>");
	}
}
%>

<h3>총 <%=faceCount %>명의 얼굴을 찾았습니다</h3>

</body>
</html>