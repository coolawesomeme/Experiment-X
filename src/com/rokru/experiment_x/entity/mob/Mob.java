package com.rokru.experiment_x.entity.mob;

import com.rokru.experiment_x.entity.Entity;
import com.rokru.experiment_x.graphics.Sprite;
import com.rokru.experiment_x.level.Level;

public abstract class Mob extends Entity {
	
	public Mob(Level level, String name) {
		super(level, name);
	}

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
			
	public void move(int xa, int ya) {
		if(xa != 0 && ya != 0){
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 2;
		if (ya > 0) dir = 3;
		if (ya < 0) dir = 0;
		
		if (!collision(xa, ya)) {
			x += xa;
			y += ya;
			tileX = x/16;
			tileY = y/16;
		}
	}
	
	public void update() {
	}
	
	private boolean collision(int xa, int ya) {
		boolean walkable = true;
		for(int c = 0; c < 4; c++){
			int xt = ((x+xa) + c % 2 * 12 - 5) / 16;
			int yt = ((y+ya) + c / 2 * 12 + 4) / 16;
			if(!level.getTile(xt, yt).walkable()) walkable = false;
		}
		return !walkable;
	}
	
	public void render() {
	}

}
