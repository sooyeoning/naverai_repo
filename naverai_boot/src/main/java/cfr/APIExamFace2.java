package cfr;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.example.ai.MyNaverInform;

// 네이버 얼굴인식 API 예제2 
// 1 main으로 java application run 테스트 먼저 해보기
public class APIExamFace2 {

    public static void main(String[] args) {

        StringBuffer reqStr = new StringBuffer();
        String clientId =  MyNaverInform.clientID;//애플리케이션 클라이언트 아이디값";
        String clientSecret =  MyNaverInform.secret;//애플리케이션 클라이언트 시크릿값";

        try {
            String paramName = "image"; // 파라미터명은 image로 지정
            String imgFile = MyNaverInform.path + "image.jpg";
            //이미지 크기 2mb 이상 - 400 오류
            File uploadFile = new File(imgFile);
            String apiURL = "https://naveropenapi.apigw.ntruss.com/vision/v1/face"; // 얼굴 감지
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            // multipart request
            String boundary = "---" + System.currentTimeMillis() + "---";
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            OutputStream outputStream = con.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
            String LINE_FEED = "\r\n";
            // file 추가
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + paramName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
            writer.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();
            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();
            BufferedReader br = null;
            int responseCode = con.getResponseCode();
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 오류 발생
                System.out.println("error!!!!!!! responseCode= " + responseCode);
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }
            String inputLine;
            if(br != null) {
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

/* Main 메서드인 APIExamFace2 -> MVC 패턴으로 변경
 * faceserviceimpl2 
 * 1 service 상속, @Service, 구현해야할 메서드 안에 apiexamface2 메서드 붙여넣기
 * 2 매개변수 변경 - 매개변수로 전달받은 이미지 사용 String imgFile = MyNaverInform.path + image;
 * 3 리턴값 변경 - return response.toString()
 * 4 if문 바깥에 response 선언 
 * FaceController
 * 1 @Autowired, @Qualifier naverService 추가
 * 2 faceinput 메서드 복붙 - 뷰이름, 메서드이름 변경
 * faceinput2.jsp
 * 1 a태그 링크 변경
 * FaceController
 * 1 faceresult 메서드 복붙 - 뷰이름, 메서드이름, 모델 object 매개변수명 변경
 * faceinput2.jsp
   faceresult2.jsp
 * jsp 내 모든 get메서드의 리턴값 = Object 이므로 형변환 필요
   서비스 결과값:json형태의 값을 serviceimple 메서드 매개변수(String타입)으로 전달함
   string으로 전달받았지만 get메서드를 통해 받았으므로 최초 상태는 object임
   String으로 전달받은 것이므로 String형변환
   최초 서비스 결과값은 json이므로 json형변환 필요
 * */
