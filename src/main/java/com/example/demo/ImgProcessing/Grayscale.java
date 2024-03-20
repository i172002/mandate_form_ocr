package com.example.demo.ImgProcessing;

import java.io.File;
import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Grayscale {
    public static File convertToGrayscale(File image) throws IOException {
        // Read the input image
        Mat inputImage = Imgcodecs.imread(image.getAbsolutePath());
        
        //String output_directory = "C:\\Users\\intern-infant\\Desktop\\customGrayscale\\";
        String image_name = image.getName();
        String output_path = "OpenCVGrayScaled_" + image_name;
        
        // Check if the image is loaded successfully
        if (inputImage.empty()) {
            throw new IOException("Error: Unable to read the input image!");
        }

        // Convert the input image to grayscale
        Mat grayscaleImage = new Mat();
        Imgproc.cvtColor(inputImage, grayscaleImage, Imgproc.COLOR_BGR2GRAY);

        // Save the grayscale image
        Imgcodecs.imwrite(output_path, grayscaleImage);
        
//        System.out.println("Grayscale completed");
//        File output_image = new File(output_path);
       
        inputImage.release();
        grayscaleImage.release();
       
      
        
        return new File(output_path);
    }
}

