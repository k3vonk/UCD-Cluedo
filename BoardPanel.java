import java.awt.*;
import javax.swing.*;
/**
 * A basic board panel
 */
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel{

	public Weapon[] logo = new Weapon[6];
	public Player[] icon = new Player[6];
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

	public void createPlayers(){
		icon[0] = new Player("Plum", test3.map[22][2].getXCord(), test3.map[22][2].getYCord(), Color.magenta);
		icon[1] = new Player("White", test3.map[3][22].getXCord(), test3.map[3][22].getYCord(), Color.white);
		icon[2] = new Player("Scarlet", test3.map[2][13].getXCord(), test3.map[2][13].getYCord(), Color.red);
		icon[3] = new Player("Green", test3.map[20][20].getXCord(), test3.map[20][20].getYCord(), Color.green);
		icon[4] = new Player("Mustard", test3.map[18][15].getXCord(), test3.map[18][15].getYCord(), Color.yellow);
		icon[5] = new Player("Peacock", test3.map[4][4].getXCord(), test3.map[4][4].getYCord(), Color.blue);

	}
	
	//Draw the objects together
	public void paintComponent(Graphics g) {
		createWeapons();
		createPlayers();
		
		 test.drawMe(g);
		 test3.drawMe(g);
		 logo[0].paint(g);
		 logo[1].paint(g);
		 logo[2].paint(g);
		 logo[3].paint(g);
		 logo[4].paint(g);
		 logo[5].paint(g);

		 icon[0].paint(g);
		 icon[1].paint(g);
		 icon[2].paint(g);
		 icon[3].paint(g);
		 icon[4].paint(g);
		 icon[5].paint(g);
	}

	public void movementTest(){
		icon[0].moveX(20);
		icon[0].moveY(20);

		icon[1].moveX(20);
		icon[1].moveY(20);

		icon[2].moveX(20);
		icon[2].moveY(20);

		icon[3].moveX(20);
		icon[3].moveY(20);

		icon[4].moveX(20);
		icon[4].moveY(20);

		icon[5].moveX(20);
		icon[5].moveY(20);
	}

}
