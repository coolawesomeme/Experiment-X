package com.rokru.experiment_x.level;

import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.level.tile.SpecialTile;
import com.rokru.experiment_x.level.tile.Tile;

public class Level {
	
	protected String[] tiles;
	protected int width, height;
	private Tile[] specialTiles;
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new String[width * height];
		specialTiles = new Tile[width * height];
		generateLevel();
	}
	
	public Level(String path, int width, int height) {
		this(width, height);
		loadLevel(path);
	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}
	
	protected void loadLevel(String path) {}

	protected void generateLevel() {}
	
	public void update() {
	}
	
	@SuppressWarnings("unused")
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
				getTile(new Coordinates(x, y)).render(x, y, screen);
			}
		}
	}
	
	public Tile getTile(Coordinates coords) {
		if (coords.getX() < 0 || coords.getY() < 0 || coords.getX() >= width || coords.getY() >= height) return Tile.voidTile;
		else {
			//metadata sequence: "e-x:##~prop=value|prop=value"
			if(tiles[coords.getX()+coords.getY()*width].contains("~")){
				if(specialTiles[coords.getX()+coords.getY()*width] == null){
					String[] split = tiles[coords.getX()+coords.getY()*width].split("~");
					specialTiles[coords.getX()+coords.getY()*width] = new SpecialTile(split[0], split[1]);
				}
				return specialTiles[coords.getX()+coords.getY()*width];
			}else{
				return Tile.getTileFromID(tiles[coords.getX()+coords.getY()*width]);
			}
		}
	}
	
	public int getLevelWidth(){
		return width;
	}
	
	public int getLevelHeight(){
		return height;
	}
	
}
