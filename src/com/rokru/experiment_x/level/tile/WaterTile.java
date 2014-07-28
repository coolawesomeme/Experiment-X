package com.rokru.experiment_x.level.tile;

import com.rokru.experiment_x.graphics.Sprite;

public class WaterTile extends Tile {

	public WaterTile(Sprite sprite, String id, String name) {
		super(sprite, id, name);
	}
	
	public boolean walkable(){
		return false;
	}
}
