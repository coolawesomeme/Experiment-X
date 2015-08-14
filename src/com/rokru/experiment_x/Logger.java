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
		System.out.println("[" + channel.toUpperCase() + " - INFO] " + message);
	}
	
	public void logError(String message, int priorityLevel){
		String priority;
		if(priorityLevel <= 1) priority = "!";
		else if(priorityLevel == 2) priority = "!!";
		else priority = "!!!";
		System.out.println("[" + channel.toUpperCase() + " - ERROR] " + priority + " " + message);
	}
	
	public void logAction(String type, String cause){
		if(type.equalsIgnoreCase("button"))
			System.out.println("[" + channel.toUpperCase() + " - ACTION] Button Activated: " + cause);
		else if(type.equalsIgnoreCase("key"))
			System.out.println("[" + channel.toUpperCase() + " - ACTION] Key Activated: " + cause);
		else if(type.equalsIgnoreCase("mouse"))
			System.out.println("[" + channel.toUpperCase() + " - ACTION] Mouse Activated: " + cause);
		else
			System.out.println("[" + channel.toUpperCase() + " - ACTION] " + type + ": " + cause);
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
