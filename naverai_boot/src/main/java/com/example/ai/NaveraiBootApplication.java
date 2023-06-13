package com.example.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import chatbot.PizzaMapper;
import objectdetection.ObjectDetectionController;

@SpringBootApplication
@ComponentScan
@ComponentScan(basePackages = {"cfr", "pose", "stt_csr", "tts_voice", "mymapping", "ocr", "chatbot"})
@ComponentScan(basePackageClasses = ObjectDetectionController.class)

@MapperScan(basePackageClasses = PizzaMapper.class)
public class NaveraiBootApplication { //웹서버 시작 + 어노테이션 스캔

	public static void main(String[] args) {
		SpringApplication.run(NaveraiBootApplication.class, args);
	}

}
