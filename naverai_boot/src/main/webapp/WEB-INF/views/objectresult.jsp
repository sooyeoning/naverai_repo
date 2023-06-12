<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- 1 얼굴분석 서비스: 모델을 json 객체 변환 - 자바 api(pom.xml 추가)
     2 사물인식 서비스: 모델을 json 객체 변환 - 자바스크립트 내 JSON.parse 사용-->
     
<script src="/js/jquery-3.6.4.min.js"></script>
<script> //js변수
$(document).ready(function(){
	var json = JSON.parse('${objectresult }'); //쌍따옴표 불가
	
	$("#count").html("<h3>"+json.predictions[0].num_detections+"개의 사물 탐지 </h3>");
	
	//
	for(var i=0; i < json.predictions[0].num_detections; i++){
		
		$("#names").append(	//div id=names 부분에 내용 추가	
		json.predictions[0].detection_names[i] + " - " +
		parseInt(json.predictions[0].detection_scores[i] * 100)  + "% <br>"
		//parseInt(parseFloat(json.predictions[0].detection_scores[i]) * 100)
		);
		
		/* 
		var x1 = json.predictions[0].detection_boxes[i][0];
		var y1 = json.predictions[0].detection_boxes[i][1];
		var x2 = json.predictions[0].detection_boxes[i][2];
		var y2 = json.predictions[0].detection_boxes[i][3];
		$("#boxes").append("x1, y1좌표: ("+x1+","+y1 +") x2, y2 좌표: ("+x2+","+y2+")<br>"); */
		
	}//for

	let mycanvas = document.getElementById("objectcanvas");
	let mycontext = mycanvas.getContext("2d");
	
	let objectimage = new Image();
	objectimage.src = "/naverimages/${param.image}";//url 통신 이미지 다운로드 시간 대기
	objectimage.onload = function(){//이미지 다운로드 후 시작
		mycontext.drawImage(objectimage, 0,0, objectimage.width, objectimage.height);//캔버스에 이미지 그리기
		
		//사물의 x,y 좌표 찾기
		var boxes = json.predictions[0].detection_boxes; // boxes = 각 사물이 들어간 배열, boxes[0] = 한개 사물의 4개의 좌표값을 갖고 있는 배열
		for(var i=0; i<boxes.length; i++){
			var y1 = boxes[i][0] * objectimage.height;
			var x1 = boxes[i][1] * objectimage.width;
			var y2 = boxes[i][2] * objectimage.height;
			var x2 = boxes[i][3] * objectimage.width;
			
			let colors = ["red", "yellow"," orange"];
			
			//캔버스 안 사각형
			mycontext.strokeStyle = "green";
			mycontext.lineWidth = 3;
			mycontext.strokeRect(x1, y1, (x2-x1), (y2-y1));
			
			//캔버스 안 텍스트
			mycontext.fillStyle="red";
			mycontext.font = "bold 12px batang";
			mycontext.fillText(json.predictions[0].detection_names[i] ,x1 ,y1-5);
			

		}//for
		
	}//objectimage onload
	
});
</script>

</head>
<body>
<h3>${objectresult }</h3>

<div id="count"></div>
<div id="names" style="border: 2px solid pink"></div>
<div id="boxes" style="border: 2px solid orange"></div>
<canvas id="objectcanvas" width="800" height="500" style="border:2px solid pink"></canvas>

</body>
</html>