import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

/**
 * A class that represents a tile on the Cluedo map
 * @author Gajun
 *
 */
@SuppressWarnings("serial")
public class Tile extends JPanel{
	
	//Dimensions of the tile
	private static final double width = 21.5; 
	private static final double height = 21.5;
	
	//Coordinates of the tile
	private double x, y;
	
	//Tile constructor
	public Tile(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	//A method to draw a tile in a given position
	public void drawMe(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
	
		//Draw a square
		Rectangle2D rect = new Rectangle2D.Double(x, y, width, height);
		/*
		//Set color of grid lines to be invisible
		Color color = new Color(1, 0, 0, 0.0f); //Red 
		g2.setPaint(color);
		*/
		g2.draw(rect);
	}
	
	//Accessors for (x,y) coordinates
	public double getYCord() {
		return y;
	}
	
	public double getXCord() {
		return x;
	}
	
}
