package com.rokru.experiment_x.level;

import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

import com.rokru.experiment_x.ExperimentX;
import com.rokru.experiment_x.level.tile.Tile;

public class LevelLoader extends Level{
	
	public LevelLoader(String path, int lvlWidth, int lvlHeight) {
		super(path, lvlWidth, lvlHeight);
		loadLevel(path);
	}

	protected void loadLevel(String path) {
		try{
			InputStream file = ExperimentX.class.getResourceAsStream(path);
			String[] result = new String[height];
			Scanner scanner = new Scanner(file);
			for(int a = 0; a < result.length; a++)
				result[a] = scanner.nextLine();
			scanner.close();
			for(int y = 0; y < result.length; y++){
				result[y] = result[y].replace("{", "");
				result[y] = result[y].replace("}", "");
				result[y] = result[y].replace("\"", "");
				result[y] = result[y].replace("\n", "");
				String[] tileRaw = result[y].split(",");
				tileRaw = replaceRandomGrassy(tileRaw);
				for(int x = 0; x < tileRaw.length; x++){
					tiles[width * y + x] = tileRaw[x];
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			
			ExperimentX.level = new RandomLevel(width, height);
		}
	}
	
	private String[] replaceRandomGrassy(String[] tileRaw) {
		for(int i = 0; i < tileRaw.length; i++){
			if(tileRaw[i].equals("e-x:random_grassy")){
				Random random = new Random();
				int q = random.nextInt(6);
				if(q == 0) tileRaw[i] = Tile.grass.getTileID();
				else if(q == 1){
					if(random.nextInt(5) == 0){
						tileRaw[i] = Tile.rock_1.getTileID();
					}else{
						tileRaw[i] = Tile.grass.getTileID();
					}
				}else if(q == 2){
					if(random.nextInt(5) == 0){
						tileRaw[i] = Tile.rock_2.getTileID();
					}else{
						tileRaw[i] = Tile.grass.getTileID();
					}
				}else if(q == 3){
					if(random.nextInt(5) == 0){
						tileRaw[i] = Tile.flower_1.getTileID();
					}else{
						tileRaw[i] = Tile.grass.getTileID();
					}
				}else if(q == 4){
					if(random.nextInt(5) == 0){
						tileRaw[i] = Tile.flower_2.getTileID();
					}else{
						tileRaw[i] = Tile.grass.getTileID();
					}
				}else if(q == 5){
					if(random.nextBoolean()){
						tileRaw[i] = Tile.tall_grass_1.getTileID();
					}else{
						tileRaw[i] = Tile.tall_grass_2.getTileID();
					}
				}
				else tileRaw[i] = Tile.grass.getTileID();
			}
		}
		return tileRaw;
	}

	public String[] getTiles(){
		return tiles;
	}
}
