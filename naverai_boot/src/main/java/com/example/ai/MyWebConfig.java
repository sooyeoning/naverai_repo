package com.example.ai;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebConfig implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/naverimages/**").addResourceLocations("file:///"+MyNaverInform.path);

	}

}

/*	WebConfig.java:
 *  WebMvcConfigurer 상속 > addResourceHandlers 오버라이딩
 	외부 파일의 실제 경로를 접근 url로 설정 
 *  ㄴ addResourceLocations: 파일의 실제 경로, addResourceHandler: 접근 url 경로 설정, 포트번호까지생략했다는 의미로 /부터 씀
 *  
 *  NaveraiBootApplication : @ComponentScan 추가 : 현재 패키지 읽어오기
 *  ㄴ 원래는 @SpringBootApplication만으로 com.example.ai 패키지 안 어노테이션들 스캔함
 *  ㄴ 근데 @ComponentScan(basePackages = "cfr") 코드 추가되면서 기본 스캔패키지가 cfr로 변경됨
 *  ㄴ com.example.ai 패키지 안에 @Configuration 역할을 하는 MyWebConfig.java 파일이 추가되면서 com.example.ai 패키지도 스캔 필요해짐
 *  ㄴ NaveraiBootApplication 파일에 @ComponentScan 추가(application 파일과 mywebcongig 파일은 동일한 패키지 내에 있으므로 basepackages 설정 생략 가능)
 *  
 *  faceinput.jsp : 
 *  경로 추가 설정(WebConfig 파일에서 설정한 url: /naverimages/) > jsp 내 url도 변경 필요<img src="/naverimages/${filename }" /> 
 * */
 