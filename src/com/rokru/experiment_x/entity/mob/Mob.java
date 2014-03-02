package com.rokru.experiment_x.entity.mob;

import com.rokru.experiment_x.entity.Entity;
import com.rokru.experiment_x.graphics.Sprite;
import com.rokru.experiment_x.level.Level;

public abstract class Mob extends Entity {
	
	public Mob(Level level) {
		super(level);
	}

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
			
	public void move(int xa, int ya) {
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 2;
		if (ya > 0) dir = 3;
		if (ya < 0) dir = 0;
		
		if (!collision()) {
			x += xa;
			y += ya;
		}
	}
	
	public void update() {
	}
	
	private boolean collision() {
		return false;
	}
	
	public void render() {
	}

}
