package com.rokru.experiment_x.graphics;

public class Sprite {
	
	public final int SIZEx, SIZEy;
	private int x, y;
	public int[] pixels;
	private SpriteSheet sheet;
	
	public static Sprite grass = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite flower_1 = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock_1 = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite flower_2 = new Sprite(16, 3, 0, SpriteSheet.tiles);
	public static Sprite rock_2 = new Sprite(16, 4, 0, SpriteSheet.tiles);
	public static Sprite water_1 = new Sprite(16, 14, 0, SpriteSheet.tiles);
	public static Sprite water_2 = new Sprite(16, 15, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0x242424);
	
	

	public static Sprite player_up = new Sprite(32, 0, 0, SpriteSheet.charSpritesheet);
	public static Sprite player_down = new Sprite(32, 3, 0, SpriteSheet.charSpritesheet);
	public static Sprite player_left = new Sprite(32, 2, 0, SpriteSheet.charSpritesheet);
	public static Sprite player_right = new Sprite(32, 1, 0, SpriteSheet.charSpritesheet);
	
	public static Sprite player_up_1 = new Sprite(32, 0, 1, SpriteSheet.charSpritesheet);
	public static Sprite player_up_2 = new Sprite(32, 0, 2, SpriteSheet.charSpritesheet);
	
	public static Sprite player_right_1 = new Sprite(32, 1, 1, SpriteSheet.charSpritesheet);
	public static Sprite player_right_2 = new Sprite(32, 1, 2, SpriteSheet.charSpritesheet);
	
	public static Sprite player_down_1 = new Sprite(32, 2, 1, SpriteSheet.charSpritesheet);
	public static Sprite player_down_2 = new Sprite(32, 2, 2, SpriteSheet.charSpritesheet);
	
	public static Sprite player_left_1 = new Sprite(32, 3, 1, SpriteSheet.charSpritesheet);
	public static Sprite player_left_2 = new Sprite(32, 3, 2, SpriteSheet.charSpritesheet);

	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZEx = size;
		SIZEy = size;
		pixels = new int [SIZEx * SIZEy];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int size, int color) {
		SIZEx = size;
		SIZEy = size;
		pixels = new int[SIZEx * SIZEy];
		setColor(color);
	}
	
	private void setColor(int color) {
		for (int i = 0; i < SIZEx * SIZEy; i++) {
			pixels[i] = color;
		}	
		
	}
	
	private void load() {
		for (int y = 0; y < SIZEx; y++) {
			for (int x = 0; x < SIZEy; x++) {
				pixels[x + y * SIZEy] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZEx];
			}
		}
	}
}
