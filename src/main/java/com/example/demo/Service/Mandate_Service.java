package com.example.demo.Service;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.ImgProcessing.Grayscale;
import com.example.demo.ImgProcessing.ImageRedaction;
import com.example.demo.ImgProcessing.ImageResize;
import com.example.demo.ImgProcessing.RemoveBoxes;
import com.example.demo.ImgProcessing.Threshold_Inc;
import com.example.demo.MultipartFileToFile.MultipartFileToFile;
import com.example.demo.OCR.EOCR;
import com.example.demo.OCR.TessOCR;
import com.example.demo.Response.Response;
import com.example.demo.SignDetector.Mandate_Sign_Detector;



@Service
public class Mandate_Service {

	public Response getImageToText(MultipartFile image) throws Exception{
		try{
			
			File init_image = MultipartFileToFile.convertMultipartFileToFile(image);
			System.out.println("Image Name : "+init_image.getName());
			//File grayscaled_image = Grayscale.convertToGrayscale(init_image);
			File boxes_removed_image = RemoveBoxes.removeBoxes(init_image);
			File thresholded = Threshold_Inc.threshold_inc(boxes_removed_image);
            File resized_image = ImageResize.imageResize(thresholded);
			
			
//			Map<String,String> mandate_form_details_eocr = EOCR.doEasyOCR(new FileInputStream(thresholded)); 
			Map<String,String> mandate_form_details  = TessOCR.doTessOCR(thresholded,thresholded);
			System.out.println("EasyOCR Results");

//			if(mandate_form_details_eocr!=null) {
//			for (Map.Entry<String,String> entry : mandate_form_details_eocr.entrySet())  
//            System.out.println("Key = " + entry.getKey() + 
//                    ", Value = " + entry.getValue()); 
//			}
			System.out.println("TessOCR Results");
			if(mandate_form_details == null)
			{
				System.out.println("No details from TessOCR");
			}
			else {
			for (Map.Entry<String,String> entry : mandate_form_details.entrySet())  
	            System.out.println("Key = " + entry.getKey() + 
	                    ", Value = " + entry.getValue()); 
			}
			
			
			 
			 init_image.delete();
			 boxes_removed_image.delete();
			 thresholded.delete();
        
			return new Response(200,"Image Reading Completed",mandate_form_details);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
}

//451,354,613,380
