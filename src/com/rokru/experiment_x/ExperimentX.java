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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import com.rokru.experiment_x.gui.MainMenu;
import com.rokru.experiment_x.gui.OptionsMenu;
import com.rokru.experiment_x.gui.PauseMenu;
import com.rokru.experiment_x.input.Keyboard;
import com.rokru.experiment_x.level.Coordinates;
import com.rokru.experiment_x.level.LevelLoader;
import com.rokru.experiment_x.level.Level;
import com.rokru.experiment_x.level.PlayerInfoSaver;

public class ExperimentX extends Canvas implements Runnable {
	
    private static final long serialVersionUID = 1L;
    public final static String title = "Experiment X";
    public final static String gameVersion = "0.0.1";
    public final static String gameVersionFormatted = title + " - v" + gameVersion;
    
    public final static int width = 300;
    public final static int height = 168;
    public final static int scale = 3;
    
    public static int currentMenu;
    public static int borderWidth = 0, borderHeight = 0;
    
    public static MainMenu mainMenuInstance;
    private static Thread gameThread;
    
    private static JFrame frame;
    private Keyboard key;
    public static Level level;
    public static Player player;
    public static String username;
    private static boolean running = false;
    
    private int frames, f2 = 0;
    private int updates, u2 = 0;
    
    private static int pauseRender = 0;
    
    private Render screen;
    
    public static boolean debug = false;
	public static boolean hidegui = false;
	
    public static boolean titleBar = true;
	public static int saveTimer = 4;
    
