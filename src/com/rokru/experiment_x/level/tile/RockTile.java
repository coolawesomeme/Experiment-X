package com.rokru.experiment_x.level.tile;

import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.graphics.Sprite;

public class RockTile extends Tile {

	public RockTile(Sprite sprite, String id, String name) {
		super(sprite, id, name);
	}
	
	public void render(int x, int y, Render screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean solid() {
		return true;
	}
}
