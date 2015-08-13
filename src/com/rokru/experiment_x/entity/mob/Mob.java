package com.rokru.experiment_x.entity.mob;

import com.rokru.experiment_x.entity.Entity;
import com.rokru.experiment_x.graphics.Sprite;
import com.rokru.experiment_x.level.Coordinates;
import com.rokru.experiment_x.level.Level;
import com.rokru.experiment_x.level.tile.Tile;

public abstract class Mob extends Entity {
	
	public Mob(Level level, String name) {
		super(level, name);
	}

	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
    public Tile currentTile = Tile.voidTile;
	
	public void setCoords(Coordinates coords){
		x = coords.getX() * 16 + 8;
		y = (coords.getY() * 16) - 15 + 8;
		tileX = coords.getX();
		tileY = coords.getY();
	}
	
	public void setCurrentTile(Tile tile){
		currentTile = tile;
	}
	
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
			int prevTileX = tileX;
			int prevTileY = tileY;
			x += xa;
			y += ya;
			tileX = x/16;
			tileY = (y+15)/16;
			if(prevTileX != tileX || prevTileY != tileY){
				newTileEvent(new Coordinates(prevTileX, prevTileY));
			}
		}
	}
	
	private boolean collision(int xa, int ya) {
		boolean walkable = true;
		for(int c = 0; c < 4; c++){
			int xt = ((x+xa) + c % 2 * 12 - 5) / 16;
			int yt = ((y+ya) + c / 2 * 12 + 4) / 16;
			if(!level.getTile(new Coordinates(xt, yt)).isWalkable()) walkable = false;
		}
		return !walkable;
	}
	
	private void newTileEvent(Coordinates prevCoords){
		this.setCurrentTile(level.getTile(new Coordinates(tileX, tileY)));
		this.currentTile.onTileStep(this);
		onMobMovedToNewTile(prevCoords);
	}
	
	public void update() {}
	
	public void render() {}
	
	public void onMobMovedToNewTile(Coordinates prevCoords) {}
}
