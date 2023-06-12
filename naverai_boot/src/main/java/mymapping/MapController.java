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
import org.springframework.web.servlet.ModelAndView;

import com.example.ai.MyNaverInform;
import com.example.ai.NaverService;

@Controller
public class MapController {

	@Autowired
	@Qualifier("mapservice")
	NaverService service;

	@Autowired
	@Qualifier("ttsservice")
	NaverService ttsservice;

	@RequestMapping("/myinput")
	public String myinput() {
		return "mymapping/input";
	}

	@RequestMapping("/myoutput")
	public ModelAndView myoutput(String question) throws IOException {
		  String response = service.test(question);

		  //답변 -> txt 파일 생성 
		  //filewriter(경로+이름), filereader(경로+이름), test 메서드는 파일명만 필요
		  FileWriter fw = new FileWriter(MyNaverInform.path+"답변.txt");	 
		  fw.write(response); 
		  fw.close();

		 //TTSServiceImpl로 전달하여 실행 내용을 리턴 
		 String mp3 = ttsservice.test("답변.txt");
		 
		 ModelAndView mv = new ModelAndView(); 
		 mv.addObject("response", response); //답변(텍스트)
		 mv.addObject("mp3",mp3); 			 //답변(mp3)	
		 mv.setViewName("mymapping/output"); 
		 return mv;
		
	}
}
