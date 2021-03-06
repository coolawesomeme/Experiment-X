package com.rokru.experiment_x.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.rokru.experiment_x.ExperimentX;
import com.rokru.experiment_x.Logger;
import com.rokru.experiment_x.gui.OptionsMenu;
import com.rokru.experiment_x.gui.PauseMenu;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean [KeyEvent.KEY_LAST];
	public boolean up, down, left, right;
	public static int up_key = KeyEvent.VK_W, 
			down_key = KeyEvent.VK_S, 
			left_key = KeyEvent.VK_A, 
			right_key = KeyEvent.VK_D;
	public static int pause_key = KeyEvent.VK_ESCAPE;
	public static int debug_key = KeyEvent.VK_F3;
	public static int hidegui_key = KeyEvent.VK_F1;
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
			if(e.getKeyCode() <= KeyEvent.KEY_LAST){
				keys[e.getKeyCode()] = true;
				mostRecentKey = e.getKeyCode();
				if(e.getKeyCode() == debug_key)
					ExperimentX.debugMode(!ExperimentX.debug);
				else if(e.getKeyCode() == hidegui_key)
					ExperimentX.hideGui(!ExperimentX.hidegui);
			}
		}else if(e.getKeyCode() == pause_key && !PauseMenu.paused){
			keys[pause_key] = true;
			keys[mostRecentKey] = false;
			Logger.generalLogger.logAction("key", "Pause Key (Key " + pause_key + ")");
			PauseMenu.setPaused(true);
			ExperimentX.setCurrentMenu(PauseMenu.menuID);
		}else if(ExperimentX.currentMenu == PauseMenu.menuID){
			Logger.generalLogger.logAction("key", "Key " + e.getKeyCode() + "(while paused)");
			PauseMenu.requestFocus();
		}else if(ExperimentX.currentMenu == OptionsMenu.menuID){
			Logger.generalLogger.logAction("key", "Key " + e.getKeyCode() + "(while paused)");
        	OptionsMenu.requestFocus();
        }
	}

	public void keyReleased(KeyEvent e) {
		try{
			keys[e.getKeyCode()] = false;
		}catch(Exception ex){
			Logger.generalLogger.logError("Invalid key released! ID: " + e.getKeyCode() + " (" + e.getKeyChar() + ")", 1);
		}
	}

	public void keyTyped(KeyEvent e) {
	
	}
	
}
