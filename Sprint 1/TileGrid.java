import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
@SuppressWarnings("serial")
public class TileGrid extends JComponent {
    public Tile[][] map; //Array of Tiles

    //Array size
    private static final int COLUMN = 24;
    private static final int ROW = 25;

    //Starting coordinates for the tile
    private double x = 42.5;
    private double y = 23.25;

    //Constructor that builds the array of tiles
    public TileGrid() {
        map = new Tile[COLUMN][ROW];  //Dimension of the Board

        for (int i = 0; i < map.length; i++) {
            y = 23.25;
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = new Tile(x, y, j, i);
                y += 23;
            }

            x += 23;
        }
    }

    //Draw the tiles
    public void drawGrid(Graphics g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j].drawTile(g);
            }
        }
    }
}
