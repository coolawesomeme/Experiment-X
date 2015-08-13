package com.rokru.experiment_x.level;

import com.rokru.experiment_x.entity.Entity;
import com.rokru.experiment_x.level.tile.Tile;

public class Coordinates {

	private int xCoord, yCoord;
	private Object source = null;
	
	public Coordinates(int x, int y, Entity e){
		this(x, y);
		source = e;
	}
	
	public Coordinates(int x, int y, Tile t){
		this(x, y);
		source = t;
	}
	
	public Coordinates(int x, int y){
		xCoord = x;
		yCoord = y;
	}
	
	public int getX(){
		return xCoord;
	}
	
	public int getY(){
		return yCoord;
	}
	
	/** Returns the object at the coords.
	 * Will return either an entity or a tile. **/
	public Object getSource(){
		return source;
	}
	
	public void setCoords(int x, int y){
		xCoord = x;
		yCoord = y;
	}
	
	public void moveCoords(int xa, int ya){
		xCoord += xa;
		yCoord += ya;
	}
}
