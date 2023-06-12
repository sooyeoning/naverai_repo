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
	
	//인수로 전달받은 문자열을 JavaScript 객체로 변환/ string -> json 객체
	var json = JSON.parse('${ocrresult}'); 
	$('#output').html(JSON.stringify(json)); //JavaScript 값이나 객체를 JSON 문자열로 변환
	
	let mycanvas = document.getElementById("ocrcanvas"); // 자바스크립트 객체 <-> $("#posecanvas") jquery 객체 
	let mycontext = mycanvas.getContext("2d");
	let myimage = new Image();
	myimage.src = "/naverimages/${param.image}";
	
	myimage.onload = function(){
		if(myimage.width > mycanvas.width){ //이미지 잘리지 않게
			mycanvas.width = myimage.width;
		}
		mycontext.drawImage(myimage, 0,0,myimage.width, myimage.height);
		
		//이미지 글씨 박스화
		let fieldslist = json.images[0].fields; //모든 단어를 가진 배열
		for(let i in fieldslist){ //단어만 뽑기
			if(fieldslist[i].lineBreak == true){
				$('#output2').append(fieldslist[i].inferText + "<br>");
			} else {
				$('#output2').append(fieldslist[i].inferText + "&nbsp;");
			}
		
			var x = fieldslist[i].boundingPoly.vertices[0].x; //단어 시작 x좌표
			var y = fieldslist[i].boundingPoly.vertices[0].y; //단어 시작 y좌표
			var width = fieldslist[i].boundingPoly.vertices[1].x - x; //단어 가로 크기 
			var height = fieldslist[i].boundingPoly.vertices[2].y - y; //단어 세로 크기 

			mycontext.strokeStyle = "blue";
			mycontext.lineWidth = 2;
			mycontext.strokeRect(x, y, width, height);
			
		}//for end
	}//myimage.onload
	
});//ready
</script>
</head>
<body>
<!-- <h3>${poseresult }</h3> -->
</body>
<div id="output" style="border: 2px solid orange"></div>
<div id="output2" style="border: 2px solid pink"></div>
<canvas id="ocrcanvas" style="border: 2px solid green" width="500" height="500"></canvas>
</html>