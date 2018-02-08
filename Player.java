/**
 * A class that represents players on a board
 * 
 * @Author Richard, Gajun
 */
import java.awt.*;

@SuppressWarnings("serial")
public class Player extends Token{

    private String name; //Name of the character.
    private Color colour; //Colour of icon on board.

    //Adjusts player icon so it fits neatly into the middle of the tile like a good little girl :)
    private static final int ADJUST = 2;
    private static final int ADJUSTX = 5;
    private static final int ADJUSTY = 16;
    
    //Oval size
    private static final int OVAL = 18;

    //Constructor for Player.
  	public Player(String name, Color colour, Tile position) {
  		super(position);
  		this.name = name;
  		this.colour = colour;
  		this.position = position;
  	}
  	
    //Method to create the player icon.
    public void drawPlayer(Graphics g) {
        Graphics2D gg = (Graphics2D) g;
        
        //Font for initials. (Don't submit until we find Comic Sans MS ty.)
        Font font = new Font("Comic Sans MS", Font.ITALIC, 15);
        gg.setFont(font);

        gg.setColor(colour); //Colour for Icon
        gg.fillOval((int) position.getXCord() + ADJUST, (int) position.getYCord()+ ADJUST, OVAL, OVAL); //Circle icon.

        gg.setColor(Color.black); //Colour for Initials.
        gg.drawString(name.substring(0,1), (int) position.getXCord() + ADJUSTX, (int) position.getYCord() + ADJUSTY); //Initials.
    }

}
