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
<h3>${faceresult2 }</h3>
<%
//자바이므로 get메서드 사용 필요! get메서드의 리턴값 = Object 이므로 형변환 필요
//json형태의 값을 String타입으로 전달받았으므로 우선 String형변환 후 json형변환 필요
String faceresult2 = (String)request.getAttribute("faceresult2");
JSONObject total = new JSONObject(faceresult2);
JSONObject info = (JSONObject)total.get("info"); //{}형태로 객체 
int faceCount = (Integer)info.get("faceCount");

JSONArray faces = (JSONArray)total.get("faces");

//성별 나이 감정 방향
for(int i=0; i<faces.length(); i++){
	JSONObject oneperson = (JSONObject)faces.get(i);
	JSONObject gender = (JSONObject)oneperson.get("gender");
	String gender_value = (String)gender.get("value");
	BigDecimal gender_conf = (BigDecimal)gender.get("confidence");
	double gender_conf_double = gender_conf.doubleValue(); 
	
	JSONObject age = (JSONObject)oneperson.get("age");
	String age_value = (String)age.get("value");
	
	JSONObject emotion = (JSONObject)oneperson.get("emotion");
	String emotion_value = (String)emotion.get("value");
	
	JSONObject pose = (JSONObject)oneperson.get("pose");
	String pose_value = (String)pose.get("value");
	
	out.println("<h3>"+(i+1)+"번째 얼굴의 성별: "+gender_value +", 정확도: "+gender_conf_double+"</h3>");
	out.println("<h3>"+(i+1)+"번째 얼굴의 나이: " + age_value + "</h3>");
	out.println("<h3>"+(i+1)+"번째 얼굴의 감정: " + emotion_value + "</h3>");
	out.println("<h3>"+(i+1)+"번째 얼굴의 방향: " + pose_value + "</h3>");

	//눈코입 위치: landmark
	if(!oneperson.get("landmark").equals(null)){
		JSONObject landmark = (JSONObject)oneperson.get("landmark");
		JSONObject nose = (JSONObject)landmark.get("nose");
		out.println("<h3> 코위치 x좌표: "+	(Integer)nose.get("x")+", y좌표: "+(Integer)nose.get("y")+ "</h3>");
	} else {
		out.println("<h3> 눈코입을 파악할 수 없습니다 </h3>");
	}
}
%>

<h3>총 <%=faceCount %> 명의 얼굴을 찾았습니다.</h3>
</body>
</html>