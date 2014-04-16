package com.rokru.experiment_x.level;

import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.level.tile.Tile;

public class Level {
	
	protected int width, height;
	protected String[] tiles;
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new String[width * height];
		generateLevel();
	}
	
	public Level(String path) {
		//loadLevel(path);
	}

	protected void generateLevel() {		
	}
	
	public void update() {
	}
	
	private void time() {
	}
	
	public void render(int xScroll, int yScroll, Render screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height || tiles[x + y * width] == null) return Tile.voidTile;
		else if (tiles[x + y * width] == Tile.grass.getTileID()) return Tile.grass;
		else if (tiles[x + y * width] == Tile.flower_1.getTileID()) return Tile.flower_1;
		else if (tiles[x + y * width] == Tile.rock_1.getTileID()) return Tile.rock_1;
		else if (tiles[x + y * width] == Tile.flower_2.getTileID()) return Tile.flower_2;
		else if (tiles[x + y * width] == Tile.rock_2.getTileID()) return Tile.rock_2;
		return Tile.voidTile;
	}
	
	public int getLevelSide(){
		return width;
	}
	
}
