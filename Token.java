import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A Token class that contains the token and the player token associated with the token
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class Token {


    private String token; //Name of token
   // private String name; //Name of player
    private Color colour; //Colour of icon on board.
    private Tile position; //Position of tile

    //Adjusts player icon so it fits neatly into the middle of the tile like a good little girl :)
    private static final int ADJUST = 2;
    private static final int ADJUST_X = 5;
    private static final int ADJUST_Y = 16;

    //Oval size
    private static final int OVAL = 18;

    //Constructor for token without players
    public Token(String token, Color colour, Tile position) {
        this.token = token;
        this.colour = colour;
        this.position = position;
       // this.name = name;
    }
    
    //Accessor method to obtain a player's token
    public String getTokenToken() {
        return this.token;
    }
    
    //Returns the token name
    public String getTokenName() {
    	return this.token;
    }
    
    //Tile accessor
    public Tile getPosition() {
    	return position;
    }
    
    //Moving Tiles
    public void moveBy(Tile position) {
    	this.position = position;
    }

    
    //Returns true if matching token
    public boolean hasName(String token) {
        return this.token.toLowerCase().equals(token.toLowerCase().trim());
    }
    
    //Method to create the player icon.
    public void drawToken(Graphics g) {
        Graphics2D gg = (Graphics2D) g;

        //Font for initials.
        Font font = new Font("default", Font.BOLD, 14);
        gg.setFont(font);

        //Oval background
        gg.setColor(Color.lightGray); 
        gg.fillOval((int) position.getXCord() + ADJUST, (int) position.getYCord() + ADJUST, OVAL,OVAL);
        
        //Oval foreground
        gg.setColor(colour);
        gg.fillOval((int) position.getXCord() + ADJUST + 1, (int) position.getYCord() + ADJUST + 1, OVAL - 2,OVAL - 2);
        
        //Draw initial onto the Oval
        gg.setColor(Color.black); 
        gg.drawString(token.substring(0, 1), position.getXCord() + ADJUST_X, position.getYCord() + ADJUST_Y); //Initials.
    }
}
