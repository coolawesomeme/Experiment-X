package com.rokru.experiment_x.entity.mob;

import com.rokru.experiment_x.Logger;
import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.graphics.Sprite;
import com.rokru.experiment_x.input.Keyboard;

public class Player extends Mob {
	
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	
	public Player (Keyboard input, String username) {
		this.input = input;
		sprite = Sprite.player_up;
	}
	
	public Player(int x, int y, Keyboard input, String username) {
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player_up;
	}
	
	public void update() {
		int xa = 0, ya = 0;
		if (anim < 7500) anim++;
		else anim = 0;
		if (input.up) ya--;
		if (input.down) ya++;
		if (input.left) xa--;
		if (input.right) xa++;
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			Logger.playerLogger.logInfo("COORDS: (" + x + ", " + y + ")");
			walking = true;
		} else {
			walking = false;
		}

	}
	
	public void render(Render screen) {
		if (dir == 0) {
			sprite = Sprite.player_up;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_up_1;
				} else {
					sprite = Sprite.player_up_2;
					
				}
			}
		}
		if (dir == 1) {
			sprite = Sprite.player_right;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_right_1;
				} else {
					sprite = Sprite.player_right_2;
					
				}
			}
		}
		if (dir == 2) {
			sprite = Sprite.player_down;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_left_1;
				} else {
					sprite = Sprite.player_left_2;
					
				}
			}
		}
		if (dir == 3) {
			sprite = Sprite.player_left;
			if (walking) {
				if (anim % 20 > 10) {
					sprite = Sprite.player_down_1;
				} else {
					sprite = Sprite.player_down_2;
					
				}
			}
		}		
		screen.renderPlayer(x - 16, y - 16, sprite);
		
	}
	
}
