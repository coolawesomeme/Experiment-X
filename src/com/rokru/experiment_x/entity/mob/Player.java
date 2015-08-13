package com.rokru.experiment_x.entity.mob;

import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.graphics.Sprite;
import com.rokru.experiment_x.gui.PauseMenu;
import com.rokru.experiment_x.input.Keyboard;
import com.rokru.experiment_x.level.Coordinates;
import com.rokru.experiment_x.level.Level;

public class Player extends Mob {
	
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private boolean walking = false;
	
	public Player (Level level, Keyboard input, String name) {
		super(level, name);
		this.input = input;
		sprite = Sprite.player_up;
	}
	
	public Player(Level level, int x, int y, Keyboard input, String name) {
		super(level, name);
		this.x = x;
		this.y = y;
		this.tileX = x/16;
		this.tileY = (y+15)/16;
		this.input = input;
		sprite = Sprite.player_up;
	}
	
	@Override
	public void onMobMovedToNewTile(Coordinates prevCoords){
		/*Random random = new Random();
		if(ExperimentX.debug && random.nextBoolean()){
			Logger.playerLogger.logInfo("Location: Block @ (" + tileX + ", " + tileY + "): " + level.getTile(new Coordinates(tileX, tileY)).getFormattedTileName() + " (id = " + level.getTile(new Coordinates(tileX, tileY)).getTileID() + ")");
		}*/
	}
	
	@Override
	public void update() {
		int xa = 0, ya = 0;
		if (anim < 7500) anim++;
		else anim = 0;
		if(!PauseMenu.paused){
			if (input.up) ya--;
			if (input.down) ya++;
			if (input.left) xa--;
			if (input.right) xa++;
		}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}
	
	@Override
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
