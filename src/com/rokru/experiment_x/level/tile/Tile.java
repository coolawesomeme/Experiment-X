package com.rokru.experiment_x.level.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.rokru.experiment_x.Logger;
import com.rokru.experiment_x.entity.mob.Mob;
import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.graphics.Sprite;

public class Tile {
	
	public int x, y;
	public Sprite sprite;
	private String ID;
	private int colorID;
	private String name;
	private static HashMap<String, Tile> tileIDMap = new HashMap<String, Tile>();
	private static HashMap<Integer, Tile> tileColorIDMap = new HashMap<Integer, Tile>();
	private static List<String> specialIDList = new ArrayList<String>();
	
	public static Tile voidTile = new VoidTile(Sprite.voidSprite, "e-x:"+0, 0xff242424, "experimentx:void");
	public static Tile grass = new NaturalTile(Sprite.grass, "e-x:"+1, 0xff67ae49, "experimentx:grass");
	public static Tile flower_1 = new NaturalTile(Sprite.flower_1, "e-x:"+2, 0xffe7c60d, "experimentx:yellow_flower");
	public static Tile rock_1 = new NaturalTile(Sprite.rock_1, "e-x:"+3, 0xff8f8f8f, "experimentx:blocky_rock");
	public static Tile flower_2 = new NaturalTile(Sprite.flower_2, "e-x:"+4, 0xffc300d4, "experimentx:purple_flower");
	public static Tile rock_2 = new NaturalTile(Sprite.rock_2, "e-x:"+5, 0xff949494, "experimentx:round_rock");
	public static Tile water_0 = new WaterTile(Sprite.water_0, "e-x:"+6, 0xff1f9be1, "experimentx:water_still");
	public static Tile water_1 = new WaterTile(Sprite.water_1, "e-x:"+7, 0xff1d98de, "experimentx:water_vertical");
	public static Tile water_2 = new WaterTile(Sprite.water_2, "e-x:"+8, 0xff5cacff, "experimentx:water_horizontal");
	public static Tile tall_grass_1 = new NaturalTile(Sprite.tall_grass_1, "e-x:"+9, 0xff62a646, "experimentx:tall_grass");
	public static Tile tall_grass_2 = new NaturalTile(Sprite.tall_grass_2, "e-x:"+10, 0xff5fa143, "experimentx:tall_grass");
	public static Tile stone_bricks = new WallTile (Sprite.stone_bricks, "e-x:"+11,0xff404040, "experimentx:stone_bricks");
	public static Tile clay_bricks = new WallTile (Sprite.clay_bricks, "e-x:"+12,0xff7F1900, "experimentx:clay_bricks");
	public static Tile wooden_planks = new WallTile (Sprite.wooden_planks, "e-x:"+13,0xff351500, "experimentx:wooden_planks");
	public static Tile wooden_slope_1 = new WallTile (Sprite.wooden_slope_1, "e-x:"+14,0xff3F1800, "experimentx:wooden_slope_1");
	public static Tile wooden_slope_2 = new WallTile (Sprite.wooden_slope_2, "e-x:"+15,0xff4C1C00, "experimentx:wooden_slope_2");
	public static Tile wooden_slope_3 = new WallTile (Sprite.wooden_slope_3, "e-x:"+16,0xff591F00, "experimentx:wooden_slope_3");
	public static Tile wooden_slope_4 = new WallTile (Sprite.wooden_slope_4, "e-x:"+17,0xff662100, "experimentx:wooden_slope_4");
	public static Tile metal_gray = new GeneralTile (Sprite.metal_gray, "e-x:"+18,0xffBFBFBF, "experimentx:iron_block");
	public static Tile teleporter_1 = new FunctionalTile (Sprite.teleporter_1, "e-x:"+19, 0xff0184ff, "experimentx:blue_teleporter");
	public static Tile teleporter_2 = new FunctionalTile (Sprite.teleporter_2, "e-x:"+20, 0xffc400ff, "experimentx:purple_teleporter");
	
	public Tile(Sprite sprite, String id, int colorID, String name) {
		this.sprite = sprite;
		this.ID = id;
		this.colorID = colorID;
		this.name = name;
		if(tileIDMap.containsKey(id) && colorID != 0)
			Logger.generalLogger.logError("The ID " + id + " is already in use!", 2);
		if(tileColorIDMap.containsKey(colorID) && colorID != 0)
			Logger.generalLogger.logError("The color ID " + colorID + " is already in use!", 1);
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
		return name.split(":")[1].toUpperCase().replace("_", " ");
	}
	
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
	
	public static String getUniqueSpecialID() {
		Random random = new Random();
		int i = random.nextInt(999);
		i+=1000;
		String id = "e-x:"+i;
		if(!specialIDList.contains(id)){
			specialIDList.add(id);
			Logger.generalLogger.logInfo("Unique special ID generated (" + id + ").");
			return id;
		}else
			return getUniqueSpecialID();
	}
	
	public boolean isWalkable(){
		return true;
	}
	
	public boolean isSpecialTile(){
		return false;
	}
	
	public void onTileStep(Mob mob){}
}
