package smlr;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;
import java.io.*;

public class TestDCT {
	
public TestDCT(String path){
	

    	String[] name=path.split("\\.");
        
            try {
                // Read file
            	File input = new File(name[0]+".jpg");
                BufferedImage inputImage = ImageIO.read(input);
                System.out.println(" DONE");

                // Compress with DCT
                System.out.print("DCT...");
                byte[] dct = DCT.compress(inputImage);
                System.out.println(" DONE");

                // Decompress with DCT
                System.out.print("Inverse DCT...");
                BufferedImage outputImage = DCT.decompress(dct, inputImage.getWidth(), inputImage.getHeight());
                System.out.println(" DONE");

                // Save image
                
                System.out.print("Save image...");
        		
        		//
        		// disable the "All files" option.
        		//
        		
                String[] fname=name[0].split("\\\\");
        		int count=-1;
        		for(String filename:fname){
        			//System.out.println(filename);
        			count++;
        		}
        		System.out.println("name "+fname[count]);
        		System.out.println("count "+count);
        		String oname1=fname[count];
        		String[] subname=oname1.split("_");
                String oname=subname[0];
                	String filename1= new MainPage().getPath();
                	ImageIO.write(outputImage, "JPG", new File(filename1+"\\"+oname+"_com.jpg"));
                	System.out.println(filename1+"\\"+oname+"_com.jpg");
                
                System.out.println(" DONE");
                JOptionPane.showMessageDialog(null, "Sucessfully Compressed!!");
              

                // print info
                //System.out.println("Original size: " + (inputImage.getWidth() * inputImage.getHeight() * 3) / 1024);
               // System.out.println("Compressed size: " + dct.length / 1024);
                //System.out.println("Decompressed size: " + (outputImage.getWidth() * outputImage.getHeight() * 3) / 1024);

                System.out.println();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
