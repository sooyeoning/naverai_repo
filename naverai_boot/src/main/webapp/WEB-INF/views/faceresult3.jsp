<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
window.onload = function(){ //html 로딩 완성 후 시작
	let mycanvas = document.getElementById("facecanvas");
	let mycontext = mycanvas.getContext("2d");
	
	let faceimage = new Image();
	faceimage.src = "/naverimages/${param.image}";//url 통신 이미지 다운로드 시간 대기
	faceimage.onload = function(){//이미지 다운로드 후 시작
		mycontext.drawImage(faceimage, 0,0, faceimage.width, faceimage.height);//캔버스에 이미지 그리기
		
		//script 태그 안 jstl 사용 가능 - 얼굴 좌표 roi - (x,y,width,height)
		<% //jsp 변수 사용
		//get메서드의 리턴값 = Object 이므로 형변환 필요
		//json형태의 값을 String타입으로 전달받았으므로 우선 String형변환 후 json형변환 필요
		String faceresult2 = (String)request.getAttribute("faceresult2");
		JSONObject total = new JSONObject(faceresult2);
		JSONArray faces = (JSONArray)total.get("faces");

		for(int i=0; i<faces.length(); i++){
			JSONObject oneperson = (JSONObject)faces.get(i);
			JSONObject roi = (JSONObject)oneperson.get("roi");
			
			int x = (Integer)roi.get("x");
			int y = (Integer)roi.get("y");
			int width = (Integer)roi.get("width");
			int height = (Integer)roi.get("height");
		%>
		//js
		mycontext.lineWidth = 3;
		
		//얼굴위치인식
		mycontext.strokeStyle = "pink";
		mycontext.strokeRect(<%=x%>,<%=y%>,<%=width%>,<%=height%> );
		
		//일부이미지만 복사: var 사용: for문 안에 있으므로 변수 선언하므로 재선언 가능한 것 사용해야함 / let 사용불가(재선언 불가)
		var copyimage = mycontext.getImageData(<%=x%>,<%=y%>,<%=width%>,<%=height%>); //이미지복사
		mycontext.putImageData(copyimage, <%=x%>,<%=y+300%> ); //이미지 붙여넣기(붙여넣을 이미지, x좌표, y좌표)
		
		//모자이크처리
		mycontext.fillStyle = "orange"; 
		mycontext.fillRect(<%=x%>,<%=y%>,<%=width%>,<%=height%> );
		
	<%	
	} //jsp-for end
	%>
	}//faceimage.onload
	
	}//onload

</script>
</head>
<body>

<canvas id="facecanvas" width="500" height="500" style="border: 2px solid pink">

</canvas>
</body>
</html>