package com.rokru.experiment_x.level.tile;

import java.util.HashMap;

import com.rokru.experiment_x.entity.mob.Mob;
import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.graphics.Sprite;
import com.rokru.experiment_x.graphics.SpriteSheet;

public class Tile {
	
	public int x, y;
	public Sprite sprite;
	private String ID;
	private int colorID;
	private String name;
	private static HashMap<String, Tile> tileIDMap = new HashMap<String, Tile>();
	private static HashMap<Integer, Tile> tileColorIDMap = new HashMap<Integer, Tile>();
	
	public static Tile voidTile = new VoidTile(Sprite.voidSprite, "e-x:"+0, 0xff242424, "experimentx:void");
	public static Tile grass = new NatureTile(Sprite.grass, "e-x:"+1, 0xff67ae49, "experimentx:grass");
	public static Tile flower_1 = new NatureTile(Sprite.flower_1, "e-x:"+2, 0xffe7c60d, "experimentx:yellow flower");
	public static Tile rock_1 = new NatureTile(Sprite.rock_1, "e-x:"+3, 0xff8f8f8f, "experimentx:blocky rock");
	public static Tile flower_2 = new NatureTile(Sprite.flower_2, "e-x:"+4, 0xffc300d4, "experimentx:purple flower");
	public static Tile rock_2 = new NatureTile(Sprite.rock_2, "e-x:"+5, 0xff949494, "experimentx:round rock");
	public static Tile water_0 = new WaterTile(Sprite.water_0, "e-x:"+6, 0xff1f9be1, "experimentx:water_still");
	public static Tile water_1 = new WaterTile(Sprite.water_1, "e-x:"+7, 0xff1d98de, "experimentx:water_vertical");
	public static Tile water_2 = new WaterTile(Sprite.water_2, "e-x:"+8, 0xff5cacff, "experimentx:water_horizontal");
	public static Tile tall_grass_1 = new NatureTile(Sprite.tall_grass_1, "e-x:"+9, 0xff62a646, "experimentx:tall grass");
	public static Tile tall_grass_2 = new NatureTile(Sprite.tall_grass_2, "e-x:"+10, 0xff5fa143, "experimentx:tall grass");
	public static Tile stone_bricks = new WallTile (Sprite.stone_bricks, "e-x:"+11,0xff404040, "experimentx:stone bricks");
	public static Tile clay_bricks = new WallTile (Sprite.clay_bricks, "e-x:"+12,0xff7F1900, "experimentx:clay bricks");
	public static Tile wooden_planks = new WallTile (Sprite.wooden_planks, "e-x:"+13,0xff351500, "experimentx:wooden planks");
	public static Tile wooden_slope_1 = new WallTile (Sprite.wooden_slope_1, "e-x:"+14,0xff3F1800, "experimentx:wooden slope 1");
	public static Tile wooden_slope_2 = new WallTile (Sprite.wooden_slope_2, "e-x:"+15,0xff4C1C00, "experimentx:wooden slope 2");
	public static Tile wooden_slope_3 = new WallTile (Sprite.wooden_slope_3, "e-x:"+16,0xff591F00, "experimentx:wooden slope 3");
	public static Tile wooden_slope_4 = new WallTile (Sprite.wooden_slope_4, "e-x:"+17,0xff662100, "experimentx:wooden slope 4");
	public static Tile metal_gray = new GeneralTile (Sprite.metal_gray, "e-x:"+18,0xffBFBFBF, "experimentx:iron block");
	
	public Tile(Sprite sprite, String id, int colorID, String name) {
		this.sprite = sprite;
		this.ID = id;
		this.colorID = colorID;
		this.name = name;
		tileIDMap.put(id, this);
		tileColorIDMap.put(colorID, this);
	}
	
	public void render(int x, int y, Render screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public String getTileID(){
		return ID;
	}
	
	public int getTileColorID(){
		return colorID;
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
	
	public static Tile getTileFromColorID(int tileColorID){
		if(tileColorIDMap.containsKey(tileColorID)){
			return tileColorIDMap.get(tileColorID);
		}else if(tileColorID == 0xff9f00a2){
			return Tile.grass;
		}else{
			return Tile.voidTile;
		}
	}
}
