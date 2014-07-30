package com.rokru.experiment_x;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.rokru.experiment_x.entity.mob.Player;
import com.rokru.experiment_x.graphics.Render;
import com.rokru.experiment_x.gui.pause.PauseMenu;
import com.rokru.experiment_x.input.Keyboard;
import com.rokru.experiment_x.level.Level;
import com.rokru.experiment_x.level.RandomLevel;

public class ExperimentX extends Canvas implements Runnable{
    private static final long serialVersionUID = 1L;
    
    public static String gameVersion = "0.0.1";
    public static String gameVersionFormatted = "Experiment X - v" + gameVersion;

    public static int width = 300;
    public static int height = 168;
    public static int scale = 3;
    public static String title = "Experiment X";
    
    private Thread gameThread;
    private static JFrame frame;
    private Keyboard key;
    public static Level level;
    public static Player player;
    public static String username;
    private boolean running = false;
    
    private int frames, f2 = 0;
    private int updates, u2 = 0;
    
    private Render screen;
    
    public static boolean debug = false;
    
    private BufferedImage image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
    private int [] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    public ExperimentX() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        
        screen = new Render(width, height);
        frame = new JFrame();
        key = new Keyboard();
        level = new RandomLevel(128, 128);
        player = new Player(level, key, username);
        player.initLevel(level);
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorI = new ImageIcon(ExperimentX.class.getResource(("/images/cursor_main.png"))).getImage();
        Cursor cursor = toolkit.createCustomCursor(cursorI, new Point(0,0), "cursor_1");
        setCursor(cursor);
        
        addKeyListener(key);
        
