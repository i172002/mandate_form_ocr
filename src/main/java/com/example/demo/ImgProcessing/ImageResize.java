package com.example.demo.ImgProcessing;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageResize {
	
	public static File imageResize(File init_image) {
		
		System.out.println("Inside Resize");
		
		Mat input_image = Imgcodecs.imread(init_image.getAbsolutePath());
		
		String image_name = init_image.getName();
        String resized_output_path = "resized-"+image_name+"."+image_name.substring(image_name.lastIndexOf(".")+1);

		Mat resizeimage = new Mat();
		Size sz = new Size(1195,573);
		
		Imgproc.resize( input_image, resizeimage, sz );
		Imgcodecs.imwrite(resized_output_path,resizeimage);
		
		System.out.println("Resize Completed");

		File resized_image =  new File(resized_output_path);
		return resized_image;
	}

}
