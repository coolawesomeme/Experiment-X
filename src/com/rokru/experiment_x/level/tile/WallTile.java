package com.rokru.experiment_x.level.tile;

import com.rokru.experiment_x.graphics.Sprite;

public class WallTile extends GeneralTile {

	public WallTile(Sprite sprite, String id, int colorID, String name) {
		super(sprite, id, colorID, name);
	}	
	
	@Override
	public boolean isWalkable(){
		return false;
	}
}
