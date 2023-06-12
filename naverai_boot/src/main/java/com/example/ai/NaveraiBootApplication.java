package com.example.ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import objectdetection.ObjectDetectionController;

@SpringBootApplication
@ComponentScan
@ComponentScan(basePackages = {"cfr", "pose", "stt_csr", "tts_voice", "mymapping", "ocr", "chatbot"})
@ComponentScan(basePackageClasses = ObjectDetectionController.class)
public class NaveraiBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaveraiBootApplication.class, args);
	}

}
