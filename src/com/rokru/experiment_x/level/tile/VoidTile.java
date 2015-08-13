package com.rokru.experiment_x.level.tile;

import com.rokru.experiment_x.graphics.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite, String id, int colorID, String name) {
		super(sprite, id, colorID, name);
	}

	@Override
	public boolean isWalkable(){
		return false;
	}
}
