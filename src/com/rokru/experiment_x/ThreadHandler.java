package com.rokru.experiment_x;

public class ThreadHandler implements Runnable{

	public static Thread saveThread;
	
	public ThreadHandler(){
		startAllThreads();
	}
	
	public void startAllThreads(){
		saveThread = new Thread(this, "AutoSaver");
		startThread(saveThread);
		saveThread.setPriority(Thread.MIN_PRIORITY);
	}

	public static void startThread(Thread thread){
		thread.start();
	}
	
	public static void closeAllThreads(){
		closeThread(saveThread);
	}
	
	public static void closeThread(Thread thread){
		try{
			thread.join();
			thread = null;
		}catch(Exception e){
			e.printStackTrace();
			thread = null;
		}
	}

	@Override
	public void run() {
		while(Thread.currentThread() == ThreadHandler.saveThread){
			ExperimentX.saveGame();
			Logger.generalLogger.logAction("Save Thread", "Game saved");
			try {
				Thread.sleep(20 * 60 * 60 * ExperimentX.saveTimer);
			} catch (InterruptedException e) {
				e.printStackTrace();
				closeThread(ThreadHandler.saveThread);
			}
		}
	}
}
