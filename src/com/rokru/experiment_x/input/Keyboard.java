package com.rokru.experiment_x.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.rokru.experiment_x.Logger;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean [120];
	public boolean up, down, left, right;
	
	public static boolean paused = false;
	
	public void update() {
		up = keys[KeyEvent.VK_W] ||keys[KeyEvent.VK_UP];
		down = keys[KeyEvent.VK_S] ||keys[KeyEvent.VK_DOWN];
		left = keys[KeyEvent.VK_A] ||keys[KeyEvent.VK_LEFT];
		right = keys[KeyEvent.VK_D] ||keys[KeyEvent.VK_RIGHT];
	}
	
	public void keyPressed(KeyEvent e) {
		//Ignores key-presses during pause
		if(!paused)
			keys[e.getKeyCode()] = true;
		else if(paused && (e.getKeyCode() == KeyEvent.VK_ESCAPE)) 
			keys[KeyEvent.VK_ESCAPE] = true;
			
		if(keys[KeyEvent.VK_ESCAPE]){
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
