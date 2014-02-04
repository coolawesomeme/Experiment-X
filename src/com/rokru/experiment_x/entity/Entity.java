package com.rokru.experiment_x.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import com.rokru.experiment_x.Logger;
import com.rokru.experiment_x.entity.mob.Player;
import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.level.Level;

public abstract class Entity {

	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	protected String UUID;
	private static HashMap<String, Entity> uuidMap = new HashMap<String, Entity>();
	private static List<Entity> entityList = new ArrayList<Entity>();

	public Entity() {
		UUID = generateUUID();
		while (uuidMap.containsKey(UUID)) {
			UUID = generateUUID();
		}
		uuidMap.put(UUID, this);
		entityList.add(this);
		if(this instanceof Player){
			Logger.logInfo("Player UUID: " + UUID);
		}
	}

	public void update() {
	}

	public void render(Render screen) {
	}

	public void remove() {
		// Remove from level
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public static List<Entity> getEntityList(){
		return entityList;
	}
	
	public static Entity getEntityFromUUID(String UUID){
		try{
			return uuidMap.get(UUID);
		}catch(Exception e){
			return null;
		}
	}
	
	protected String generateUUID() {
		String uuid = "";
		String[] appenders = { "a", "b", "c", "d", "e", "f", "g", "h", "i",
				"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
				"v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F", "G",
				"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
				"T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4",
				"5", "6", "7", "8", "9" };
		for (int i = 0; i < 24; i++) {
			uuid = uuid.concat(appenders[random.nextInt(appenders.length)]);
		}
		return uuid;
	}
}
