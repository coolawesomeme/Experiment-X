package com.rokru.experiment_x;

public class Logger {

	private String channel;

	protected static Logger xLogger = new Logger("experimentx");
	public static Logger generalLogger = new Logger("general");
	public static Logger playerLogger = new Logger("player");
	
	public Logger(String channel){
		this.setChannel(channel);
	}
	
	public void logPlain(String message){
		System.out.println(message);
	}
	
	public void logInfo(String message){
		System.out.println("[INFO] " + message);
	}
	
	public void logError(String message){
		System.out.println("[ERROR] " + message);
	}
	
	public void logButtonEvent(String buttonName){
		System.out.println("[ACTION] Button Activated: " + buttonName);
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
