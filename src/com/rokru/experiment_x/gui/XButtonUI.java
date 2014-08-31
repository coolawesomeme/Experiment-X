package com.rokru.experiment_x.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalButtonUI;

public class XButtonUI extends MetalButtonUI{

	/*public void paint(Graphics g, JComponent c){
		((AbstractButton) c).setBorderPainted(false);
    	g.setColor(new Color(0xff003E85));
    	g.fillRect(0, 0, c.getWidth(), c.getHeight());
		Image img = new ImageIcon(this.getClass().getResource("/images/button_unpressed.png")).getImage();
        g.drawImage(img, 0, 0, c.getWidth(), c.getHeight(), null);
        super.paint(g, c);
	}*/
	
    protected void paintButtonPressed(Graphics g, AbstractButton b)
    {
    	b.setBorderPainted(false);
    	g.setColor(new Color(0xff003E85));
    	g.fillRect(0, 0, b.getWidth(), b.getHeight());
    	Image img = new ImageIcon(this.getClass().getResource("/images/button_pressed.png")).getImage();
        g.drawImage(img, 0, 0, b.getWidth(), b.getHeight(), null);
        b.setForeground(Color.WHITE);
    }

    public void update(Graphics g, JComponent c) {
    	AbstractButton button = (AbstractButton)c;
    	ButtonModel bm = button.getModel();
    	button.setBorderPainted(false);
    	g.setColor(new Color(0xff003E85));
    	g.fillRect(0, 0, c.getWidth(), c.getHeight());
    	if(bm.isRollover()){
    		Image img = new ImageIcon(this.getClass().getResource("/images/button_rollover.png")).getImage();
            g.drawImage(img, 0, 0, c.getWidth(), c.getHeight(), null);
            c.setForeground(Color.WHITE);
            super.paint(g, c);
    	}else{
    		Image img = new ImageIcon(this.getClass().getResource("/images/button_unpressed.png")).getImage();
            g.drawImage(img, 0, 0, c.getWidth(), c.getHeight(), null);
            c.setForeground(Color.BLACK);
            super.paint(g, c);
    	}
    }
}
