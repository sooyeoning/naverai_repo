package mymapping;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.example.ai.NaverService;

@Service("mapservice")
public class MapServiceImpl implements NaverService{
	
	HashMap<String, String> map = new HashMap<>();
	
	// 질문 - 답변 매핑하기
	public MapServiceImpl() {
		map.put("이름이 뭐니?" , "클로버야");
		map.put("무슨 일을 하니?", "ai 서비스 관련 일을 해");
		map.put("멋진 일을 하는구나", "고마워");
		map.put("난 훌륭한 개발자가 될거야", "넌 할 수 있어");
		map.put("잘 자", "내 꿈 꿔");	
	}
	
	// 답변 
	@Override
	public String test(String question) {
	
		String response = map.get(question);
		
		if(response == null) {
			response =  "아직은 저도 몰라요";
		}
		
		return response;
	}

}
