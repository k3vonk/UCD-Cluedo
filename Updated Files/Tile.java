import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Tile {

    //Dimensions of the tile
    private static final double WIDTH = 21.5;
    private static final double HEIGHT = 21.5;

    private float x, y; 	  //Coordinates of the tile
    private int row, column;  //Row and column of the array

    //Tile constructor
    public Tile(float x, float y, int row, int column) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
    }

    //Accessors for (x,y) coordinates
    public float getXCord() {
        return x;
    }

    public float getYCord() {
        return y;
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
        Rectangle2D rect = new Rectangle2D.Double(x, y, WIDTH, HEIGHT); //Draw a square

        //Set color of grid lines to be invisible
        Color color = new Color(0, 0, 0, 0.8f); //Black   - 0.0f for transparent
        g2.setPaint(color);
        g2.draw(rect);
    }
}