    private BufferedImage image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
    private int [] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    public ExperimentX() {
    	if(!titleBar)
    		borderWidth = borderHeight = 7;
    	Dimension size = new Dimension(width * scale, height * scale);
        setSize(size);
        screen = new Render(width, height);
        frame = new JFrame();
        key = new Keyboard();
        //level = new SpawnLevel("/level/spawn_level.png");
        level = new LevelLoader("/level/spawn_level.map", 128, 64);
    	Coordinates coords = PlayerInfoSaver.getPlayerCoords();
    	if(coords == null){
    		coords = new Coordinates(level.getLevelWidth()/2, level.getLevelHeight()/2, player);
    		PlayerInfoSaver.savePlayerCoords(coords);
    	}
        player = new Player(level, coords.getX()*16 + 8, coords.getY()*16 - 1, key, username);
        player.setCurrentTile(level.getTile(new Coordinates(player.tileX, player.tileY)));
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image cursorI = new ImageIcon(ExperimentX.class.getResource(("/images/cursor_main.png"))).getImage();
        Cursor cursor = toolkit.createCustomCursor(cursorI, new Point(0,0), "cursor_1");
        setCursor(cursor);
        
        addKeyListener(key);
        
        addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent e){
        		if(currentMenu != PauseMenu.menuID){
        			currentMenu = PauseMenu.menuID;
        			Logger.generalLogger.logInfo("Focus lost");
        			Logger.generalLogger.logInfo("Pause menu opened");
        		}
        	}});
        
		addMouseListener(new MouseAdapter() {
		     public void mouseReleased(MouseEvent e) {
		        if(PauseMenu.paused && !OptionsMenu.menuOpen){
		        	PauseMenu.requestFocus();
		        	Logger.generalLogger.logAction("mouse", "Click (while paused)");
		        }else if(PauseMenu.paused && OptionsMenu.menuOpen){
		        	OptionsMenu.requestFocus();
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
			Logger.xLogger.logError("Launcher not detected, going to exit.", 2);
			System.exit(0);
		}else if(parameters.contains("-v") || parameters.contains("-version")){
			Logger.xLogger.logPlain(gameVersion);
			System.exit(0);
		}
		Logger.generalLogger.logPlain("===========================================");
		Logger.generalLogger.logPlain(gameVersionFormatted.toUpperCase());
		Logger.generalLogger.logPlain("===========================================");
		new Config();
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
		titleBar = Boolean.parseBoolean(Config.getProperty("titleBar"));
		saveTimer = Integer.parseInt(Config.getProperty("saveTimer"));
		ExperimentX x = new ExperimentX();
		ExperimentX.frame.setResizable(false);
		ExperimentX.frame.setTitle(gameVersionFormatted);
		ExperimentX.frame.setIconImage(new ImageIcon(ExperimentX.class.getResource("/images/app_icon.png")).getImage());
		mainMenuInstance = new MainMenu(x, frame);
		if(!titleBar){
			Logger.generalLogger.logInfo("Launching without title bar");
			ExperimentX.frame.getContentPane().setBackground(new Color(0xff002747));
			mainMenuInstance.setBounds(borderWidth, borderHeight, width*scale, height*scale);
			ExperimentX.frame.setSize(new Dimension(width*scale + 2*borderWidth, height*scale + 2*borderHeight));
			ExperimentX.frame.add(mainMenuInstance);
		}else{
			ExperimentX.frame.add(mainMenuInstance);
		}
		ExperimentX.frame.setUndecorated(!titleBar);
		if(titleBar){
			ExperimentX.frame.pack();
		}
    	ExperimentX.frame.setLayout(null);
		ExperimentX.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ExperimentX.frame.setLocationRelativeTo(null);
		ExperimentX.frame.setVisible(true);
		new ThreadHandler();
		//m.start();
    }

	public synchronized void start(){
        running = true;
        gameThread = new Thread(this, "Game");
        gameThread.start();
    }
    
    private synchronized static void stop(){
        running = false;
        ThreadHandler.closeThread(gameThread);
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
					Logger.xLogger.logInfo(u2 + "ups, " + f2 + " fps");
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
        		if(pauseRender < 2){
        			render();
        			pauseRender++;
        			frames = 0;
        		}
        		updates = 0;
        	}
        }
        stop();
    }  
    
    public void update() {
    	key.update();
    	player.update();
    	if(currentMenu == PauseMenu.menuID){
    		PauseMenu.openPauseMenu();
    		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    		ExperimentX.frame.getContentPane().setBackground(new Color(0xff1a1a1a));
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
    	g.drawImage(image, 0, 0, width*scale, height*scale, null);
    	if(!hidegui){
    		g.setFont(getDefaultFont(Font.BOLD, 14, 1));
    		if(Boolean.parseBoolean(Config.getProperty("guiBar")) && !debug){
    			g.setColor(new Color(0f, 0f, 0f, 0.15f));
    			g.fillRect(0, 0, 5 + g.getFontMetrics().stringWidth(username) + 6, 22);
    		}else if(Boolean.parseBoolean(Config.getProperty("guiBar")) && !PauseMenu.paused){
    			g.setColor(new Color(0f, 0f, 0f, 0.15f));
    			g.fillRect(0, 0, width*scale, 22);
    			g.fillRect(0, 22, g.getFontMetrics().stringWidth("Tile: " + player.currentTile.getFormattedTileName() + " (id " + player.currentTile.getTileID() + ")") + 15, 32);
    		}
    	
    		g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.7f));
    		g.drawString(username, 5, 16);
    	
    		if(PauseMenu.paused){
    			g.setColor(new Color(0f, 0f, 0f, 0.6f));
        		g.fillRect(0, 0, width*scale, height*scale);
        		if(currentMenu == OptionsMenu.menuID){
        			g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.8f));
        			g.setFont(getDefaultFont(Font.BOLD, 40));
        			g.drawString("PAUSED", width*scale/2 - g.getFontMetrics().stringWidth("PAUSED")/2, height*scale / 2);
        		}
    		}else if(debug){
    			g.setColor(new Color(1.0f, 1.0f, 1.0f, 0.7f));
    			g.drawString(u2 + " ups, " + f2 + " fps", width*scale - 5 - g.getFontMetrics().stringWidth(u2 + " ups, " + f2 + " fps") , 16);
    			g.drawString("Tile: " + player.currentTile.getFormattedTileName() + " (id " + player.currentTile.getTileID() + ")", 5, 32);
    			g.drawString("(" + player.tileX + ", " + player.tileY + ")", 5, 48);
    		}
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
		pauseRender = 0;
		ExperimentX.frame.getContentPane().setBackground(new Color(0xff002747));
	}
	
	public static void debugMode(boolean turnOnDebug){
		debug = turnOnDebug;
	}
	
	public static void setCurrentMenu(int x){
		currentMenu = x;
		pauseRender = 0;
	}

	public static void hideGui(boolean hideGui) {
		hidegui = hideGui;
	}
	
	public static void endGameEvent(){
		saveGame();
	}

	public static void saveGame() {
		PlayerInfoSaver.savePlayerCoords(new Coordinates(player.tileX, player.tileY, player));
	}
}