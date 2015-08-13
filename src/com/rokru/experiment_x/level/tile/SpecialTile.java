package com.rokru.experiment_x.level.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.rokru.experiment_x.entity.mob.Mob;
import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.level.Coordinates;


public class SpecialTile extends GeneralTile{

	private boolean walkable;
	private Tile background = null;
	private static Random random = new Random();
	private boolean teleporter = false;
	private List<String> unusedCommands = new ArrayList<String>();
	
	public SpecialTile(String id, String properties) {
		super(Tile.getTileFromID(id).sprite, "e-x:"+(random.nextInt(999)+1000), 0, Tile.getTileFromID(id).getTileName());
		walkable = Tile.getTileFromID(id).isWalkable();
		changeTileProperties(properties);
		if(id.equals(Tile.teleporter_1.getTileID())){
			teleportHandler();
		}else if(id.equals(Tile.teleporter_2.getTileID())){
			teleportHandler();
		}
	}

	private void changeTileProperties(String properties) {
		String[] commands = properties.split("\\|");
		for(String command : commands){
			if(command.startsWith("walkable")){
				String[] a = command.split("=");
				if(a.length > 1) walkable = Boolean.parseBoolean(a[1]);
			}else if(command.startsWith("background")){
				String[] a = command.split("=");
				if(a.length > 1) background = Tile.getTileFromID(a[1]);
			}else{
				unusedCommands.add(command);
			}
		}
	}

	@Override
	public void render(int x, int y, Render screen){
		if(background != null)
			screen.renderTile(x << 4, y << 4, background);
		screen.renderTile(x << 4, y << 4, this);
	}
	
	private void teleportHandler(){
		this.teleporter = true;
	}
	
	@Override
	public void onTileStep(Mob mob){
		if(teleporter){
			int toX = 0, toY = 0;
			for(String s : unusedCommands){
				if(s.startsWith("teleportTo")){
					String[] a = s.split("=");
					if(a.length > 1) {
						toX = Integer.parseInt(a[1].split("/")[0]);
						toY = Integer.parseInt(a[1].split("/")[1]);
					}
					break;
				}
			}
			mob.setCoords(new Coordinates(toX, toY));;
		}
	}
	
	@Override
	public boolean isWalkable(){
		return walkable;
	}
	
	@Override
	public boolean isSpecialTile(){
		return true;
	}
}
