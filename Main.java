import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;



/**
 * Main that is executed that displays everything and actions are taken here before being used by
 * the classes
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class Main {

    private int capacity;                     //Amount of players playing game.

    private Players players;				 //Empty players (Default 6 players with no tokens)
    private Weapons weapons;         		//Fixed set of weapons on board
    private CluedoUI ui;     				//Starts with an empty board with no players
    private StartUp start;					//Start class for starting methods
    private Turn turn;
    private TileGrid grid = new TileGrid();

    public Main() {
    	this.players = new Players();
    	this.weapons = new Weapons();
    	this.ui 	 = new CluedoUI(players, weapons);
    	this.start   = new StartUp(ui);
    	this.turn    = new Turn(ui);
    }
    
    /**
     * A method that checks if a string contains a number
     *
     * @return true = string consists of numbers only, false = string consists of non-numbers
     */
    public static boolean isNum(String str) {
    	if(str.equals(""))
    		return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }


    /**
     * Start of the game to find the number of players and add players and weapons to game
     */
    public void start() {
        capacity = start.size();
        this.players = new Players(capacity);
        start.addPlayers(players);

        weapons.createWeapons(); //Instantiates the weapons
        CommandPanel.updateCommands(); //Shows users available commands
        
        //Update and display the board
        ui.setBoard(players, weapons);
        ui.display();
    }
    
    //Takes players turns
    public void turn() {
        turn.turns(players);
    }
   
	/**
	 * A movement method to move a character
	 * @param dice
	 * @param curr
	 */
