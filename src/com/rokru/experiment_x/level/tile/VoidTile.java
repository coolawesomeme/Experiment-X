package com.rokru.experiment_x.level.tile;

import com.rokru.experiment_x.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite, String id, String name) {
		super(sprite, id, name);
	}

	public boolean walkable(){
		return false;
	}
}
