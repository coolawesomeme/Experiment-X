package com.rokru.experiment_x.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.rokru.experiment_x.ExperimentX;
import com.rokru.experiment_x.Logger;
import com.rokru.experiment_x.level.tile.Tile;

public class SpawnLevel extends Level{
	
	public SpawnLevel(String path) {
		super(path);
	}
	
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(ExperimentX.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tilePixels = new int[w*h];
			image.getRGB(0, 0, w, h, tilePixels, 0, w);
			for(int i = 0; i < tilePixels.length; i++){
				if(tilePixels[i] == 0xff9f00a2){
					Random random = new Random();
					int q = random.nextInt(6);
					if(q == 0) tilePixels[i] = Tile.grass.getTileColorID();
					else if(q == 1){
						if(random.nextInt(5) == 0){
							tilePixels[i] = Tile.rock_1.getTileColorID();
						}else{
							tilePixels[i] = Tile.grass.getTileColorID();
						}
					}else if(q == 2){
						if(random.nextInt(5) == 0){
							tilePixels[i] = Tile.rock_2.getTileColorID();
						}else{
							tilePixels[i] = Tile.grass.getTileColorID();
						}
					}else if(q == 3){
						if(random.nextInt(5) == 0){
							tilePixels[i] = Tile.flower_1.getTileColorID();
						}else{
							tilePixels[i] = Tile.grass.getTileColorID();
						}
					}else if(q == 4){
						if(random.nextInt(5) == 0){
							tilePixels[i] = Tile.flower_2.getTileColorID();
						}else{
							tilePixels[i] = Tile.grass.getTileColorID();
						}
					}else if(q == 5){
						if(random.nextBoolean()){
							tilePixels[i] = Tile.tall_grass_1.getTileColorID();
						}else{
							tilePixels[i] = Tile.tall_grass_2.getTileColorID();
						}
					}
					else tilePixels[i] = Tile.grass.getTileColorID();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			Logger.generalLogger.logError("Exception! Could not load level file!");
		}
	}
}
