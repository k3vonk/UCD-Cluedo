/*
Class for the players and stuff like that.
*/
import javax.swing.*;
import java.awt.*;

public class Player extends JPanel{

    private String name; //Name of the character.
    private double x,y; //X Y coordinates on the board.
    private Color colour; //Colour of icon on board.

    //Adjusts player icon so it fits neatly into the middle of the tile like a good little girl :)
    private static final int adjustX = 2;
    private static final int adjustY = 2;

    //Constructor for Player.
    public Player( String name, double x, double y, Color colour){

        this.name = name;
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    //Methods to adjust coordinates of players.
    public void moveX(int X){
        this.x = this.x - X;
    }
    public void moveY(int Y){
        this.y = this.y - Y;
    }

    //Method to create the player icon.
    public void paint(Graphics g) {
        Graphics2D gg = (Graphics2D) g;
        //Font for initials. (Don't submit until we find Comic Sans MS ty.)
        Font font = new Font("Comic Sans MS", Font.ITALIC, 15);
        gg.setFont(font);

        gg.setColor(colour); //Colour for Icon
        gg.fillOval((int)x+adjustX,(int)y+adjustY,18,18); //Circle icon.

        gg.setColor(Color.black); //Colour for Initials.
        gg.drawString(name.substring(0,1),(int)x+5,(int)y+16); //Initials.
    }


}
