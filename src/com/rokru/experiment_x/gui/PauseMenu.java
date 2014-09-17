package com.rokru.experiment_x.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

import com.rokru.experiment_x.ExperimentX;
import com.rokru.experiment_x.input.Keyboard;
import com.rokru.experiment_x.Logger;

public class PauseMenu{	
	
	private static JFrame menu;
	
	private static boolean menuOpen = false;
	public static boolean paused = false;
	public static int menuID = 1;
	
	public JButton options, quit, returntogame;
	
	private JLabel mainContentLabel = new JLabel();
	
	private PauseMenu(){
		setPaused(true);
		menu = new JFrame();
		menu.setTitle("Pause Menu");
		menu.setIconImage(new ImageIcon(ExperimentX.class.getResource("/images/app_icon.png")).getImage());
		menu.setSize(new Dimension(550, 350));
		menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		menu.getContentPane().add(mainContentLabel);
		addComponents();
		menu.setLocationRelativeTo(null);
		menu.setResizable(false);
		menu.setUndecorated(true);
		menu.setBackground(new Color((float)0/255, (float)0/255, (float)0/255, 0.6f));
		menu.setVisible(true);
		mainContentLabel.setLayout(null);
		
		menu.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == Keyboard.pause_key){
					closePauseMenu(0);
				}
			}			
		});
		menu.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
				 if(paused && menuOpen){
					setPaused(false);
				 	menuOpen = false;
				 }
			  }
		});
		menuOpen = true;
	}
	
	private void addComponents() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JLabel title = new JLabel(new ImageIcon(ExperimentX.class.getResource("/images/pause_title.png")));
		title.setBounds(187, 35, 176, 40);
		mainContentLabel.add(title);
		
		returntogame = new JButton("Return to Game");
		returntogame.setBounds(40, 100,
				465, 50);
		mainContentLabel.add(returntogame);
		returntogame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		options = new JButton("Options");
		options.setBounds(40, 100 + 60 + 20,
				465, 50);
		mainContentLabel.add(options);
		options.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		quit = new JButton("Quit Game");
		quit.setBounds(40, 100 + (60 + 20) * 2,
				465, 50);
		mainContentLabel.add(quit);
		quit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		returntogame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "ReturnToGame");
				closePauseMenu(0);
			}
		});
		
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "Options");
				OptionsMenu.openOptionsMenu(0);
				closePauseMenu(1);
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "Quit");
				ExperimentX.endGameEvent();
				System.exit(0);
			}
		});
	}

	public static void openPauseMenu(){
		if(!menuOpen){
			new PauseMenu();
			ExperimentX.setCurrentMenu(menuID);
		}
	}
	
	public static void closePauseMenu(int i){
		if(menuOpen){
			menu.dispose();
			menuOpen = false;
			if(i == 0){
				setPaused(false);
				ExperimentX.pauseMenuClosed();
				ExperimentX.setCurrentMenu(0);
			}else if(i == 2){
				setPaused(false);
				ExperimentX.pauseMenuClosed();
				ExperimentX.setCurrentMenu(0);
			}
		}
	}

	public static void setPaused(boolean pause){
		paused = pause;
	}
	
	public static void requestFocus(){
		if(menuOpen)
			menu.requestFocus();
	}
	
}
