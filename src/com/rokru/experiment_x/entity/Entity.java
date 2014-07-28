package com.rokru.experiment_x.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.rokru.experiment_x.Logger;
import com.rokru.experiment_x.entity.mob.Player;
import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.level.Level;

public abstract class Entity {

	public int x, y;
	public int tileX, tileY;
	private boolean removed = false;
	protected Level level;
	protected static String name = "Entity";
	protected final Random random = new Random();
	protected static UUID entityUUID;
	private static HashMap<UUID, Entity> uuidMap = new HashMap<UUID, Entity>();
	private static List<Entity> entityList = new ArrayList<Entity>();

	public Entity(Level level, String name) {
		this.level = level;
		this.name = name;
		entityUUID = UUID.randomUUID();
		uuidMap.put(entityUUID, this);
		entityList.add(this);
		if(this instanceof Player){
			Logger.playerLogger.logInfo("Player UUID: " + entityUUID.toString());
		}
	}

	public void update() {
	}

	public void render(Render screen) {
	}

	public void remove() {
		// Remove from level
		removed = true;
		uuidMap.remove(this);
	}

	public boolean isRemoved() {
		return removed;
	}

	public static List<Entity> getEntityList(){
		return entityList;
	}
	
	public static List<UUID> getUUIDList(){
		ArrayList<UUID> UUIDList = new ArrayList<UUID>();
		for(UUID id : uuidMap.keySet()){
			UUIDList.add(id);
		}
		return UUIDList;
	}
	
	public static Entity getEntityFromUUID(String uuid){
		try{
			return uuidMap.get(UUID.fromString(uuid));
		}catch(Exception e){
			return null;
		}
	}
	
	public static String getEntityName(){
		return name;
	}
	
	public void initLevel(Level level){
		this.level = level;
	}
}
