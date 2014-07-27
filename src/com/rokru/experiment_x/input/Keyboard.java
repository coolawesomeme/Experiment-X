package com.rokru.experiment_x.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.rokru.experiment_x.Logger;
import com.rokru.experiment_x.gui.pause.PauseMenu;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean [120];
	public boolean up, down, left, right;
	public static int up_key = KeyEvent.VK_W, 
			down_key = KeyEvent.VK_S, 
			left_key = KeyEvent.VK_A, 
			right_key = KeyEvent.VK_D;
	public static int pause_key = KeyEvent.VK_ESCAPE;
	private int mostRecentKey;
	
	public void update() {
		up = keys[up_key];
		down = keys[down_key];
		left = keys[left_key];
		right = keys[right_key];
	}
	
	public void keyPressed(KeyEvent e) {
		//Ignores key-presses during pause
		if(!PauseMenu.paused && e.getKeyCode() != pause_key){
			keys[e.getKeyCode()] = true;
			mostRecentKey = e.getKeyCode();
		}else if(e.getKeyCode() == pause_key && !PauseMenu.paused){
			keys[pause_key] = true;
			keys[mostRecentKey] = false;
			Logger.generalLogger.logAction("key", "Pause Key (Key " + pause_key + ")");
			PauseMenu.setPaused(true);
		}else if(PauseMenu.paused){
			PauseMenu.requestFocus();
		}
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	
	}
	
}
