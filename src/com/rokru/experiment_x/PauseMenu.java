package com.rokru.experiment_x;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.rokru.experiment_x.input.Keyboard;

public class PauseMenu{	
	
	private static JFrame options;
	
	private static boolean menuOpen = false;

	private JLabel mainContentLabel = new JLabel();
	
	private PauseMenu(){
		options = new JFrame();
		options.setTitle("Options");
		options.setIconImage(new ImageIcon(ExperimentX.class.getResource("/images/app_icon.png")).getImage());
		options.setSize(new Dimension(550, 350));
		options.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		options.getContentPane().add(mainContentLabel);
		options.setLocationRelativeTo(null);
		options.setResizable(false);
		options.setVisible(true);
		mainContentLabel.setLayout(null);
		
		options.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == Keyboard.pause_key){
					closePauseMenu();
					Keyboard.setPaused(false);
					menuOpen = false;
				}
			}			
		});
		options.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
				 Keyboard.setPaused(false);
				 menuOpen = false;
			  }
		});
		
		addComponents();
		menuOpen = true;
	}
	
	private void addComponents() {
		
	}

	public static void openPauseMenu(){
		if(!menuOpen){
			new PauseMenu();
		}
	}
	
	public static void closePauseMenu(){
		if(menuOpen){
			options.dispose();
			menuOpen = false;
			Keyboard.setPaused(false);
		}
	}

}
