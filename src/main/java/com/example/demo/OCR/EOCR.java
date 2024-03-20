package com.example.demo.OCR;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import cn.easyproject.easyocr.EasyOCR;

public class EOCR {
	//FirstName and LastName Regex

	
    public static Map<String, String> doEasyOCR(InputStream image) {
    		    Map<String,String> mandate_form_details = new HashMap<>();
		EasyOCR easyOCR = new EasyOCR("C:\\Users\\intern-infant\\AppData\\Local\\Programs\\Tesseract-OCR\\tesseract.exe",EasyOCR.OPTION_LANG_ENG);

        String accountNumber = null,IFSC = null;
        String text_from_image = null;
		try {
			text_from_image = easyOCR.discernAndAutoCleanImage(image,4);
			System.out.println("EasyOCR Extracted Text: \n\n"+text_from_image+"\n\n");
			String details[] = text_from_image.split("\n");
			for(String i : details)
			{
				i = i.toLowerCase();
				if(i.contains("bank") && i.contains("number")) {
				    accountNumber = i.replaceAll("[^0-9]", "");
				    System.out.println(accountNumber);
				}
				if(i.contains("ifsc")) {
					
					String IFSC_details[] = i.replaceAll("[^a-zA-Z0-9]", "").split("ifsc");
					if(IFSC_details.length>1)
					{
						IFSC = IFSC_details[1];
					}
					
					System.out.println(IFSC);
				}
			}
			mandate_form_details.put("accountNumber",accountNumber);
			mandate_form_details.put("IFSC",IFSC);


			return mandate_form_details;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		        return null;

	}


}
