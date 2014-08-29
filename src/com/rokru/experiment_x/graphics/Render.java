package com.rokru.experiment_x.graphics;

import java.util.Random;

import com.rokru.experiment_x.level.tile.Tile;

public class Render {
	
	public int width, height;
	public int[] pixels;
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	public int xOffset, yOffset;
	public int[] tiles = new int [MAP_SIZE * MAP_SIZE];
	private Random random = new Random();
	
	public Render(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height]; // 50,400
		
		for (int i = 0; i < MAP_SIZE * MAP_SIZE; i++) {
			tiles[i] = random.nextInt(0xffffff);	
		
		}
		
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZEy; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZEx; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZEx || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZEx];
			}
		}
	}
	
	public void renderPlayer(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZEx; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZEx; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZEx || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = sprite.pixels[x + y * sprite.SIZEx];
				if (col != 0xffff00ff) pixels[xa + ya * width] = col;
				}
			}
		}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

}