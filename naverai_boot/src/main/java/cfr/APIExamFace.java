package cfr;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import com.example.ai.MyNaverInform;
/* json {}:객체 []:배열
 * {
 * "info":{
 *          "size":{"width":1440,"height":1440}, //이미지 사진 크기
 *          "faceCount":2 //찾은 얼굴 수: info.faceCount(jsp형태)
 *        },
 * "faces":[
 *           {
 *             "celebrity":{"value":"박광현","confidence":0.562866} 
 *             //faces[0].celebrity.value: 첫번째 사람이 닮은 연예인이름(jsp형태)
 *             //faces[0].confidence.value: 첫번째 사람이 연예인을 닮은 정도(jsp형태)
 *           },
 *           {
 *             "celebrity":{"value":"서현진","confidence":0.139184}
 *           }
 *          ]
 *}
*/

// 네이버 얼굴인식 API 예제
public class APIExamFace {

    public static void main(String[] args) {

        StringBuffer reqStr = new StringBuffer();
        String clientId = MyNaverInform.clientID;//애플리케이션 클라이언트 아이디값";
        String clientSecret = MyNaverInform.secret;//애플리케이션 클라이언트 시크릿값";
        String path = MyNaverInform.path;

        try {
            String paramName = "image"; // 파라미터명은 image로 지정
            String imgFile = path + "image3.jpg";
            File uploadFile = new File(imgFile);
            String apiURL = "https://naveropenapi.apigw.ntruss.com/vision/v1/celebrity"; // 유명인 얼굴 인식 url
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
                System.out.println(response.toString()); //콘솔 출력 결과
            } else {
                System.out.println("error !!!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


/* 닮은꼴 찾기
 * 1 @Controller
 * 2 메서드 추가 - 고를 수 있는 사진이 들어가있는 폴더의 파일리스트 생성
 * 3 ModelAndView - 파일리스트 배열 전달, 뷰 설정
 * 4 application.properties: 포트번호, jsp 뷰 설정(기본으로 jsp 사용안하므로)
 * 5 src-main-webapp > WEB-INF-views 만들고 > 뷰 만들기
 * 6 servlet(session 사용시), jsp, jstl 라이브러리 pom.xml 추가
 * 7 faceinput.jsp : taglib 추가 
 * 8 faceinput.jsp : (6+7번 해야)c 태그 사용가능
 * 9 실행: @ComponentScan(basePackages = "cfr") 추가 후 NaverBootApplication 실행
 * 10 MyWebConfig.java : WebMvcConfigurer 상속 > addResourceHandlers 오버라이딩
 * 11 faceinput.jsp : img 태그 경로 변경 - 이미지 서버에 출력됨
 * 12 faceinput.jsp : 클릭시 이동할수 있도록 a태그 설정 > image 매개변수를 갖고 faceresult url로 이동
 * 13 공통으로 사용할 service = interface로 만든다, @ 안 붙음(interface를 상속받은 서비스만 @ 붙는다)
 * 14 cfr에서만 사용할 service = interface 상속받음 
 * - 메서드 구현 + @Service 붙인다 + 
 * 15 @autowired, @qualifer - service
 * 16 faceresult 메서드 구현
 * 17 faceresult 뷰 구현
 *  */
 