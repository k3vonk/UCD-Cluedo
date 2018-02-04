import java.awt.Graphics;
/**
 * A basic board panel
 */
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel{

	//Graphic objects
	Board test = new Board();
	TileGrid test3 = new TileGrid();
	
	//Draw the objects together
	public void paintComponent(Graphics g) {
		 test.drawMe(g);
		 test3.drawMe(g);
	}
}
