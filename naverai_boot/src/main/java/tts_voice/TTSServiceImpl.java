package tts_voice;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.example.ai.MyNaverInform;
import com.example.ai.NaverService;

//네이버 음성합성 Open API 예제
@Service("ttsservice")
public class TTSServiceImpl implements NaverService{

	public String test(String textfile) {
		return test(textfile, "nreview");//추가 변수는 기본값 지정
	}
	
	public String test(String textfile, String speaker) {
		
     String tempname = "";

     String clientId = MyNaverInform.voice_clientID;//애플리케이션 클라이언트 아이디값";
     String clientSecret = MyNaverInform.voice_secret;//애플리케이션 클라이언트 시크릿값";
     
     try {
         //문자열 읽기: String text = URLEncoder.encode("지금", "UTF-8"); // 2000자
    	 //텍스트 파일 읽기
    	 String text = "";
    	 FileReader fr = new FileReader(MyNaverInform.path + textfile);
    	 Scanner sc = new Scanner(fr);
    	 while(sc.hasNextLine()) {
    		 text += sc.nextLine();
    	 }
    	 
    	 text = URLEncoder.encode(text, "UTF-8");
    	 
         String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";
         URL url = new URL(apiURL);
         HttpURLConnection con = (HttpURLConnection)url.openConnection();
         con.setRequestMethod("POST");
         con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
         con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
         // post request
         String postParams = "speaker="+speaker+"&volume=0&speed=0&pitch=0&format=mp3&text=" + text;
         con.setDoOutput(true);
         DataOutputStream wr = new DataOutputStream(con.getOutputStream());
         wr.writeBytes(postParams);
         wr.flush();
         wr.close();
         int responseCode = con.getResponseCode();
         BufferedReader br;

         if(responseCode==200) { // 정상 호출
             InputStream is = con.getInputStream();
             int read = 0;
             byte[] bytes = new byte[1024];
             
             // 랜덤한 이름으로 mp3 파일 생성
             tempname = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            //제목: 1/10000초 단위 String tempname = Long.valueOf(new Date().getTime()).toString();
             
             File f = new File(MyNaverInform.path +tempname + ".mp3");
             f.createNewFile();
             OutputStream outputStream = new FileOutputStream(f);
             while ((read =is.read(bytes)) != -1) {
                 outputStream.write(bytes, 0, read);
             }
             is.close();
             System.out.println(tempname+"파일은 해당 경로에서 확인하세요");
             
         } else {  // 오류 발생
             br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
             String inputLine;
             StringBuffer response = new StringBuffer();
             while ((inputLine = br.readLine()) != null) {
                 response.append(inputLine);
             }
             br.close();
             System.out.println(response.toString());
         }
     } catch (Exception e) {
         System.out.println(e);
     }
     return tempname + ".mp3";
 }

}