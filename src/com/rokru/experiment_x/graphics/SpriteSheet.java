package com.rokru.experiment_x.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	
	private String path;
	public final int SIZEx, SIZEy;
	public int[] pixels;
	
	public static SpriteSheet tiles = new SpriteSheet("/textures/spritesheet.png", 256);
	public static SpriteSheet charSpritesheet = new SpriteSheet("/textures/char.png", 128, 96);
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZEx = size;
		SIZEy = size;
		pixels = new int[SIZEx * SIZEy];
		load();
	}
	
	public SpriteSheet(String path, int sizeX, int sizeY) {
		this.path = path;
		this.SIZEx = sizeX;
		this.SIZEy = sizeY;
		pixels = new int[SIZEx * SIZEy];
		load();
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
