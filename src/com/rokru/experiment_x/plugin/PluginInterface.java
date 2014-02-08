package com.rokru.experiment_x.plugin;

public interface PluginInterface {

	/** The plugin's proper name 
	 * (not including version) */
	public String getPluginName();

	/** The plugin's version */
	public String getVersion();

	/** Name of who made the plugin 
	 * (can be returned null, and will be disregarded) */
	public String getAuthor();

	/** Minimum version of the game the plugin needs to work 
	 * (can be returned null, and will be disregarded) */
	public String getMinimumGameVersion();

	/** Code to execute when plugin is started */
	public void onPluginStart();

	/** Code to execute when/ before plugin is stopped */
	public void onPluginStop();

}
