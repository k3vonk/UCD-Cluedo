/**A class panel that draws out the board panel of the cluedo game
 * 
 * @author Gajun, Richard, Royal
 */
import java.awt.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;
/**
 * A board panel which draws all the images and sets up the token locations
 * @Author Richard, Gajun
 */
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {

    private ArrayList<Weapon> weapon;
    private ArrayList<Player> player;

    //Graphic objects
    private TileGrid grid;
    private Board board;
    
    private static final int MAX_TOKEN = 6; //Amount of tokens available

    //Constructor panel
    public BoardPanel() {
        weapon = new ArrayList<Weapon>();
        player = new ArrayList<Player>();
        grid = new TileGrid();
        board = new Board();
        createWeapons();
        createPlayers();
    }

    //Candlestick, Dagger, Lead Pipe, Revolver, Rope, Spanner
    public void createWeapons() {
        weapon.add(new Weapon("Candle Stick", grid.map[21][1])); 
        weapon.add(new Weapon("Dagger", grid.map[2][21])); 
        weapon.add(new Weapon("Lead Pipe", grid.map[1][12])); 
        weapon.add(new Weapon("Revolver", grid.map[22][22])); 
        weapon.add(new Weapon("Rope", grid.map[19][16])); 
        weapon.add(new Weapon("Spanner", grid.map[2][2])); 
    }

    //Creating a list of players [Fixed starting location]
    public void createPlayers() {
        player.add(new Player("Plum", Color.magenta, grid.map[23][19]));
        player.add(new Player("White", Color.white, grid.map[9][0]));
        player.add(new Player("Scarlet", Color.red, grid.map[7][24]));
        player.add(new Player("Green", Color.green, grid.map[14][0]));
        player.add(new Player("Mustard", Color.yellow, grid.map[0][17]));
        player.add(new Player("Peacock", Color.blue, grid.map[23][6]));
    }
  
    /**A movement class to move objects
     * 
     * @param direction -command panel input
     * @param name - token or player 
     * @return false - no move has been made, true - movement made
     */
    public boolean moveToken(String direction, String name) {
        boolean correctName = false;
        boolean correctDirection = false;
        Tile currTile = null;
        Token currToken = null;
        int i = -1;
        
        //Search if the name is a player or a weapon
        while((i <= MAX_TOKEN) && !correctName) {
        	i++; //Starts searching value at 0
        	 if (player.get(i).getPlayerName().equals(name)) {
        		 currToken = player.get(i);
        		 correctName = true;
        	 }
        	 else if(weapon.get(i).getWeaponName().equals(name)) {
        		 currToken = weapon.get(i);
        		 correctName = true;
        	 }
        }
        
        //Moving Token based on up, down, left, right
        if(correctName) {
        	
        	//Catch if array is out of bounds
        	try {
		        if (direction.equals("up")) {
		        	currTile = grid.map[currToken.getTile().getColumn()][currToken.getTile().getRow() - 1];
		        	correctDirection = true;
		        }
		        else if(direction.equals("down")) {
		        	currTile = grid.map[currToken.getTile().getColumn()][currToken.getTile().getRow() + 1];
		        	correctDirection = true;
		        }
		        else if(direction.equals("left")) {
		        	currTile = grid.map[currToken.getTile().getColumn() - 1][currToken.getTile().getRow()];
		        	correctDirection = true;
		        }
		        else if (direction.equals("right")){
		        	currTile = grid.map[currToken.getTile().getColumn() + 1][currToken.getTile().getRow()];
		        	correctDirection = true;
		        }
        	}
        	catch(ArrayIndexOutOfBoundsException e) {
        		JOptionPane.showMessageDialog(null, "Invalid direction...Direction does not exist");
        	}
        }
        
        //New Location of token & draw it
        if(correctDirection) {
        	currToken.setTile(currTile);
        	repaint();
        }
        
        return correctDirection && correctName;
    }


    //Draw the objects together
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        board.paintComponent(g2); //Draws the board
        
        //grid.drawGrid(g2);    //draws a grid
        //Draw all the weapons in their current position
        for (Weapon li : weapon) {
            li.drawWeapon(g2);
        }

        //Draw all the players in their first location
        for (Player pi : player) {
            pi.drawPlayer(g2);
        }
    }

}
