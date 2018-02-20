import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Player {


    private String name; //Name of the character.
    private Color colour; //Colour of icon on board.
    private Tile position; //Position of tile

    //Adjusts player icon so it fits neatly into the middle of the tile like a good little girl :)
    private static final int ADJUST = 2;
    private static final int ADJUST_X = 5;
    private static final int ADJUST_Y = 16;

    //Oval size
    private static final int OVAL = 18;

    //Constructor for Player.
    public Player(String name, Color colour, Tile position) {
        this.name = name;
        this.colour = colour;
        this.position = position;
    }

    //Accessor method to obtain a player's name
    public String getPlayerName() {
        return this.name;
    }
       
    public Tile getPosition() {
    	return position;
    }
    
    //Returns true if matching name
    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }
    
    public void moveBy(Tile position) {
    	this.position = position;
    }

    //Method to create the player icon.
    public void drawPlayer(Graphics g) {
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
        

        gg.setColor(Color.black); //Colour for Initials.
        gg.drawString(name.substring(0, 1), position.getXCord() + ADJUST_X, position.getYCord() + ADJUST_Y); //Initials.
    }
}
