<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>chatbot 질문창</title>
<script src="js/jquery-3.6.4.min.js"></script>
<script>
$(document).ready(function(){
	//1 type:button 클릭하면 request 질문을 response 붙여넣는다
	$('input:button').on('click', function(){
		$('#response').append("질문: "+ $('#request').val()+"<br>");
		$.ajax({
			url: "/chatbotajaxprocess",
			data: {"request": $('#request').val() , "event": $(this).val()},
			type: 'get',
			dataType: 'json',
			success: function(server){
				let bubbles = server.bubbles;
				for(let b in bubbles){
					//1.기본(텍스트/ 텍스트+url)
					if(bubbles[b].type=='text'){
						$("#response").append("기본답변 : " + bubbles[b].data.description + "<br>");
						if(bubbles[b].data.url != null){ //url 있다면
							$("#response").append
							('<a href=' + bubbles[b].data.url + '>' + bubbles[b].data.description + '</a><br>');
						}
					}
					//이미지이거나 멀티링크
					else if(bubbles[b].type=='template'){
						//2.이미지(이미지)
						if(bubbles[b].data.cover.type == 'image'){
							$("#response").append
							('<img src=' + bubbles[b].data.cover.data.imageUrl + ' width=200 height=200 ><br>');
						}
						//3.멀티링크(url) 
						else if(bubbles[b].data.cover.type == 'text'){
							$("#response").append("멀티링크답변 : " + bubbles[b].data.cover.data.description + "<br>");
						}
						//4.이미지+멀티링크 공통(url)
						for(let c in bubbles[b].data.contentTable){
							for(let d in bubbles[b].data.contentTable[c]){
								let link = bubbles[b].data.contentTable[c][d].data.title;
								let href = bubbles[b].data.contentTable[c][d].data.data.action.data.url;
								$("#response").append('<a href=' + href +'> ' + link +'</a><br>');
							}
						}
					
					}
				
				}//for(let b in bubbls) end
				
			},//success end
			error: function(e){
				alert(e);
			}
			});//ajax
		
	});//on	
});
</script>
</head>
<body>
질문 : <input type=text id="request">
<input type=button value=답변 id=event1>
<input type=button value=웰컴메세지 id=event2>
<br>

대화내용 : <div id="response" style="border: 2px solid blue"></div>
</body>
</html>