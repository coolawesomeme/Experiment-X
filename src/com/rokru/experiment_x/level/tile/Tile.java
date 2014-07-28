package com.rokru.experiment_x.level.tile;

import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.graphics.Sprite;

public class Tile {
	
	public int x, y;
	public Sprite sprite;
	private String ID;
	private String name;
	
	public static Tile voidTile = new VoidTile(Sprite.voidSprite, "e-x:"+0, "experimentx:void");
	public static Tile grass = new GrassTile(Sprite.grass, "e-x:"+1, "experimentx:grass");
	public static Tile flower_1 = new FlowerTile(Sprite.flower_1, "e-x:"+2, "experimentx:flower 1");
	public static Tile rock_1 = new RockTile(Sprite.rock_1, "e-x:"+3, "experimentx:rock 1");
	public static Tile flower_2 = new FlowerTile(Sprite.flower_2, "e-x:"+4, "experimentx:flower 2");
	public static Tile rock_2 = new RockTile(Sprite.rock_2, "e-x:"+5, "experimentx:rock 2");
	
	public Tile(Sprite sprite, String id, String name) {
		this.sprite = sprite;
		this.ID = id;
		this.name = name;
	}
	
	public void render(int x, int y, Render screen) {
	}
	
	public String getTileID(){
		return ID;
	}
	
	public String getTileName(){
		return name;
	}
	
	public boolean solid(){
		return false;
	}

	public boolean walkable(){
		return true;
	}
}
