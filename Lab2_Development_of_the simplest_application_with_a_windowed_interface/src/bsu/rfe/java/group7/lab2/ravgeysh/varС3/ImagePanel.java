package bsu.rfe.java.group7.lab2.ravgeysh.varС3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final BufferedImage img;
	
	public ImagePanel(String path) {
		try {
			img = ImageIO.read(new File(path));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img != null)
			g.drawImage(img, 0,0,getWidth(),getHeight(),Color.white,null);
	}
}