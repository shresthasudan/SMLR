package smlr;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Decomptest {
	
	public static String file_name;
	public static ArrayList diectionary;
	public static File file;
	public static FileInputStream file_input;
	public static DataInputStream data_in;
	public static short code,oldcodde, result;
	public static String seq,next_seq,todiec;
	public static BufferedWriter WriteH;
	
	public Decomptest(String path) throws Exception {
		file_name=path;
		boolean flag= false;
		String name[]=path.split("\\.");
		String[] testName=name[0].split("_");
		for (String test : testName) {
			if(test.equals("Comp")){
				flag=true;
				break;
			}
		}
		if(flag){
		creatediectionary();
		decompress(name[0]);
		JOptionPane.showMessageDialog(null, "Sucessfully Decompressed!!");
		}else{
			JOptionPane.showMessageDialog(null, "The text file is in decompressed form");
			//new HomePage2().open();
		}
		
	}
	


	public static void decompress(String name)throws Exception{
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
		
		String filename1=  new MainPage().getPath();
		  System.out.println(filename1+oname+"_Comp.txt");
			file = new File(filename1+"\\"+oname+"_Comp.txt");
			//    
			file_input = new FileInputStream(file_name);
		
		
		String[] orgName=name.split("\\_");
		file=new File(orgName[0]+"1.txt");
		//file_input= new FileInputStream(file_name);
		data_in = new DataInputStream(file_input);
		
		try {
			seq=next_seq=todiec=null;
			WriteH = new BufferedWriter(new FileWriter(file));
			code= data_in.readShort();
			seq = Character.toString((char)code);
			while(data_in.available()>0){
				code= data_in.readShort();
				next_seq = getTheString(code);
				result=searchIn_diectionary(seq+next_seq);
				 if(result!=-1) {
                     seq+=next_seq;
                     todiec=null;
                 }else{
                	 if(!next_seq.equals("!")) {
                         todiec=seq+next_seq.substring(0,1);
                         addTo_diectionary(todiec);
                         WriteH.write(seq);
                         seq=next_seq;
                 }else{
                	 todiec=seq+seq.substring(0,1);
                     addTo_diectionary(todiec);
                     WriteH.write(seq);
                      
                     seq=next_seq=todiec;
                 }
				
                 }
				 
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(seq!=null){
				WriteH.write(seq);
			}
			e.printStackTrace();
		}
			  try {
				WriteH.flush();
				WriteH.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
              
		
		
		
	}
	public static String getTheString(short code){
		if(code<256){
			return Character.toString((char)code);
		}
		else if(code<diectionary.size()){
			return (String) diectionary.get(code);
		}
		return "!";
	}
	
	public static short searchIn_diectionary(String sequ){
		for(short i =256;i<diectionary.size();i++){
			if(sequ.equals((String)diectionary.get(i))){
				return i;
			}
		}
		return -1;
	}
	public static void addTo_diectionary(String sequ){
		diectionary.add(sequ);
	}
	public static void creatediectionary(){
		short i;
		diectionary=new ArrayList();
		for(i=0;i<256;i++){
			diectionary.add((char)i);
		}
	}
}
