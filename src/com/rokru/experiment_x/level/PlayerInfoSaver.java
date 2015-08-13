package com.rokru.experiment_x.level;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.rokru.experiment_x.ExperimentX;
import com.rokru.experiment_x.Logger;

public class PlayerInfoSaver {

	public static void savePlayerCoords(Coordinates coords){
		File folders = new File(ExperimentX.getDirectory() + "/saves/");
		folders.mkdirs();
		File f = new File(ExperimentX.getDirectory() + "/saves/player.loc");
		try {
			f.createNewFile();
			FileWriter f1 = new FileWriter(f);
			f1.write("(" + coords.getX() + ", " + coords.getY() + ")");
			Logger.playerLogger.logInfo("Saved Coords: (" + coords.getX() + ", " + coords.getY() + ")");
			f1.flush();
			f1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Coordinates getPlayerCoords(){
		File file = new File(ExperimentX.getDirectory() + "/saves/player.loc");
		Coordinates coords = null;
		String result = null;
		if(file.exists()){
			try {
				result = new Scanner(file).nextLine();
			} catch (Exception e) {
				result = null;
			}
			Logger.playerLogger.logInfo("Loaded Coords: " + result);
			coords = new Coordinates(
					Integer.parseInt(result.split(", ")[0].substring(1)),
					Integer.parseInt(result.split(", ")[1].substring(0, result.split(", ")[1].length() - 1)));
		}
		return coords;
	}
	
}
