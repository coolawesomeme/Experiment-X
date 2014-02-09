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
				int q = random.nextInt(5);
				if(q == 0){
					tiles[x + y * width] = Tile.grass.getTileID();
				}else if(q == 1){
					tiles[x + y * width] = random.nextInt(4) <= 1 ? Tile.flower_1.getTileID() : 1;
				}else if(q == 2){
					tiles[x + y * width] = random.nextBoolean() ? Tile.rock.getTileID() : 1;
				}else if(q == 3){
					tiles[x + y * width] = random.nextInt(4) <= 1 ? Tile.flower_2.getTileID() : 1;
				}else{
					tiles[x + y * width] = Tile.voidTile.getTileID();
				}
			}
		}
		tiles[0] = Tile.grass.getTileID();
	}

}
