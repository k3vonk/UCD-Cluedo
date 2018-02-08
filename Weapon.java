
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A weapon class
 * @author Gajun
 *
 */
@SuppressWarnings("serial")
public class Weapon extends Token{
	
	private String type; //Type of weapon
	
	//Adjust coordinates such that the initial is at the middle of the tile
	private static final int adjustX = 4;
	private static final int adjustY = 18;
	
	//Constructor
	public Weapon(String type, Tile position) {
		super(position);
		this.type = type;
	}
	
	//Draw the initials of the weapon
	public void drawWeapon(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Font font = new Font("Serif", Font.BOLD, 22); //Font serif
		g2.setFont(font);
		g2.setColor(Color.blue);  //Blue color
		
		//Draw it on a specific location
		g2.drawString(type,(int) position.getXCord() + adjustX,(int) position.getYCord() + adjustY);
	}
	
}
