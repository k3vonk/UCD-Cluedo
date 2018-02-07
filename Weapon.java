
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A weapon class that shows what a weapon consists of
 * @author Gajun
 *
 */
public class Weapon {

	private String type; //Weapon initial
	private double x, y; //Coordinates of where it is at
	
	//Adjust coordinates such that the initial is at the middle of the tile
	private static final int adjustX = 4;
	private static final int adjustY = 18;
	
	//Constructor
	public Weapon(String type, double x, double y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	//Coordinates of what the letter is on
	public void setXCoord(double xCoord) {
		x = xCoord;
	}
	
	public void setYCoord(double yCoord) {
		y = yCoord;
	}
	
	//Draw the initials of the weapon
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font("Serif", Font.BOLD, 22); //Font serif
		g2.setFont(font);
		g2.setColor(Color.blue);  //Blue color
		
		//Draw it on a specific location
		g2.drawString(type,(int) x + adjustX,(int) y + adjustY);
	}
	
	
	
}
