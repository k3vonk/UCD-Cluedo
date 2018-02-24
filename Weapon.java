import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * A weapon class which represents the possible murder weapon
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class Weapon {
    private String type; //Type of weapon
    private Tile position; //Position of weapon

    //Adjust coordinates such that the initial is at the middle of the tile
    private static final float X_OFFSET = 6f;
    private static final float Y_OFFSET = 16f;
    private static final int S_OFFSET = 2;
    private static final int SQUARE = 18;
    
    //Constructor
    public Weapon(String type, Tile position) {
    	this.position = position;
        this.type = type;
    }

    //Accessor method to obtain a weapon's name.
    public String getWeaponName() {
        return type;
    }
    
    //Weapon's position
    public Tile getPosition() {
    	return position;
    }
    
    public void moveBy(Tile position) {
    	this.position = position;
    }
    
    //If name is same
    public boolean hasName(String type) {
        return this.type.toLowerCase().equals(type.toLowerCase().trim());
    }

    //Draw the initials of the weapon
    public void drawWeapon(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        
        //Background of weapon
        Rectangle2D.Double rectangleBlack = new Rectangle2D.Double(position.getXCord() + S_OFFSET, position.getYCord() + S_OFFSET, SQUARE , SQUARE);
        g2.fill(rectangleBlack);
        
        //Foreground of weapon
        Rectangle2D.Double rectangleColor = new Rectangle2D.Double(position.getXCord() + S_OFFSET + 2, position.getYCord() + S_OFFSET + 2, SQUARE - 4 , SQUARE - 4);
        g2.setColor(Color.lightGray);
        g2.fill(rectangleColor);
        
        //Weapon initial
        Font font = new Font("Comic Sans MS", Font.BOLD, 14); //Font Comic Sans
        g2.setFont(font);
        g2.setColor(Color.yellow);  //Blue color
        
        g2.drawString(type.substring(0, 1),  position.getXCord() + X_OFFSET, position.getYCord() + Y_OFFSET);
    }
}
