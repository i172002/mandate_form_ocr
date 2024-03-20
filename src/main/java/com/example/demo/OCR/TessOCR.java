package com.example.demo.OCR;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.ImgProcessing.ImageRedaction;
import com.example.demo.SignDetector.Mandate_Sign_Detector;

import cn.easyproject.easyocr.EasyOCR;
import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;

public class TessOCR {
	
	 public static Map<String, String> doTessOCR(File image,File signDetectingImage) {
		    Map<String,String> mandate_form_details = new HashMap<>();
		    String isSigned1="no",isSigned2="no",isSigned3="no";
		    BufferedImage bi=null;
			try {
				bi = ImageIO.read(image);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		 String accountNumber = null,IFSC = null;float accountNumberConf = 0,IFSCConf = 0;
		 
		
			try {

				ITesseract ts = new Tesseract();
				ts.setDatapath("D:\\softwares\\Tess4J\\tessdata");
		        ts.setLanguage("eng");
//		        ts.setPageSegMode(1);
				System.out.println("TessOCR Extracted Text: \n\n");
                
		        List<Word> words = ts.getWords(bi, ITessAPI.TessPageIteratorLevel.RIL_TEXTLINE);
		        Rectangle coordinates = new Rectangle();
				
				for (Word word : words) {
					
				    System.out.println(word.getText().toLowerCase());
				    String i = word.getText().toLowerCase();
					if((i.contains("bank") && i.contains("number")) && (accountNumber==null)) {
					    accountNumber = i.split("number")[1].replaceAll("[^0-9]", "");
					    accountNumberConf = word.getConfidence();
					    System.out.println("Account number is "+accountNumber);
					}
					if(i.contains("ifsc")) {
						String IFSC_details[] = i.replaceAll("[^a-zA-Z0-9]", "").split("ifsc");
						if(IFSC_details.length>1)
						{
							
							String ifsc_split= IFSC_details[1];
							for(int iterator = ifsc_split.length()-1;iterator>=0;iterator--) {
								
								if(Character.isDigit(ifsc_split.charAt(iterator))) {
									IFSC = ifsc_split.substring(0,iterator+1).toUpperCase();
									break;
								}
							}
						}
					    IFSCConf = word.getConfidence();			
						System.out.println(IFSC);
						
//						IFSC = i.replaceAll("[^a-zA-Z0-9]", "").split("ifsc")[1];
//						IFSC = IFSC.substring(0,11).toUpperCase();
//						IFSCConf = word.getConfidence();
//						System.out.println(IFSC);
					}
					if(i.contains("from")) {
						System.out.println("From bounding box: "+word.getBoundingBox());
						coordinates = word.getBoundingBox();
						
						
					}
					
				}
				System.out.println(accountNumber);
				
				mandate_form_details.put("accountNumber",accountNumber);
				mandate_form_details.put("IFSC",IFSC);
				mandate_form_details.put("accountNumberConf",String.valueOf(accountNumberConf));
				mandate_form_details.put("IFSCConf",String.valueOf(IFSCConf));
				
				ArrayList<Rectangle> signCoordinates = ImageRedaction.imageRedaction(coordinates, signDetectingImage);
				System.out.println("Inside TessOCR");
				
			    isSigned1 = Mandate_Sign_Detector.isSignatureBox(signDetectingImage,  signCoordinates.get(0));	
			    isSigned2 = Mandate_Sign_Detector.isSignatureBox(signDetectingImage, signCoordinates.get(1));	
			    isSigned3 = Mandate_Sign_Detector.isSignatureBox(signDetectingImage, signCoordinates.get(2));
				
                
				
				mandate_form_details.put("isSigned1",isSigned1);
				mandate_form_details.put("isSigned2",isSigned2);
				mandate_form_details.put("isSigned3",isSigned3);
				System.out.println("The size is "+mandate_form_details.size());

				return mandate_form_details;
				

			}catch(Exception e )
			{
				e.printStackTrace();
				return mandate_form_details;
			}
	 }
}

	
	

