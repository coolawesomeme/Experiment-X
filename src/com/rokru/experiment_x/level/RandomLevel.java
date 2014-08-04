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
					tiles[x + y * height] = random.nextBoolean() ? Tile.tall_grass_1.getTileID() : Tile.tall_grass_2.getTileID();
				}else{
					tiles[x + y * height] = Tile.voidTile.getTileID();
				}
			}
		}
		
		int z = random.nextInt(15);
		z += 70;
		
		for(int i = 0; i < z; i++){
			int px = random.nextInt((width-3));
			int py = random.nextInt((height-3));
			
			if(px <= 3 || py <= 3){
				px += 3;
				py += 3;
			}
			
			if(!Tile.getTileFromID(tiles[px+py*height]).walkable()){
				continue;
			}
			
			if(random.nextBoolean()){
				tiles[px + py * height] = Tile.water_0.getTileID();
				tiles[(px+1) + py * height] = Tile.water_2.getTileID();
				tiles[(px-1) + py * height] = Tile.water_2.getTileID();
				tiles[px + (py+1) * height] = Tile.water_1.getTileID();
				tiles[px + (py-1) * height] = Tile.water_1.getTileID();
			}else{
				tiles[px + py * height] = Tile.water_0.getTileID();
				tiles[(px+1) + py * height] = Tile.water_0.getTileID();
				tiles[(px+1) + (py-1) * height] = Tile.water_0.getTileID();
				tiles[px + (py-1) * height] = Tile.water_0.getTileID();
				tiles[px + (py+1) * height] = Tile.water_1.getTileID();
				tiles[(px+1) + (py+1) * height] = Tile.water_1.getTileID();
				tiles[px + (py-2) * height] = Tile.water_1.getTileID();
				tiles[(px+1) + (py-2) * height] = Tile.water_1.getTileID();
				tiles[(px-1) + py * height] = Tile.water_2.getTileID();
				tiles[(px-1) + (py-1) * height] = Tile.water_2.getTileID();
				tiles[(px+2) + py * height] = Tile.water_2.getTileID();
				tiles[(px+2) + (py-1) * height] = Tile.water_2.getTileID();
			}
		}
		
		tiles[0 + 0 * height] = Tile.grass.getTileID();
		tiles[1 + 0 * height] = Tile.grass.getTileID();
		tiles[0 + 1 * height] = Tile.grass.getTileID();
		tiles[1 + 1 * height] = Tile.grass.getTileID();
	}

}
