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
	var json = JSON.parse('${poseresult}');
	
	let mycanvas = document.getElementById("posecanvas"); // 자바스크립트 객체 <-> $("#posecanvas") jquery 객체 
	let mycontext = mycanvas.getContext("2d");
	let myimage = new Image();
	myimage.src = "/naverimages/${param.image}";
	myimage.onload = function(){
		if(myimage.width > mycanvas.width){
			mycanvas.width = myimage.width;
		}
		mycontext.drawImage(myimage, 0,0,myimage.width, myimage.height);
		
		//신체부위 	
		let bodyParts = ["코", "목", "오른쪽 어깨", "오른쪽 팔굼치", "오른쪽 손목", "왼쪽 어깨", "왼쪽 팔꿈치", "왼쪽 손목", "오른쪽 엉덩이", "오른쪽 무릎", "오른쪽 발목", "왼쪽 엉덩이", "왼쪽 무릎", "왼쪽 발목", "오른쪽 눈", "왼쪽 눈", "오른쪽 귀", "왼쪽 귀"];		
	
		let colorinforms = ['red', 'orange', 'yellow', "green", "navy", "purple"];
		
		for(var j=0; j < json.predictions.length; j++ ){
		for(var i=0; i < bodyParts.length; i++){
		if(json.predictions[j][i] != null){
		var x = json.predictions[j][i].x * myimage.width;
		var y = json.predictions[j][i].y * myimage.height;
		
		mycontext.fillStyle = colorinforms[i%colorinforms.length];//colorinforms 색상 글씨색상 변경
		mycontext.fillText(bodyParts[i], x, y);
		}
	}
		}
	}//myimage.onload
});//ready
</script>
</head>
<body>
<!-- <h3>${poseresult }</h3> -->
</body>
<div id="output" style="border: 2px solid orange"></div>
<canvas id="posecanvas" style="border: 2px solid green" width="500" height="500"></canvas>
</html>