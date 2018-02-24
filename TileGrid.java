import java.awt.Graphics;

/**
 * A grid created by an array of Tiles that represents the board space
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */


public class TileGrid {
	public Tile[][] map; //Array of Tiles
	
	//{WALL, PATH, ROOM, ENTERANCE, SECRET};
	private int[][] property = new int[][]
							   { {0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0},
								 {2,2,2,2,2,4,0,1,1,1,2,2,2,2,1,1,1,0,2,2,2,2,2,2},
								 {2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2},
								 {2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2},
								 {2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1,1,3,2,2,2,2,2},
								 {2,2,2,2,2,2,1,1,3,2,2,2,2,2,2,3,1,1,1,2,2,2,4,0},
								 {0,2,2,2,3,2,1,1,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1},
								 {1,1,1,1,1,1,1,1,2,3,2,2,2,2,3,2,1,1,1,1,1,1,1,0},
								 {0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2},
								 {2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,3,2,2,2,2,2},
								 {2,2,2,2,2,2,2,2,1,1,0,0,0,0,0,1,1,1,2,2,2,2,2,2},
								 {2,2,2,2,2,2,2,2,1,1,0,0,0,0,0,1,1,1,2,2,2,2,2,2},
								 {2,2,2,2,2,2,2,3,1,1,0,0,0,0,0,1,1,1,2,2,2,2,3,2},
								 {2,2,2,2,2,2,2,2,1,1,0,0,2,0,0,1,1,1,1,1,1,1,1,0},
								 {2,2,2,2,2,2,2,2,1,1,0,0,2,0,0,1,1,1,2,2,3,2,2,0},
								 {2,2,2,2,2,2,3,2,1,1,0,0,2,0,0,1,1,2,2,2,2,2,2,2},
								 {0,1,1,1,1,1,1,1,1,1,0,0,3,0,0,1,1,3,2,2,2,2,2,2},
								 {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
								 {0,1,1,1,1,1,1,1,1,2,2,3,3,2,2,1,1,1,2,2,2,2,2,0},
								 {4,2,2,2,2,2,3,1,1,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1},
								 {2,2,2,2,2,2,2,1,1,2,2,2,2,2,3,1,1,1,1,1,1,1,1,0},
								 {2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,1,1,3,2,2,2,2,2,4},
								 {2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2},
								 {2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2},
								 {2,2,2,2,2,2,0,1,0,2,2,2,2,2,2,0,1,0,2,2,2,2,2,2}
																					};
	
	//Array size
	private static final int COLUMN = 24;
	private static final int ROW = 25;

	//Grid offset
	private float x = 42.5f;
	private float y;
	
	//Constructor that builds the array of tiles
	public TileGrid() {	
		map = new Tile[ROW][COLUMN];  //Dimension of the Board
		for (int i = 0; i < map.length; i++) {
			y += 23;
			x = 42.5f;
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = new Tile(x, y, i, j, property[i][j]);
				x += 23;
			}
		} 
	}

	//Draw the grid tiles onto the map
	public void drawGrid(Graphics g) {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j].drawTile(g);
			}
		}
	}
}
