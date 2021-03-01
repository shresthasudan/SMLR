package smlr;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Comptest {
	
	private static int result;
	private static ArrayList diectionary;
	private static String seq=null;
	private static short code;
	private static short oldcode;
	private static short i;
	private static File file;
	private static FileOutputStream file_output ;
	private static DataOutputStream data_out;
	private static BufferedReader ReadH;
	private static String filename;
	
	public Comptest(String path) throws Exception {
		// TODO Auto-generated constructor stub
		//public static void main(String[] args) throws Exception {
			
	        filename=path;
	        boolean flag=true;
	        String[] name=path.split("\\.");
	        String[] testName=name[0].split("_");
	        for (String test : testName) {
	        	if(test.equals("Comp")){
		        	JOptionPane.showMessageDialog(null, "The text file is already Compressed!!");
		        	flag=false;
		        	
		        }
			}
	        if(flag){
	        creatediectionary();
	        //System.out.println("moving on");
	        compress(name[0]);
	       // System.out.println("main completed");
	        JOptionPane.showMessageDialog(null, "Sucessfully Compressed!!");
	        
	        }
	        else{
	        	return;
	        }
	//	}
	}
	static void compress(String name) throws Exception{
		
		
		
		//
		// disable the "All files" option.
		//
		String[] fname=name.split("\\\\");
		int count=-1;
		for(String filename:fname){
			//System.out.println(filename);
			count++;
		}
		System.out.println("name "+fname[count]);
		System.out.println("count "+count);
		String oname=fname[count];
		//String fpath=chooser.getSelectedFile();
		
	//	File file1 = chooser.getSelectedFile();
		String filename1= new MainPage().getPath();
		  System.out.println(filename1+oname+"_Comp.txt");
			file = new File(filename1+"\\"+oname+"_Comp.txt");
			//    
			file_output = new FileOutputStream(file);
		
		
        data_out = new DataOutputStream(file_output);
        
        try{
        	ReadH=new BufferedReader(new FileReader(filename));
        	oldcode=code=(short)ReadH.read();
        	seq=Character.toString((char)code);
        	while(seq!=null){
        		if(seq.length()>1){
        			result=searchindiectionary(seq);
        			if(result==-1){
        				addtodiectionary(seq);
        				seq=Character.toString(seq.charAt(seq.length()-1));
        				data_out.writeShort(oldcode);
        				oldcode=(short)((char)seq.charAt(0));
        				}
        			else{
        				oldcode=(short) result;
        			}
        			
        			
        		}
        		code=(short)ReadH.read();
        		seq+=Character.toString((char)code);
        		if(code==-1){
        			//endoffile
        			data_out.writeShort(oldcode);
        			//System.out.println("completed!!");
        			break;
        		}
        		
        		
        	}
        	
        	
        }finally{
        	ReadH.close();
            data_out.flush();
            data_out.close();
        	
        }
		
		
	}
	
	static void creatediectionary()
	{
		diectionary=new ArrayList();
		for(i=0;i<256;i++){
			diectionary.add((char)i);
		}
	}
	static int searchindiectionary(String seq1){
		for(int index=256;index<diectionary.size();index++){
			if(seq1.equals((String)diectionary.get(index)))
			return index;
		}
		
		return -1;
	}

	static void addtodiectionary(String seq1){
		diectionary.add(seq1);
	}
}
