package com.rokru.experiment_x.gui.pause;

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
		menu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
		
		final JCheckBox titleBarBox = new JCheckBox("Title Bar");
		titleBarBox.setBounds(140, 60, 100, 100);
		titleBarBox.setSelected(Boolean.parseBoolean(Config.getProperty("titleBar")));
		mainContentLabel.add(titleBarBox);
		
		returntopause = new JButton("Back to Pause Menu");
		returntopause.setBounds(40, ExperimentX.height*ExperimentX.scale - 50 - 20,
				820, 50);
		mainContentLabel.add(returntopause);
		
		final JLabel warning = new JLabel("Warning: In order for this to take effect, you must restart the game.", JLabel.CENTER);
		warning.setFont(ExperimentX.getDefaultFont(Font.PLAIN, 14));
		warning.setBounds(menu.getWidth()/2 - 423/2, ExperimentX.height*ExperimentX.scale - 50 - 20 - 30, 423, 20);
		warning.setForeground(Color.RED);
		warning.setVisible(false);
		mainContentLabel.add(warning);
		
		returntopause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "ReturnToPause");
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

	public static void openOptionsMenu(){
		if(!menuOpen){
			new OptionsMenu();
			ExperimentX.setCurrentMenu(menuID);
		}
	}
	
	public static void closeOptionsMenu(){
		if(menuOpen){
			menu.dispose();
			ExperimentX.setCurrentMenu(PauseMenu.menuID);
			menuOpen = false;
		}
	}
	
	public static void requestFocus(){
		if(menuOpen)
			menu.requestFocus();
	}
}
