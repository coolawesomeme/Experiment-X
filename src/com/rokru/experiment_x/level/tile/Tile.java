package com.rokru.experiment_x.level.tile;

import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.graphics.Sprite;

public class Tile {
	
	public int x, y;
	public Sprite sprite;
	private int ID;
	
	public static Tile voidTile = new VoidTile(Sprite.voidSprite, 0);
	public static Tile grass = new GrassTile(Sprite.grass, 1);
	public static Tile flower_1 = new FlowerTile(Sprite.flower_1, 2);
	public static Tile rock = new RockTile(Sprite.rock, 3);
	public static Tile flower_2 = new FlowerTile(Sprite.flower_2, 4);
	
	public Tile(Sprite sprite, int id) {
		this.sprite = sprite;
		this.ID = id;
	}
	
	public void render(int x, int y, Render screen) {
	}
	
	public int getTileID(){
		return ID;
	}
	
	public boolean solid() {
		if(this.equals(voidTile))
			return false;
		else
			return true;
	}

}
