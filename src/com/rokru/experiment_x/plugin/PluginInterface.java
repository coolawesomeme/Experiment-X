package com.rokru.experiment_x.plugin;

public interface PluginInterface {

	public String getPluginName();
	
	public String getVersion();
	
	public String getAuthor();
	
	public String getMinimumGameVersion();
	
	public void onPluginStart();
	
	public void onPluginStop();

}
