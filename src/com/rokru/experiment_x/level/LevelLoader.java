package com.rokru.experiment_x.level;

import java.io.File;
import java.util.Scanner;

import com.rokru.experiment_x.level.tile.Tile;

public class LevelLoader {

	private static String[] tiles = new String[128 * 128];
	
	public LevelLoader(File file){
		readFile(file);
	}
	
	public LevelLoader(String path){
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
				if(tileTest.length == 128 * 128){
					tiles = result.split(",");
				}else{
					tiles = result.split(",");
					for(int i = 0; i < 128*128 - tileTest.length; i++){
						tiles[tileTest.length + i] = Tile.voidTile.getTileID();
					}
				}
			}
		}
	}
	
	public static String[] getTiles(){
		return tiles;
	}
}
