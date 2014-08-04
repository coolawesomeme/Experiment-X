package com.rokru.experiment_x.level;

import java.io.File;
import java.util.Scanner;

import com.rokru.experiment_x.level.tile.Tile;

public class AlternateLevelLoader {

	private static Level level;
	private static String[] tiles = new String[level.getLevelWidth() * level.getLevelHeight()];
	
	public AlternateLevelLoader(File file, Level level){
		AlternateLevelLoader.level = level;
		readFile(file);
	}
	
	public AlternateLevelLoader(String path, Level level){
		AlternateLevelLoader.level = level;
		File file = new File(path);
		readFile(file);
	}

	private void readFile(File file) {
		String result = null;
		if(file.exists()){
			try {
				result = new Scanner(file).next();
			} catch (Exception e) {
				result = null;
			}
		}
		interpretFile(result);
	}

	private void interpretFile(String result) {
		if(result == null){
			return;
		}else{
			if(!result.startsWith("{") && !result.endsWith("}")){
				return;
			}else{
				result.replace("{", "");
				result.replace("}", "");
				String[] tileTest = result.split(",");
				if(tileTest.length == level.getLevelWidth()*level.getLevelHeight()){
					tiles = result.split(",");
				}else{
					tiles = result.split(",");
					for(int i = 0; i < level.getLevelWidth()*level.getLevelHeight() - tileTest.length; i++){
						tiles[tileTest.length + i] = Tile.voidTile.getTileID();
					}
				}
			}
		}
	}
	
	public String[] getTiles(){
		return tiles;
	}
}
