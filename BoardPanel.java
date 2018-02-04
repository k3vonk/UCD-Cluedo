import java.awt.Graphics;
/**
 * A basic board panel
 */
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel{

	public Weapon[] logo = new Weapon[6];
	//Graphic objects
	Board test = new Board();
	TileGrid test3 = new TileGrid();

	//Candlestick, Dagger, Lead Pipe, Revolver, Rope, Spanner
	public void createWeapons() {
		logo[0] = new Weapon("C", test3.map[21][1].getXCord(), test3.map[21][1].getYCord()); //Candlestick
		logo[1] = new Weapon("D", test3.map[2][21].getXCord(), test3.map[2][21].getYCord()); //Dagger
		logo[2] = new Weapon("L", test3.map[1][12].getXCord(), test3.map[1][12].getYCord()); //Lead pipe
		logo[3] = new Weapon("Re", test3.map[21][24].getXCord(), test3.map[21][24].getYCord()); //Revolver
		logo[4] = new Weapon("R", test3.map[19][16].getXCord(), test3.map[19][16].getYCord()); //Rope
		logo[5] = new Weapon("S", test3.map[2][2].getXCord(), test3.map[2][2].getYCord()); //Spanner
	}
	
	//Draw the objects together
	public void paintComponent(Graphics g) {
		createWeapons();
		
		 test.drawMe(g);
		 test3.drawMe(g);
		 logo[0].paint(g);
		 logo[1].paint(g);
		 logo[2].paint(g);
		 logo[3].paint(g);
		 logo[4].paint(g);
		 logo[5].paint(g);
	}
}