/*	public void movement(int dice, int curr) {
		String direction;
		boolean validDirection = false; //if a valid direction
		Tile currTile = players.getPlayer(curr).getToken().getPosition();
	
		ui.displayString(players.currPlayer(curr) + "make your move :");
		String[] commands = {"u(up)", "d(down)", "l(left)", "r(right)"};
		CommandPanel.updateCommands(commands);
		do{
			direction = ui.getCommand();
			ui.displayString(players.currPlayer(curr) + ": " + direction);
			
			//Catch if array is out of bounds
        	try {
		        if (direction.equalsIgnoreCase("u")) {
		        	currTile = grid.map[players.getTile(curr).getRow() - 1][players.getTile(curr).getColumn()];
		        	validDirection = true;
		  
		        }
		        else if(direction.equalsIgnoreCase("d")) {
		        	currTile = grid.map[players.getTile(curr).getRow() + 1][players.getTile(curr).getColumn()];
		        	validDirection = true;
		        	
		        }
		        else if(direction.equalsIgnoreCase("l")) {
		        	currTile = grid.map[players.getTile(curr).getRow()][players.getTile(curr).getColumn() - 1];
		        	validDirection = true;
		        }
		        else if (direction.equalsIgnoreCase("r")){
		        	currTile = grid.map[players.getTile(curr).getRow()][players.getTile(curr).getColumn() + 1];
		        	validDirection = true;
		        }
		        else {
		        	ui.displayString("Invalid direction... retry");
		        }
        	}
        	catch(ArrayIndexOutOfBoundsException e) {
        		ui.displayString("Invalid direction...[Off the board]");
        	}
        	
        	//Ensures no two players are on the same slot
        	for(int i = 0; i < players.getCapacity(); i++) {
        		if(currTile == players.getTile(i) && validDirection && players.getPlayer(i) != players.getPlayer(curr)) {
        			validDirection = false;
        			ui.displayString(players.currPlayer(curr) + " cannot move onto an occupied tile :<");
        		}
        		
        	}
        	
        	if(currTile.getRoom() == 0 || currTile.getSlot() == 3) {
        			
        	if(currTile.getSlot() == 1 && validDirection) {
	        	players.getPlayer(curr).getToken().moveBy(currTile);
	        	ui.display();
	        	dice--;
        	}
        	else if(currTile.getSlot() == 3 && validDirection) {
        		boolean invalidRoom = true;
        		switch(currTile.getRoom()) {
        		case 1: //Kitchen
        			for(int i = 0; i < grid.map.length; i++) {
        				for(int j = 0; j < grid.map[i].length; j++) {
        					if(grid.map[i][j].getSlot() == 5 && grid.map[i][j].getRoom() == 1 && invalidRoom) {
        						currTile = grid.map[i][j];
        						invalidRoom = players.getSameTile(currTile);
        					}
        				} 
        			}
        		break;
        		case 2: //Ball Room
        			for(int i = 0; i < grid.map.length; i++) {
        				for(int j = 0; j < grid.map[i].length; j++) {
        					if(grid.map[i][j].getSlot() == 5 && grid.map[i][j].getRoom() == 2 && invalidRoom) {
        						currTile = grid.map[i][j];
        						invalidRoom = players.getSameTile(currTile);
        					}
        				} 
        			}
        			break;
        		case 3:
        			for(int i = 0; i < grid.map.length; i++) {
        				for(int j = 0; j < grid.map[i].length; j++) {
        					if(grid.map[i][j].getSlot() == 5 && grid.map[i][j].getRoom() == 3 && invalidRoom) {
        						currTile = grid.map[i][j];
        						invalidRoom = players.getSameTile(currTile);
        					}
        				} 
        			}
        			break;
        		case 4:
        			for(int i = 0; i < grid.map.length; i++) {
        				for(int j = 0; j < grid.map[i].length; j++) {
        					if(grid.map[i][j].getSlot() == 5 && grid.map[i][j].getRoom() == 4 && invalidRoom) {
        						currTile = grid.map[i][j];
        						invalidRoom = players.getSameTile(currTile);
        					}
        				} 
        			}
        			break;
        		case 5:
        			for(int i = 0; i < grid.map.length; i++) {
        				for(int j = 0; j < grid.map[i].length; j++) {
        					if(grid.map[i][j].getSlot() == 5 && grid.map[i][j].getRoom() == 5 && invalidRoom) {
        						currTile = grid.map[i][j];
        						invalidRoom = players.getSameTile(currTile);
        					}
        				} 
        			}
        			break;
        		case 6:
        			for(int i = 0; i < grid.map.length; i++) {
        				for(int j = 0; j < grid.map[i].length; j++) {
        					if(grid.map[i][j].getSlot() == 5 && grid.map[i][j].getRoom() == 6 && invalidRoom) {
        						currTile = grid.map[i][j];
        						invalidRoom = players.getSameTile(currTile);
        					}
        				} 
        			}
        			break;
        		case 7:
        			for(int i = 0; i < grid.map.length; i++) {
        				for(int j = 0; j < grid.map[i].length; j++) {
        					if(grid.map[i][j].getSlot() == 5 && grid.map[i][j].getRoom() == 7 && invalidRoom) {
        						currTile = grid.map[i][j];
        						invalidRoom = players.getSameTile(currTile);
        					}
        				} 
        			}
        			break;
        		case 8:
        			for(int i = 0; i < grid.map.length; i++) {
        				for(int j = 0; j < grid.map[i].length; j++) {
        					if(grid.map[i][j].getSlot() == 5 && grid.map[i][j].getRoom() == 8 && invalidRoom) {
        						currTile = grid.map[i][j];
        						invalidRoom = players.getSameTile(currTile);
        					}
        				} 
        			}
        			break;
        		case 9:
        			for(int i = 0; i < grid.map.length; i++) {
        				for(int j = 0; j < grid.map[i].length; j++) {
        					if(grid.map[i][j].getSlot() == 5 && grid.map[i][j].getRoom() == 9 && invalidRoom) {
        						currTile = grid.map[i][j];
        						invalidRoom = players.getSameTile(currTile);
        					}
        				} 
        			}
        			break;
        		default:
        			break;
        		}
        		
        		players.getPlayer(curr).getToken().moveBy(currTile);
	        	ui.display();
	        	dice = 0; 
        	}
        	else if(currTile.getSlot() != 1 && validDirection) {
        		ui.displayString("Can't walk through walls mate");
        	}
        	
        	}
        	else{
        		ArrayList<Tile> exits = new ArrayList<Tile>();      
        		for(int i = 0; i < grid.map.length; i++) {
    				for(int j = 0; j < grid.map[i].length; j++) {
    					if(currTile.getRoom() == grid.map[i][j].getRoom() && grid.map[i][j].getSlot() == 3) {
    						exits.add(grid.map[i][j]);
    					}
    				} 
    			}
        		
        		//Exits
        		int numExits = 0;
        		String exitChoice;
        		ui.displayString("Available exits for " + players.currPlayer(curr));
        		for(Tile t: exits) {
        			ui.displayString(++numExits + ". Exit location" + " " + t.showRoom());
        		}
<<<<<<< HEAD
        	do {	
        		do {
        			exitChoice = ui.getCommand();	
        			ui.displayString(players.currPlayer(curr) + ": " + exitChoice);
        			if(!isNum(exitChoice)) {
        				ui.displayString("'" + exitChoice +"'" + " is not a choice...");
        			}
        		}while(!isNum(exitChoice));
        	}while(Integer.parseInt(exitChoice) < 1 || Integer.parseInt(exitChoice) > exits.size());
=======

        		//exitChoice = ui.getCommand();
				//You might need this but its causing an error where you have to write the same command twice.
>>>>>>> 875672a3ffc83f49affdcef6167cdc13a7beaa62
        	
        	players.getPlayer(curr).getToken().moveBy(exits.get(Integer.parseInt(exitChoice) - 1));
        	ui.display();
        	}
		}while(dice > 0);
	} 
	*/
    public static void main(String[] args) {
        Main game = new Main();
        game.start();
       	game.turn();
       /* game.capacity();
        game.addPlayers(); */
      //  game.turns();
    }

}