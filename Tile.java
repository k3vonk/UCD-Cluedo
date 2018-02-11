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
public class Tile extends JPanel{
	
	//Dimensions of the tile
	private static final double WIDTH = 21.5; 
	private static final double HEIGHT = 21.5;
	
	//Coordinates of the tile
	private double x, y;
	
	//Row and column of the array
	private int row,column;
	
	//Tile constructor
	public Tile(double x, double y, int row, int column) {
		this.x = x;
		this.y = y;
		this.row = row;
		this.column = column;
	}
		
	//Accessors for (x,y) coordinates
	public double getYCord() {
		return y;
	}
	
	public double getXCord() {
		return x;
	}
	
	//Accessors for [row,column]
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	//A method to draw a tile in a given position
	public void drawTile(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	
		//Draw a square
		Rectangle2D rect = new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
		
		//Set color of grid lines to be invisible
		//Color color = new Color(0, 0, 0, 0.8f); //Black
		Color color = Color.white;
		g2.setPaint(color);
		
		//g2.draw(rect);
	}
}
