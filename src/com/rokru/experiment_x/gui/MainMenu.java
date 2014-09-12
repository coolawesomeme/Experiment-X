package com.rokru.experiment_x.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.rokru.experiment_x.ExperimentX;
import com.rokru.experiment_x.Logger;

public class MainMenu extends JPanel implements Runnable{
	private static final long serialVersionUID = 1L;
	private ExperimentX x;
	private JFrame frame;

	private Image[] pictures = new Image[2];
	private int current = 0;
	private Thread runner;
	private int pause = 250;
	
	public MainMenu(ExperimentX x, JFrame frame) {
		for(int i = 0; i < pictures.length; i++){
			pictures[i] = new ImageIcon(this.getClass().getResource("/images/main_menu_bg_" + (i+1) + ".png")).getImage();
		}
		
		this.frame = frame;
		this.x = x;
		
		Dimension size = new Dimension(ExperimentX.width * ExperimentX.scale, ExperimentX.height * ExperimentX.scale);
		setPreferredSize(size);
		setSize(size);
		
		this.setBackground(new Color(0xff003E85));
		this.setLayout(null);
		
		addComponents();
		frame.add(this);
		
		start();
		
		Logger.generalLogger.logInfo("Main Menu running.");
	}

	private void addComponents() {
		JButton play = new JButton("Play!");
		play.setBounds(getWidth()/2 - 120/2, 385, 140, 60);
		play.setUI(new XButtonUI());
		play.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(play);
		
		JButton exit = new JButton("Exit");
		exit.setBounds(getWidth() - 80 - 120, 385, 120, 60);
		exit.setUI(new XButtonUI());
		exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(exit);
		
		JButton options = new JButton("Options");
		options.setBounds(80, 385, 120, 60);
		options.setUI(new XButtonUI());
		options.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		add(options);
		
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "Play");
				startGame();
			}
		});
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "Exit");
				System.exit(0);
			}
		});
		
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logger.generalLogger.logAction("button", "Options");
				OptionsMenu.openOptionsMenu(1);
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (pictures[current] != null)
			g.drawImage(pictures[current], 0, 0, this);
		g.drawImage(new ImageIcon(this.getClass().getResource("/images/ex_x_logo.png")).getImage(), getWidth()/2 - 600/2, 75, 600, 200, null);
	}

	public void start() {
		if (runner == null) {
			runner = new Thread(this, "BackgroundAnimatedThread");
			runner.start();
		}
	}
	
	public void run() {
		while (runner == Thread.currentThread()) {
			repaint();
			current++;
			if (current >= pictures.length)
				current = 0;
			try {
				Thread.sleep(pause);
			} catch (InterruptedException e) { }
		}
	}
	
	public void stop() {
		if (runner != null) {
			runner = null;
		}
	}
	
	private void startGame() {
		if(!ExperimentX.titleBar){
			JPanel j = new JPanel();
			j.setBounds(ExperimentX.borderWidth, ExperimentX.borderHeight, ExperimentX.width*ExperimentX.scale, ExperimentX.height*ExperimentX.scale);
			j.setBackground(new Color(0xff002747));
			j.add(x);
			j.setLayout(null);
			j.setVisible(true);
			frame.setSize(new Dimension(ExperimentX.width*ExperimentX.scale + 2*ExperimentX.borderWidth, ExperimentX.height*ExperimentX.scale + 2*ExperimentX.borderHeight));
			frame.add(j);
		}else{
			frame.add(x);
		}
		x.start();
		frame.remove(this);
		stop();
	}
	
	public void restart(){
		start();
	}

}
