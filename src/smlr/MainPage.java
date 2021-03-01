package smlr;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.event.ActionEvent;

public class MainPage extends JFrame {

	private JPanel contentPane;
	private JLabel lblCompressionAndDecompression;
	private JButton btnCompress;
	private JButton btnDecompress;
	private JButton btnBack;
	private File[] files;
	private JButton btnBrowse;
	private static JFileChooser chooser = new JFileChooser();
	private static String choosertitle;
	private String outputPath; 
	private String filename1;
	private JLabel lblChooseOutputPath;
	private static int flag=0;

	/**
	 * Launch the application.
	 */
	
	
	public void open(File[] mfiles){
		files=mfiles;
		flag=0;
		//main(null);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainPage() {
		setVisible(true);
		setTitle("SMLR-A Data Compression And Decompression");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 425);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblCompressionAndDecompression());
		contentPane.add(getBtnCompress());
		contentPane.add(getBtnDecompress());
		contentPane.add(getBtnBack());
		contentPane.add(getBtnBrowse());
		contentPane.add(getLblChooseOutputPath());
	}
	private JLabel getLblCompressionAndDecompression() {
		if (lblCompressionAndDecompression == null) {
			lblCompressionAndDecompression = new JLabel("Compression and Decompression");
			lblCompressionAndDecompression.setFont(new Font("Tahoma", Font.ITALIC, 18));
			lblCompressionAndDecompression.setBounds(80, 61, 340, 39);
		}
		return lblCompressionAndDecompression;
	}
	private JButton getBtnCompress() {
		if (btnCompress == null) {
			btnCompress = new JButton("COMPRESS");
			btnCompress.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int count=0;
					String path; 
					for(File filer : files)
					{
						
						if(filer.isFile())
						{
							path=files[count].getAbsolutePath();
							//JOptionPane.showMessageDialog(null,path);
							String[] ext=path.split("\\.");
							if(ext[1].equals("jpg")){
								//JOptionPane.showMessageDialog(null,"image file");
								
								 new TestDCT(path);
								
							}
							else if(ext[1].equals("txt")){
								//JOptionPane.showMessageDialog(null,"text file");
							
								try {
									
									new Comptest(path);
									
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							count++;
							
							
						}
						
					}
					
					
					
				
					
				}
				
			});
			btnCompress.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnCompress.setBounds(122, 209, 134, 39);
		}
		return btnCompress;
	}
	private JButton getBtnDecompress() {
		if (btnDecompress == null) {
			btnDecompress = new JButton("DECOMPRESS");
			btnDecompress.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int count=0;
					String path; 
					for(File filer : files)
					{
						if(filer.isFile())
						{
							path=files[count].getAbsolutePath();
							//JOptionPane.showMessageDialog(null,path);
							String[] ext=path.split("\\.");
							if(ext[1].equals("txt")){
								//JOptionPane.showMessageDialog(null,"image file");
								
								try {
									new Decomptest(path);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
							
							count++;
						}
					}
					
				}
			});
			btnDecompress.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnDecompress.setBounds(377, 209, 127, 35);
		}
		return btnDecompress;
	}
	private JButton getBtnBack() {
		if (btnBack == null) {
			btnBack = new JButton("BACK");
			btnBack.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
					new HomePage2().open();
				}
			});
			btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnBack.setBounds(497, 311, 89, 23);
		}
		return btnBack;
	}
	private JButton getBtnBrowse() {
		if (btnBrowse == null) {
			btnBrowse = new JButton("Browse");
			btnBrowse.setFont(new Font("Tahoma", Font.BOLD, 14));
			btnBrowse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					chooser.setCurrentDirectory(new java.io.File("."));
					chooser.setDialogTitle(choosertitle);
					chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) { 
						  System.out.println("getCurrentDirectory(): " +  chooser.getCurrentDirectory());
						  System.out.println("getSelectedFile() : " +  chooser.getSelectedFile()); 
						    filename1=chooser.getSelectedFile().toString();
						    flag=1;
						}
						else {
						  System.out.println("No Selection ");
						  }
				}
			});
			btnBrowse.setBounds(377, 127, 121, 35);
		}
		return btnBrowse;
	}
	
	
	public String getPath(){
		dispose();
		if(flag==0){
			return "D:\\Boardway\\SmlrUpdate";
		}else
		return  chooser.getSelectedFile().toString();
	}
	private JLabel getLblChooseOutputPath() {
		if (lblChooseOutputPath == null) {
			lblChooseOutputPath = new JLabel("Choose output path");
			lblChooseOutputPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
			lblChooseOutputPath.setBounds(218, 130, 149, 28);
		}
		return lblChooseOutputPath;
	}

}
