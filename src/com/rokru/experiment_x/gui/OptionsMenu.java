package com.rokru.experiment_x.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import com.rokru.experiment_x.Config;
import com.rokru.experiment_x.ExperimentX;
import com.rokru.experiment_x.Logger;

public class OptionsMenu{	
	
	private static JFrame menu;
	
	public static boolean menuOpen = false;

	private static int openType = 0;
	
	public JButton options, quit, returntopause;
	public static int menuID = 2;
	
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
		mainContentLabel.setLayout(null);
		JLabel title = new JLabel(new ImageIcon(ExperimentX.class.getResource("/images/options_title.png")));
		title.setBounds(menu.getWidth()/2 - 249/2, 35, 249, 40);
		mainContentLabel.add(title);
		
		Config.reload();
		
		final JCheckBox guiBarBox = new JCheckBox("Gui Bar");
		guiBarBox.setBounds(40, 60, 60, 100);
		guiBarBox.setSelected(Boolean.parseBoolean(Config.getProperty("guiBar")));
		mainContentLabel.add(guiBarBox);
		guiBarBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		final JCheckBox titleBarBox = new JCheckBox("Title Bar");
		titleBarBox.setBounds(140, 60, 100, 100);
		titleBarBox.setSelected(Boolean.parseBoolean(Config.getProperty("titleBar")));
		mainContentLabel.add(titleBarBox);
		titleBarBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		returntopause = new JButton("Back to Previous Menu");
		returntopause.setBounds(40, ExperimentX.height*ExperimentX.scale - 50 - 20,
				820, 50);
		mainContentLabel.add(returntopause);
		returntopause.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		final JLabel warning = new JLabel("Warning: In order for this to take effect, you must restart the game.", JLabel.CENTER);
		warning.setFont(ExperimentX.getDefaultFont(Font.PLAIN, 14));
		warning.setBounds(menu.getWidth()/2 - 423/2, ExperimentX.height*ExperimentX.scale - 50 - 20 - 30, 423, 20);
		warning.setForeground(Color.RED);
		warning.setVisible(false);
		mainContentLabel.add(warning);
		
		returntopause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "ReturnToPrevious");
				Config.setValue("guiBar", Boolean.toString(guiBarBox.isSelected()));
				Config.setValue("titleBar", Boolean.toString(titleBarBox.isSelected()));
				closeOptionsMenu();
			}
		});
		
		titleBarBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				warning.setVisible(true);
			}
		});
	}

	public static void openOptionsMenu(int type){
		if(!menuOpen){
			openType = type;
			if(type == 0){
				ExperimentX.setCurrentMenu(menuID);
			}
			new OptionsMenu();
		}
	}
	
	public static void closeOptionsMenu(){
		if(menuOpen){
			menu.dispose();
			if(openType == 0)
				ExperimentX.setCurrentMenu(PauseMenu.menuID);
			menuOpen = false;
		}
	}
	
	public static void requestFocus(){
		if(menuOpen)
			menu.requestFocus();
	}
}
