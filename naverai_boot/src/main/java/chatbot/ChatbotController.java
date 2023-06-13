package chatbot;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.ai.MyNaverInform;

@Controller
public class ChatbotController {

	@Autowired
	@Qualifier("chatbotservice")
	ChatbotServiceImpl service; //오버라이딩된 메서드 사용하려면 형변환 필요
	
	@Autowired
	@Qualifier("chatbotttsservice")
	ChatbotTTSServiceImpl ttsservice;
	
	@Autowired
	@Qualifier("chatbotsttservice")
	ChatbotSTTServiceImpl sttservice;
	
	@Autowired
	@Qualifier("pizzaservice")
	PizzaServiceImpl pizzaservice;
	
	//non-ajax
	@RequestMapping("/chatbotrequest")
	public String chatbotrequest() {
		return "chatbotrequest";
	}
	
	@RequestMapping("/chatbotresponse")
	public ModelAndView chatbotresponse(String request, String event) {
		String response = "";
		
		if(event.equals("웰컴메세지")) { 
			response = service.test(request, "open");
		} else {
			response = service.test(request, "send");
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("response", response);
		mv.setViewName("chatbotresponse");
		return mv;
	}
	
	//ajax
	//기본답변만 분석한 뷰
	@RequestMapping("/chatbotajaxstart")
	public String chatbotajaxstart() { 
		return "chatbotajaxstart";
	}
	
	//기본+이미지+멀티링크답변 분석한 뷰
	@RequestMapping("/chatbotajax")
	public String chatbotajax() {
		return "chatbotajax";
	}
	
	@RequestMapping("/chatbotajaxprocess")
	public @ResponseBody String chatbotajaxprocess(String request, String event) {
		String response = "";
		
		if(event.equals("웰컴메세지")) { 
			response = service.test(request, "open");
		} else {
			response = service.test(request, "send");
		}
		
		return response; //json 타입
	}
	
	@RequestMapping("/chatbottts")
	@ResponseBody //현재 뷰의 body쪽으로 mp3라는 string을 주겠다(@ResponseBody 붙으면 return 값이 뷰에 대한 설정값 아님)
	public String chatbottts(String text){ //챗봇 답변 매개변수로 전달
		String mp3 = ttsservice.test(text); //답변 텍스트를 해당 경로에 저장 -> mp3 파일 이름 리턴
		return "{\"mp3\":\""+mp3+"\"}";
	}
	
	//음성질문 서버로 업로드
	@PostMapping("/mp3upload")
	@ResponseBody
	public String mp3upload(MultipartFile file1) throws IOException{
		String uploadFile = file1.getOriginalFilename(); //업로드한 파일명:a.mp3
		String uploadPath = MyNaverInform.path;
		File saveFile = new File(uploadPath+uploadFile);
		file1.transferTo(saveFile); //클라이언트 파일을 서버의 파일로 저장
		return "{ \"result\" : \"잘 받았습니다\" }";
	}
	
	//업로드한 음성질문 mp3 파일을 텍스트변환
	@RequestMapping("/chatbotstt")
	@ResponseBody
	public String chatbotstt(String mp3file) {
		String text = sttservice.test(mp3file);
		return text;//이미 json형태
	}
	
	//피자주문을 db-pizza 테이블에 저장
	@RequestMapping("/pizzaorder")
	@ResponseBody
	public void insertPizza(PizzaDTO dto) {
		pizzaservice.insertPizza(dto);
		//리턴값이 있을 경우: return "{\"insertrow\" : "+insertrow+"}"
	}
}
