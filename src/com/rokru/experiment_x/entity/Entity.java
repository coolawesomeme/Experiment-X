package com.rokru.experiment_x.entity;

import java.util.Random;

import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.level.Level;

public abstract class Entity {
	
	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void update() {
	}
	
	public void render (Render screen) {
	}
	
	public void remove() {
		//Remove from level
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}

}
