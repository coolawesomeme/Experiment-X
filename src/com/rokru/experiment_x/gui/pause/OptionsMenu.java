package com.rokru.experiment_x.gui.pause;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import com.rokru.experiment_x.ExperimentX;
import com.rokru.experiment_x.Logger;

public class OptionsMenu{	
	
	private static JFrame menu;
	
	public static boolean menuOpen = false;

	public JButton options, quit, returntopause;
	
	private JLabel mainContentLabel = new JLabel();
	
	private OptionsMenu(){
		menuOpen = true;
		menu = new JFrame();
		menu.setTitle("Options Menu");
		menu.setIconImage(new ImageIcon(ExperimentX.class.getResource("/images/app_icon.png")).getImage());
		menu.setSize(new Dimension(ExperimentX.width*ExperimentX.scale, ExperimentX.height*ExperimentX.scale));
		menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		menu.getContentPane().add(mainContentLabel);
		addComponents();
		menu.setLocationRelativeTo(null);
		menu.setUndecorated(true);
		menu.setResizable(false);
		menu.setVisible(true);
		mainContentLabel.setLayout(null);
		
		menu.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
				 if(menuOpen){
					menuOpen = false;
				 }
			  }
		});
	}
	
	private void addComponents() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		returntopause = new JButton("Back to Pause Menu");
		returntopause.setBounds(40, 100,
				820, 50);
		mainContentLabel.add(returntopause);
		
		returntopause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "ReturnToPause");
				closeOptionsMenu();
			}
		});
	}

	public static void openOptionsMenu(){
		if(!menuOpen){
			new OptionsMenu();
		}
	}
	
	public static void closeOptionsMenu(){
		if(menuOpen){
			menu.dispose();
			menuOpen = false;
		}
	}
	
	public static void requestFocus(){
		if(menuOpen)
			menu.requestFocus();
	}
}
