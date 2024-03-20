package com.example.demo.ImgProcessing;


import java.io.File;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.web.multipart.MultipartFile;


public class Threshold_Inc{

	public static File threshold_inc(File image) {

	try {
		
		String image_name = image.getName();
		String output_path = "Thresholded-"+image_name+"."+image_name.substring(image_name.lastIndexOf(".")+1);
	    Mat source =Imgcodecs.imread(image.getName(), Imgcodecs.IMREAD_COLOR);
	    Mat destination = new Mat(source.rows(), source.cols(), source.type());
	    destination = source.clone();
	    Imgproc.threshold(source, destination, 100, 255, Imgproc.THRESH_BINARY);
	    Imgcodecs.imwrite(output_path, destination);
	    

//	    System.out.println("Image completed");
	    File output_image = new File(output_path);
	    return  output_image;

	    
	} catch (Exception e) {
	    System.out.println("Error: " + e.getMessage());
	    return null;
	}
	}
	
}
