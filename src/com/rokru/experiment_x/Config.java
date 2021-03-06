package com.rokru.experiment_x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Properties;

public class Config {

	private static HashMap<String, String> configValueMap = new HashMap<String, String>();
	
	public Config(){
		//put in default values
		configValueMap.put("guiBar", "true");
		configValueMap.put("titleBar", "" + ExperimentX.titleBar);
		configValueMap.put("saveTimer", "" + ExperimentX.saveTimer);
		
		if(new File(ExperimentX.getDirectory() + "/options.properties").exists()){
			for(int i = 0; i<configValueMap.size();i++){
				//read already existing values
				configValueMap.put((String) configValueMap.keySet().toArray()[i], getValue((String) configValueMap.keySet().toArray()[i]));
			}
		}else{
			//generate new config file
			Properties prop = new Properties();
			OutputStream output = null;
			try {
				output = new FileOutputStream(ExperimentX.getDirectory() + "/options.properties");
				// set the properties value
				for(int i = 0; i<configValueMap.size();i++){
					prop.setProperty((String) configValueMap.keySet().toArray()[i], configValueMap.get(configValueMap.keySet().toArray()[i]));
				}
				//save properties
				prop.store(output, null);
			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}		 
			}
		}
	}
	
	public static void reload(){
		for(int i = 0; i<configValueMap.size();i++){
			configValueMap.put((String) configValueMap.keySet().toArray()[i], getValue((String) configValueMap.keySet().toArray()[i]));
		}
	}
	
	private static String getValue(String key){
		Properties prop = new Properties();
		InputStream input = null;
		String s = null;
		try {
			input = new FileInputStream(ExperimentX.getDirectory() + "/options.properties");
			//load a properties file
			prop.load(input);
			//get the property value
			if(!prop.containsKey(key))
				setValue(key, configValueMap.get(key));
			s = prop.getProperty(key, configValueMap.get(key));
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return s;
	}
	
	public static void setValue(String key, String value){
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream(ExperimentX.getDirectory() + "/options.properties");
			//set the properties value
			for(int i = 0; i < configValueMap.size(); i++){
				prop.setProperty((String) configValueMap.keySet().toArray()[i], configValueMap.get(configValueMap.keySet().toArray()[i]));
			}
			prop.setProperty(key, value);
			//save properties
			prop.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//update config value map
		configValueMap.put(key, value);
	}
	
	public static String getProperty(String property){
		return configValueMap.containsKey(property) ? configValueMap.get(property) : null;
	}
}
