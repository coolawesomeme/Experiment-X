package com.rokru.experiment_x.level;

import java.util.Random;

import com.rokru.experiment_x.level.tile.Tile;

public class RandomLevel extends Level {
	
	private static final Random random = new Random();

	public RandomLevel(int width, int height) {
		super(width, height);
	}
	
	protected void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int q = random.nextInt(6);
				if(q == 0){
					tiles[x + y * height] = Tile.grass.getTileID();
				}else if(q == 1){
					tiles[x + y * height] = random.nextInt(5) == 0 ? Tile.flower_1.getTileID() : Tile.grass.getTileID();
				}else if(q == 2){
					tiles[x + y * height] = random.nextInt(5) == 0 ? Tile.rock_1.getTileID() : Tile.grass.getTileID();
				}else if(q == 3){
					tiles[x + y * height] = random.nextInt(5) == 0 ? Tile.flower_2.getTileID() : Tile.grass.getTileID();
				}else if(q == 4){
					tiles[x + y * height] = random.nextInt(5) == 0 ? Tile.rock_2.getTileID() : Tile.grass.getTileID();
				}else if(x + y * height > 0 && x + y * height < getLevelSide()*getLevelSide()){
					tiles[x + y * height] = random.nextBoolean() ? Tile.water_1.getTileID() : Tile.water_2.getTileID();
				}else{
					tiles[x + y * height] = Tile.voidTile.getTileID();
				}
			}
		}
		tiles[0] = Tile.grass.getTileID();
		tiles[1] = Tile.grass.getTileID();
		tiles[128] = Tile.grass.getTileID();
		tiles[129] = Tile.grass.getTileID();
	}

}
