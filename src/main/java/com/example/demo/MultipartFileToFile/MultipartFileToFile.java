package com.example.demo.MultipartFileToFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class MultipartFileToFile {

	 public static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
	        File file = new File(multipartFile.getOriginalFilename());
	        try (OutputStream outputStream = new FileOutputStream(file)) {
	        	
	            outputStream.write(multipartFile.getBytes());
	        }
	        return file;
             
	    }
	 
}
