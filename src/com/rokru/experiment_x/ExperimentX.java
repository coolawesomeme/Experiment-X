package com.rokru.experiment_x;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.rokru.experiment_x.entity.mob.Player;
import com.rokru.experiment_x.graphics.Render;
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
    private JFrame frame;
    private Keyboard key;
    private Level level;
    private Player player;
    private boolean running = false;
    
    private Render screen;
    
    private BufferedImage image = new BufferedImage (width, height, BufferedImage.TYPE_INT_RGB);
    private int [] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    
    public ExperimentX() {
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        
        screen = new Render(width, height);
        frame = new JFrame();
        key = new Keyboard();
        level = new RandomLevel(64, 64);
        player = new Player(key);
        
        addKeyListener(key);
        
    }

    public static void main(String[] args) {
        ExperimentX x = new ExperimentX();
        x.frame.setResizable(false);
        x.frame.setTitle(gameVersionFormatted);
        x.frame.add(x);
        x.frame.pack();
        x.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        x.frame.setLocationRelativeTo(null);
        x.frame.setVisible(true);
        
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
    	int frames = 0;
    	int updates = 0;
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
            render();
            frames++;
            
            if (System.currentTimeMillis() - timer > 1000) {
            	timer += 1000;
            	System.out.println(updates + "ups, " + frames + " fps");
            	frame.setTitle(title + "  |  "  + updates + " ups, " + frames + " fps");
            	updates = 0;
            	frames = 0;
            }
        }
        stop();
    }  
    
    public void update() {
    	key.update();
    	player.update();
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
    	g.dispose();
    	bs.show();
    }
}