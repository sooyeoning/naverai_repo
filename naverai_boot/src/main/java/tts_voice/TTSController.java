package tts_voice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.ai.MyNaverInform;
import com.example.ai.NaverService;

import stt_csr.STTServiceImpl;

@Controller
public class TTSController {
	@Autowired
	@Qualifier("ttsservice")
	NaverService service;
	
	@RequestMapping("/ttsinput")
	public ModelAndView ttsinput() {
				// 사진이 들어가 있는 파일리스트 보여주는 뷰
				File f = new File(MyNaverInform.path); // 파일과 디렉토리 정보 제공
				String[] filelist = f.list(); // 특정 디렉토리에 들어가있는 파일리스트 배열 제공

				// file_ext 배열에 존재하는 확장자만 모델에 포함
				String file_ext[] = { "txt" };

				// 이미지 확장자를 가지고 있는 파일만 포함하는 배열 새로 생성 - 문제: 배열의 갯수가 몇개가 될지 모름 - 해결방안:
				// ArrayList(갯수 변동가능)
				ArrayList<String> newfilelist = new ArrayList();
				for (String onefile : filelist) {
					// 마지막 .의 위치를 찾아라 (파일명에 .이 많을수 있음) > 확장자만 가져오려면 마지막 . 뒤 문자열만 추출 필요 >
					// substring(뽑아낼 문자열이 있는 인덱스값= 마지막. 인덱스+1)
					String myext = onefile.substring(onefile.lastIndexOf(".") + 1);
					for (String imgext : file_ext) { // 이미지 확장자만 가진 배열에서 각 인덱스값 꺼내기
						if (myext.equals(imgext)) { // 이미지 확장자와 filelist 배열 안 각 파일의 파일확장자
							newfilelist.add(onefile);
							break;
						}
					}
				}

				ModelAndView mv = new ModelAndView();
				mv.addObject("filelist", newfilelist);
				mv.setViewName("ttsinput");
				return mv;
	}
	
	@RequestMapping("/ttsresult")
	public ModelAndView ttsresult(String text, String speaker) throws IOException { 
		 String ttsresult = null;
		
		 if(speaker == null) { //speaker 선택 안했을경우
			 ttsresult = service.test(text); //기본언어 Kor
		 } else {
			 ttsresult = ((TTSServiceImpl)service).test(text, speaker);
		 }
		 
		// 서비스 결과값 전달받음 ModelAndView mv = new ModelAndView();
		 ModelAndView mv = new ModelAndView();
		 mv.addObject("ttsresult", ttsresult); //mp3파일명
		 mv.setViewName("ttsresult");

		 return mv;
	}

}
