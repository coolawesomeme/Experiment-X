package com.rokru.experiment_x.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.rokru.experiment_x.Logger;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean [120];
	public boolean up, down, left, right;
	public int up_key = KeyEvent.VK_W, 
			down_key = KeyEvent.VK_S, 
			left_key = KeyEvent.VK_A, 
			right_key = KeyEvent.VK_D;
	
	public static boolean paused = false;
	private int pause_key = KeyEvent.VK_ESCAPE;
	
	public void update() {
		up = keys[up_key];
		down = keys[down_key];
		left = keys[left_key];
		right = keys[right_key];
	}
	
	public void keyPressed(KeyEvent e) {
		//Ignores key-presses during pause
		if(!paused)
			keys[e.getKeyCode()] = true;
		else if(paused && (e.getKeyCode() == pause_key)) 
			keys[pause_key] = true;
			
		if(keys[pause_key]){
			if(!paused){
				paused = true;
				Logger.generalLogger.logInfo("Paused: " + paused);
			}else{
				paused = false;
				Logger.generalLogger.logInfo("Paused: " + paused);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	
	}

}
