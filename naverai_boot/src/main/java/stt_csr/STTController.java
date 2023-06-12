package stt_csr;

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

@Controller
public class STTController {

	@Autowired
	@Qualifier("sttservice")
	NaverService service; //NaverService 오버라이딩 메서드 호출

	@RequestMapping("/sttinput")
	public ModelAndView sttinput() {

		// 사진이 들어가 있는 파일리스트 보여주는 뷰
		File f = new File(MyNaverInform.path); // 파일과 디렉토리 정보 제공
		String[] filelist = f.list(); // 특정 디렉토리에 들어가있는 파일리스트 배열 제공

		// file_ext 배열에 존재하는 확장자만 모델에 포함
		String file_ext[] = { "mp3", "m4a", "wav" };

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
		mv.setViewName("sttinput");
		return mv;
	}

	@RequestMapping("/sttresult")
	public ModelAndView sttresult(String mp3file, String lang) throws IOException { 
		 String sttresult = null;
		
		 if(lang == null) { //언어 선택 안했을경우
			sttresult = service.test(mp3file); //기본언어 Kor
		 } else {
			sttresult = ((STTServiceImpl)service).test(mp3file, lang);
		 }
		 
		// 서비스 결과값 전달받음 ModelAndView mv = new ModelAndView();
		 ModelAndView mv = new ModelAndView();
		 mv.addObject("sttresult", sttresult); //json 형태{(key)text: (value)텍스트변환결과}
		 mv.setViewName("sttresult");
		
		 //추가 MyNaverInform.path 경로에 음성 -> text 변환한 결과 파일로 저장(파일명: 전송받은 음성파일이름_20230609112022.txt)
	
		 String formatdate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		 String filename = mp3file.substring(0, mp3file.lastIndexOf(".")) + "_" + formatdate + ".txt";
		 FileWriter fw = new FileWriter(MyNaverInform.path + filename , false);
		 
		 // sttresult(기존 json 형태인데 메서드 내 String 선언) -> JSONObject 형변환 -> 키:text의 value인 텍스트변환 결과(기존 Object 타입) 가져와서 String 형변환
		 JSONObject jsontext =  new JSONObject(sttresult);
		 String text = (String)jsontext.get("text");
		 
		 fw.write(text);
		 fw.close();

		 return mv;
	}

}
