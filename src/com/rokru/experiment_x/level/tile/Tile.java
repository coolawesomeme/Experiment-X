package com.rokru.experiment_x.level.tile;

import java.util.HashMap;

import com.rokru.experiment_x.entity.mob.Mob;
import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.graphics.Sprite;

public class Tile {
	
	public int x, y;
	public Sprite sprite;
	private String ID;
	private String name;
	private static HashMap<String, Tile> tileIDMap = new HashMap<String, Tile>();
	
	public static Tile voidTile = new VoidTile(Sprite.voidSprite, "e-x:"+0, "experimentx:void");
	public static Tile grass = new GrassTile(Sprite.grass, "e-x:"+1, "experimentx:grass");
	public static Tile flower_1 = new FlowerTile(Sprite.flower_1, "e-x:"+2, "experimentx:yellow flower");
	public static Tile rock_1 = new RockTile(Sprite.rock_1, "e-x:"+3, "experimentx:blocky rock");
	public static Tile flower_2 = new FlowerTile(Sprite.flower_2, "e-x:"+4, "experimentx:purple flower");
	public static Tile rock_2 = new RockTile(Sprite.rock_2, "e-x:"+5, "experimentx:round rock");
	public static Tile water_0 = new WaterTile(Sprite.water_0, "e-x:"+6, "experimentx:water_still");
	public static Tile water_1 = new WaterTile(Sprite.water_1, "e-x:"+7, "experimentx:water_vertical");
	public static Tile water_2 = new WaterTile(Sprite.water_2, "e-x:"+8, "experimentx:water_horizontal");
	public static Tile tall_grass_1 = new GrassTile(Sprite.tall_grass_1, "e-x:"+9, "experimentx:tall grass");
	public static Tile tall_grass_2 = new GrassTile(Sprite.tall_grass_2, "e-x:"+10, "experimentx:tall grass");
	
	public Tile(Sprite sprite, String id, String name) {
		this.sprite = sprite;
		this.ID = id;
		this.name = name;
		tileIDMap.put(id, this);
	}
	
	public void render(int x, int y, Render screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public String getTileID(){
		return ID;
	}
	
	public String getTileName(){
		return name;
	}
	
	public String getFormattedTileName(){
		return name.split(":")[1].toUpperCase();
	}
	
	public boolean solid(){
		return false;
	}

	public boolean walkable(){
		if(this.solid()) return false;
		else return true;
	}
	
	public void onTileStep(Mob mob){}
	
	public static Tile getTileFromID(String tileID){
		if(!tileID.startsWith("e-x:")){
			return Tile.voidTile;
		}
		return tileIDMap.containsKey(tileID) ? tileIDMap.get(tileID) : Tile.voidTile;
	}
}
