import java.awt.Graphics;

/**
 * A class that makes an array of tiles
 * @author Gajun
 *
 */
public class TileGrid {
	public Tile[][] map; //Array of Tiles
	
	private double x, y; //Coordinates for the start of each tile
	
	//Constructor that builds the array of tiles
	public TileGrid() {
		map = new Tile[24][25];
		x = 42.5;
		for(int i = 0; i < map.length; i++) {
			y = 23.25;
			for(int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(x, y);
				y += 23;
			}
			
			x += 23;
		}
	}
	
	//Draw the tiles
	public void drawMe(Graphics g) {
		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				map[i][j].drawMe(g);
			}
		}
	}
}
