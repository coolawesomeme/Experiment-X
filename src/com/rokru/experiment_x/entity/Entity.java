package com.rokru.experiment_x.entity;

import java.util.HashMap;
import java.util.Random;

import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.level.Level;

public abstract class Entity {
	
	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	protected String UUID;
	private HashMap<String, Entity> uuidList = new HashMap<String, Entity>();
	
	public Entity(){
		UUID = generateUUID();
		while(uuidList.containsKey(UUID)){
			UUID = generateUUID();
		}
		uuidList.put(UUID, this);
	}
	
	public void update() {
	}
	
	public void render (Render screen) {
	}
	
	public void remove() {
		//Remove from level
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}

	protected String generateUUID(){
		return "NYI";
	}
}
