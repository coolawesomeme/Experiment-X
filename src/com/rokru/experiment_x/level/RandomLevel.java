package com.rokru.experiment_x.level;

import java.util.Random;

public class RandomLevel extends Level {
	
	private static final Random random = new Random();

	public RandomLevel(int width, int height) {
		super(width, height);
	}
	
	protected void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int q = random.nextInt(4);
				if(q == 0){
					tiles[x + y * width] = q;
				}else if(q == 1){
					tiles[x + y * width] = random.nextBoolean() ? q : 0;
				}else if(q == 2){
					tiles[x + y * width] = random.nextInt(4) <= 2 ? q : 0;
				}else{
					tiles[x + y * width] = q;
				}
			}
		}
		
		
	}

}
