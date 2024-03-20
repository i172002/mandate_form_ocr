package com.example.demo.ImgProcessing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class RemoveBoxes {
	public static File removeBoxes(File image) {
try {
		
		String image_name = image.getName();
		String output_path = "boxes-removed_"+image_name+"."+image_name.substring(image_name.lastIndexOf(".")+1);
	    Mat source =Imgcodecs.imread(image.getName(), Imgcodecs.IMREAD_COLOR);
	    Mat gray = new Mat();
        Imgproc.cvtColor(source, gray, Imgproc.COLOR_BGR2GRAY);
        Mat thresh = new Mat();
        Imgproc.threshold(gray, thresh, 0, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);
        
        //horizontal line removal
        Mat horizontalKernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(40, 1));
        Mat removeHorizontal = new Mat();
        Imgproc.morphologyEx(thresh, removeHorizontal, Imgproc.MORPH_OPEN, horizontalKernel, new Point(-1, -1), 2);

        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(removeHorizontal, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Draw contours on the result image
        for (MatOfPoint contour : contours) {
            Imgproc.drawContours(source, contours, contours.indexOf(contour), new Scalar(197,200,197), 5);
        }
        
        Mat verticalKernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 40));
        Mat removeVertical = new Mat();
        Imgproc.morphologyEx(thresh, removeVertical, Imgproc.MORPH_OPEN, verticalKernel, new Point(-1, -1), 2);

        // Find contours
        List<MatOfPoint> contoursVertical = new ArrayList<>();
        Mat hierarchyVertical = new Mat();
        Imgproc.findContours(removeVertical, contoursVertical, hierarchyVertical, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Draw contours on the result image
        for (MatOfPoint contour : contoursVertical) {
            Imgproc.drawContours(source, contoursVertical, contoursVertical.indexOf(contour), new Scalar(197,200,197), 5);
        }

        
               // Save the processed image
        Imgcodecs.imwrite(output_path, source);
	    File output_image = new File(output_path);
	    
	    
	    return  output_image;

	    
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println("Error: " + e.getMessage());
	    return null;
	}


}
}
