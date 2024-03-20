package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import nu.pattern.OpenCV;

@SpringBootApplication
public class MandateOcrApplication {

	public static void main(String[] args) {
		SpringApplication.run(MandateOcrApplication.class, args);
		OpenCV.loadShared();

	}

}
