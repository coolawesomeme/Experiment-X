package com.rokru.experiment_x.level;

import java.io.File;

public class LevelLoader {

	private String[] tiles = new String[128 * 128];
	
	public LevelLoader(File file){
		readFile(file);
	}
	
	public LevelLoader(String path){
		File file = new File(path);
		readFile(file);
	}

	private void readFile(File file) {
		String result = null;
		//TODO toplel
		interpretFile(result);
	}

	private void interpretFile(String result) {
		if(result == null){
			return;
		}else{
			if(!result.startsWith("{") && !result.endsWith("}")){
				return;
			}else{
				
			}
			//TODO toplel part 2
		}
	}
}