		addMouseListener(new MouseAdapter() {
		     public void mouseReleased(MouseEvent e) {
		        if(PauseMenu.paused){
		        	PauseMenu.requestFocus();
		        	Logger.generalLogger.logAction("mouse", "Click (while paused)");
		        }
		     }
		});
    }

    public static void main(String[] args) {
    	List<String> parameters = new ArrayList<String>();
		for(String s : args){
			parameters.add(s);
		}
		if(parameters.isEmpty()){
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch (Exception e) {}
			JOptionPane.showMessageDialog(null, new JLabel("Please use the official (or a custom) launcher to open the game.", JLabel.CENTER), "Error", JOptionPane.ERROR_MESSAGE);
			Logger.xLogger.logError("Launcher not detected, going to exit.");
			System.exit(0);
		}else if(parameters.contains("-v") || parameters.contains("-version")){
			Logger.xLogger.logPlain(gameVersion);
			System.exit(0);
		}
		Random random = new Random();
		username = "Player" + random.nextInt(999);
		if(parameters.size() > 0){
			for (String q : parameters){
				if (q.startsWith("-user:")){
					username = parameters.get(parameters.indexOf(q)).split(":")[1];
				    break;
				}
			}
		}
		Logger.playerLogger.logInfo("Current Username: " + username);
		makeDirectories();
		ExperimentX x = new ExperimentX();
		ExperimentX.frame.setResizable(false);
		ExperimentX.frame.setTitle(gameVersionFormatted);
		ExperimentX.frame.setIconImage(new ImageIcon(ExperimentX.class.getResource("/images/app_icon.png")).getImage());
		ExperimentX.frame.add(x);
		ExperimentX.frame.pack();
		ExperimentX.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ExperimentX.frame.setLocationRelativeTo(null);
		ExperimentX.frame.setVisible(true);
		
		x.start();
    }

    public synchronized void start(){
        running = true;
        gameThread = new Thread(this, "Game");
        gameThread.start();
    }
    
    public synchronized void stop(){
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void run(){
    	long lastTime = System.nanoTime();
    	long timer = System.currentTimeMillis();
    	final double ns = 1000000000.0 / 60.0;
    	double delta = 0;
    	requestFocus();
        while(running) {
        	long now = System.nanoTime();
        	delta += (now - lastTime) / ns;
        	lastTime = now;
        	while (delta >= 1) {
        		update();
        		updates++;
        		delta--;
        	}
        	if(!PauseMenu.paused){
        		render();
        		frames++;
            
        		if(!debug && System.currentTimeMillis() % 600 == 0){
					Logger.playerLogger.logInfo("COORDS: (" + player.tileX + ", " + player.tileY + ")");
				}
        		
        		if (System.currentTimeMillis() - timer > 1000) {
        			timer += 1000;
        			if(debug){
        				Logger.xLogger.logInfo(updates + "ups, " + frames + " fps");
        			}
        			u2 = updates;
        			f2 = frames;
        			updates = 0;
        			frames = 0;
        		}
        	}else{
        		render();
        	}
        }
        stop();
    }  
    
    public void update() {
    	key.update();
    	player.update();
    	if(PauseMenu.paused){
    		PauseMenu.openPauseMenu();
    		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	}
    }
    
    public void render() {
    	BufferStrategy bs = getBufferStrategy();
    	if (bs == null) {
    		createBufferStrategy(3);
    		return;
    	}
    	screen.clear();
    	int xScroll = player.x - screen.width / 2;
    	int yScroll = player.y - screen.height / 2;
    	level.render(xScroll, yScroll, screen);
    	player.render(screen);
    	
    	for (int i = 0; i < pixels.length; i++) {
    		pixels[i] = screen.pixels[i];
    	}
    	
    	Graphics g = bs.getDrawGraphics();
    	g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    	g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.7f));
    	g.setFont(getDefaultFont(Font.BOLD, 14, 1));
    	g.drawString(username, 5, 16);
    	
    	if(PauseMenu.paused){
    		g.setColor(new Color(0f, 0f, 0f, 0.6f));
        	g.fillRect(0, 0, width*scale, height*scale);
        	//g.setColor(Color.WHITE);
        	//g.setFont(getDefaultFont(Font.BOLD, 40));
        	//Rectangle2D fontBox = g.getFont().getStringBounds("PAUSED", g.getFontMetrics().getFontRenderContext());
        	//g.drawString("PAUSED", width*scale/2 - (int)fontBox.getWidth()/2, height*scale / 2);
    	}else if(debug){
    		g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.7f));
    		g.setFont(getDefaultFont(Font.BOLD, 14, 1));
    		g.drawString(u2 + " ups, " + f2 + " fps", width*scale - 5 - 105 , 16);
    	}
    	g.dispose();
    	bs.show();
    }
    
    private static void makeDirectories() {
    	File f = new File(getDirectory());
		if(f.mkdirs()){
			Logger.xLogger.logInfo("Game File Folder created at:");
			Logger.xLogger.logInfo(f.getAbsolutePath());
		}
	}

	public static String getDirectory(){
		if(System.getProperty("user.home") != null){
			return System.getProperty("user.home") + "/.experimentx/game";
		}else{
			return ".experimentx/game";
		}
	}
	
	public static String getLauncherDirectory(){
		if(System.getProperty("user.home") != null){
			return System.getProperty("user.home") + "/.experimentx/launcher";
		}else{
			return ".experimentx/launcher";
		}
	}
	
	public static Font getDefaultFont(int fontType, int fontSize){
		try{
			return new Font("Arial", fontType, fontSize);
		}catch(Exception e){
			return new Font(UIManager.getFont("Label.font").getFontName(), fontType, fontSize);
		}
	}
	
	public static Font getDefaultFont(int fontType, int fontSize, int reformatIfDefault){
		boolean flag = false;
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String []fonts=g.getAvailableFontFamilyNames();
            for (int i = 0; i < fonts.length; i++) {
            	if(fonts[i].equals("Arial")){
            		flag = true;
            		break;
            	}
            }
		if(flag){
			return new Font("Arial", fontType, fontSize);
		}else{
			if(reformatIfDefault <= 1)
				return new Font(UIManager.getFont("Label.font").getFontName(), Font.PLAIN, fontSize);
			else if(reformatIfDefault == 2)
				return new Font(UIManager.getFont("Label.font").getFontName(), Font.BOLD, fontSize);
			else if(reformatIfDefault == 3)
				return new Font(UIManager.getFont("Label.font").getFontName(), Font.ITALIC, fontSize);
			else
				return new Font(UIManager.getFont("Label.font").getFontName(), Font.PLAIN, fontSize);
		}
	}
	
	public static void pauseMenuClosed(){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void debugMode(boolean turnOnDebug){
		debug = turnOnDebug;
	}
}