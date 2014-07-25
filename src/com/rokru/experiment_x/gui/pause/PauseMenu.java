package com.rokru.experiment_x.gui.pause;

import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.rokru.experiment_x.ExperimentX;
import com.rokru.experiment_x.input.Keyboard;
import com.rokru.experiment_x.Logger;

public class PauseMenu{	
	
	private static JFrame menu;
	
	private static boolean menuOpen = false;
	public static boolean paused = false;

	public JButton options, quit, returntogame;
	
	private JLabel mainContentLabel = new JLabel();
	
	private PauseMenu(){
		menu = new JFrame();
		menu.setTitle("Pause Menu");
		menu.setIconImage(new ImageIcon(ExperimentX.class.getResource("/images/app_icon.png")).getImage());
		menu.setSize(new Dimension(550, 350));
		menu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		menu.getContentPane().add(mainContentLabel);
		addComponents();
		menu.setLocationRelativeTo(null);
		menu.setResizable(false);
		menu.setVisible(true);
		mainContentLabel.setLayout(null);
		
		menu.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == Keyboard.pause_key){
					closePauseMenu();
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
		title.setBounds(187, 15, 176, 40);
		mainContentLabel.add(title);
		
		returntogame = new JButton("Return to Game");
		returntogame.setBounds(40, 80,
				465, 50);
		mainContentLabel.add(returntogame);
		
		options = new JButton("Options");
		options.setBounds(40, 80 + 60 + 20,
				465, 50);
		mainContentLabel.add(options);
		
		quit = new JButton("Quit Game");
		quit.setBounds(40, 80 + (60 + 20) * 2,
				465, 50);
		mainContentLabel.add(quit);
		
		returntogame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "ReturnToGame");
				closePauseMenu();
			}
		});
		
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "Options");
				JOptionPane.showMessageDialog(null, new JLabel("Not yet implemented.", JLabel.CENTER), "Error", JOptionPane.ERROR_MESSAGE);
				options.setBackground(new Color(255, 89, 89));
				options.setForeground(new Color(255, 89, 89));
			}
		});
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "Quit");
				System.exit(0);
			}
		});
	}

	public static void openPauseMenu(){
		if(!menuOpen){
			new PauseMenu();
		}
	}
	
	public static void closePauseMenu(){
		if(menuOpen){
			menu.dispose();
			menuOpen = false;
			setPaused(false);
		}
	}

	public static void setPaused(boolean pause){
		paused = pause;
	}
	
}
