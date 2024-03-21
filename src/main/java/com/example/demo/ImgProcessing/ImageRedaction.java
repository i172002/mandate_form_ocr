package com.example.demo.ImgProcessing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;




public class ImageRedaction {
    public static ArrayList<Rectangle> imageRedaction(Rectangle coordinates,File init_image) {
        // Load the original image
        BufferedImage originalImage = null;
        ArrayList<Rectangle> signCoordinates = new ArrayList<>();
        try {
        	//input_image sent via API call(plain_image)
            originalImage = ImageIO.read(new File(init_image.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
           
        }
        
        int originalX = coordinates.x+1200;
	    int originalY = coordinates.y;
	    int originalWidth = (int)coordinates.width;
	    int originalHeight = 150;
        maskRectangle(originalImage, new Rectangle(originalX,originalY,2000,originalHeight));
        signCoordinates.add(new Rectangle(originalX,originalY,2000,originalHeight));

        
//        int originalX = coordinates.x+1300;
//        int originalY = coordinates.y;
//        int originalWidth = (int)coordinates.width;
//        int originalHeight = 150;
//        for(int i = 0;i<3;i++)
//        {
//            maskRectangle(originalImage, new Rectangle(originalX,originalY,560,originalHeight));
//            signCoordinates.add(new Rectangle(originalX,originalY,560,originalHeight));
//            originalX+=760;
//        }
        
        

        
        // Save the modified image
        try {
        	String image_name = init_image.getName();
            File redacted_output = new File("masked-"+image_name+"."+image_name.substring(image_name.lastIndexOf(".")+1));
            ImageIO.write(originalImage, image_name.substring(image_name.lastIndexOf(".")+1), redacted_output);
            return signCoordinates;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //apply the black rectangle in the given coordinates 
	    private static void maskRectangle(BufferedImage image, Rectangle rect) {
	        int redactX = rect.x;
	        int redactY = rect.y;
	        int redactWidth = (int) rect.getWidth();
	        int redactHeight = (int) rect.getHeight();
	        System.out.println(redactX+" "+redactY+" "+redactWidth+" "+redactHeight);
	
	        Graphics2D g = image.createGraphics();
	        g.setColor(Color.BLACK);
	        g.fillRect(redactX, redactY, redactWidth, redactHeight);
	        g.dispose();
	    }
}
