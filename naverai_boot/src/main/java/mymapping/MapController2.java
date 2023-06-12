package mymapping;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.ai.MyNaverInform;
import com.example.ai.NaverService;

@Controller
public class MapController2 {

	@Autowired
	@Qualifier("mapservice")
	NaverService service;

	@Autowired
	@Qualifier("ttsservice")
	NaverService ttsservice;

	@RequestMapping("/myinputajax")
	public String myinput() {
		return "mymapping/ajax";
	}

	@RequestMapping("/myoutputajax")
	@ResponseBody //ajax: sts3 - pom.xml 라이브러리 추가
	public String myoutput(String question) throws IOException {
		  String response = service.test(question);

		  //답변 -> txt 파일 생성 
		  //filewriter(경로+이름), filereader(경로+이름), test 메서드는 파일명만 필요
		  FileWriter fw = new FileWriter(MyNaverInform.path+"답변.txt");	 
		  fw.write(response); 
		  fw.close();

		 //TTSServiceImpl로 전달하여 실행 내용을 리턴 
		 String mp3 = ttsservice.test("답변.txt");
		 
		 return "{\"response\" : \""  +  response + "\" , \"mp3\":\"" + mp3 + "\" }";
		// 전달받는 변수명 따옴표 앞 \ , 변수값 앞뒤 따옴표 + 따옴표 앞 \, 
	}
}
