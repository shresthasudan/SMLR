package smlr;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;

public class HomePage2 extends JFrame {

	private JPanel contentPane;
	private JFileChooser c = new JFileChooser();
	private String path;
	private File[] files;
	//private File[] file;
	private JPanel panel;
	private JButton btnNewButton;
	private JLabel lblChooseFileTo;
	private JLabel lblSmlr;
	private JButton btnExit;

	/**
	 * Launch the application.
	 */
	
	public void open() {
		main(null);
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomePage2 frame = new HomePage2();
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
	public HomePage2() {
		setTitle("SMLR : Text and Image Compression and Decompression");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 548, 374);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getPanel(), BorderLayout.CENTER);
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getBtnNewButton());
			panel.add(getLblChooseFileTo());
			panel.add(getLblSmlr());
			panel.add(getBtnExit());
		}
		return panel;
	}
	private JButton getBtnNewButton() {
		if (btnNewButton == null) {
			btnNewButton = new JButton("BROWSE");
			btnNewButton.setFont(new Font("Tahoma", Font.ITALIC, 18));
			btnNewButton.setBounds(293, 194, 122, 31);
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					c.setMultiSelectionEnabled(true);
					int rVal =c.showOpenDialog(null);
					if(rVal == JFileChooser.APPROVE_OPTION) {
						files=c.getSelectedFiles();
						}
					else{
						
						return;
					}
					new MainPage().open(files);
					dispose();
					//boolean check=true;
							//System.out.println(count);
						
						
					
					//System.out.println(count);
				//path=files[0].getAbsolutePath();
				/*String path1=files[1].getAbsolutePath();
					JOptionPane.showMessageDialog(null,path);
					String[] ext=path.split("\\.");
					if(ext[1].equals("jpg")){
						//JOptionPane.showMessageDialog(null,"image file");
						dispose();
						new ImageFrame().open(path);
					}
					else if(ext[1].equals("txt")){
						//JOptionPane.showMessageDialog(null,"text file");
						dispose();
						new TextFrame().open(path);
					}
					JOptionPane.showMessageDialog(null,path1);
					String[] ext1=path1.split("\\.");
					if(ext1[1].equals("jpg")){
						//JOptionPane.showMessageDialog(null,"image file");
						dispose();
						new ImageFrame().open(path1);
					}
					else if(ext1[1].equals("txt")){
						//JOptionPane.showMessageDialog(null,"text file");
						dispose();
						new TextFrame().open(path1);
					}*/
				}
			});
		}
		return btnNewButton;
	}

	



	private JLabel getLblChooseFileTo() {
		if (lblChooseFileTo == null) {
			lblChooseFileTo = new JLabel("Choose File to be comprressed or decompressed");
			lblChooseFileTo.setFont(new Font("Tahoma", Font.PLAIN, 16));
			lblChooseFileTo.setBounds(73, 107, 359, 45);
		}
		return lblChooseFileTo;
	}
	private JLabel getLblSmlr() {
		if (lblSmlr == null) {
			lblSmlr = new JLabel("SMLR ");
			lblSmlr.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
			lblSmlr.setBounds(201, 37, 132, 41);
		}
		return lblSmlr;
	}
	private JButton getBtnExit() {
		if (btnExit == null) {
			btnExit = new JButton("EXIT");
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnExit.setFont(new Font("Tahoma", Font.PLAIN, 20));
			btnExit.setBounds(380, 272, 89, 23);
		}
		return btnExit;
	}
}
