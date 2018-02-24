import javax.swing.JOptionPane;

/**
 * Main that is executed that displays everything and actions are taken here before being used by the classes
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class Main {

	private String capacity; 								//Amount of players playing game.
	
	private Players players = new Players(); 				//Empty players (Default 6 players with no tokens)
	private final Weapons weapons = new Weapons();	 		//Fixed set of weapons on board
	private CluedoUI ui = new CluedoUI(players, weapons); 	//Starts with an empty board with no players
	private TileGrid grid = new TileGrid();
	private Dice dice = new Dice();
	
	public enum Token {PLUM, WHITE, SCARLET, GREEN, MUSTARD, PEACOCK}
	
	/**
	 * A method that checks if a string contains a number
	 * @param str
	 * @return true = string consists of numbers only, false = string consists of non-numbers
	 */
	public static boolean isNum(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	/**
	 * Asks the users, the number of players playing the game and instantiates the arraylist of players.
	 */
	private void capacity() {
        ui.displayString("Enter the number of players: [min: 2, max: 6]");
        
        //Ensures choice is within the [min, max] range
        do {
        	
        	//Checks if capacity contains numbers only
        	do {
	            capacity = ui.getCommand();
	            ui.displayString(capacity);
	            if(!isNum(capacity)) { //Error message
	            	ui.displayString("\'" + capacity + "\'" + " is not an integer.....");
	            }
        	}while(!isNum(capacity));
	        
        	if(Integer.parseInt(capacity) < 2 || Integer.parseInt(capacity) > 6) { //Error message
        		ui.displayString("Enter a valid integer between [2 - 6].....");
        	}
        } while (Integer.parseInt(capacity) < 2 || Integer.parseInt(capacity) > 6);
        
        this.players = new Players(Integer.parseInt(capacity)); //Instantiate the players with a new set of players
    }
	
	/**
	 * Finds name and choice of player and sets their token
	 */
	private void addPlayers() {
		String name; 	//Name of player
		String verifyName; 	//If they want to change their name
		String choice; 	//The token they want to choose
		
		//Iterates through each player and allows each one to choose their name and token
		for(int i = 0; i < Integer.parseInt(capacity); i++) {
			int j  = 0;
			
			//Acquire players name
			ui.displayString("Player " + (i+1) + " name: ");
			name = ui.getCommand();

			do {//Ensures if the name they choose, is the name they want
				ui.displayString("\'" + name + "\'" + ", Are you sure with this name [Y/N]");
				verifyName = ui.getCommand();
				
				if(verifyName.equalsIgnoreCase("N")) { //Allows users to change name
					ui.displayString("Player " + (i+1) + " choose a new name:");
					name = ui.getCommand();
				}
			}while(!verifyName.equalsIgnoreCase("Y"));
			
			
			//Character choice 
			ui.displayString("Player " + (i+1) + "(" + name + "): "+ " Please choose a character");
			for(Token p: Token.values()) {
				ui.displayString(++j +". " + p.toString());
			}

			boolean valid = true;
			do { //Ensures valid choice
				do { //Ensures choice is a number
					choice = ui.getCommand();
					ui.displayString(choice);
					
					for(Player p: players) { //Ensures theres no two players having the same token
						valid = p.hasChoice(Integer.parseInt(choice));
						if(!valid) {
							ui.displayString("Not available character, retry....");
							break;
						}
					}
					
					if(!isNum(choice)) { //Error message for non numbers
						ui.displayString("\'" + choice + "\'" + " is not a valid choice...");
					}
				}while(!isNum(choice));
				
				if(Integer.parseInt(choice) > 6 || Integer.parseInt(choice) < 1) { //Error message for out of bound
					ui.displayString("Not available character, retry....");
				}
			}while((Integer.parseInt(choice) > 6 || Integer.parseInt(choice) < 1) || !valid);
				
			//Create the players & give them tokens
			players.createPlayers(name, Integer.parseInt(choice));
			players.createTokens(i);
		}
		
		//Update and display the board
		ui.setBoard(players, weapons);
		ui.display();
	}
	
	
	public void turns() {
		String command;
		boolean valid;
		do {
			for(int i = 0; i < players.getCapacity(); i++) {


				ui.displayString(players.currPlayer(i) + " turn to move.\nType roll to roll the dice.");
				CommandPanel.updateUserImage(players.getPlayer(i).getImagePath());

				do {
					valid = false;
					command = ui.getCommand();
					ui.displayString(players.currPlayer(i) + ": " + command);

					if(command.equalsIgnoreCase("roll")){
						dice.rollDice();
						ui.displayString(players.currPlayer(i) + " rolled "+ dice.getRoll());
						valid = true;
					}else{
						ui.displayString("Whoops! Wrong command. Try 'roll' this time :)");
					}
				}while(!valid);

				movement(dice.getRoll(), i);

			}
		}while(true);
	}
	
	public void movement(int dice, int curr) {
		String direction;
		Tile currTile = players.getPlayer(curr).getToken().getPosition();
	
		do{
			direction = ui.getCommand();
			ui.displayString(players.currPlayer(curr) + ": " + direction);
			
			//Catch if array is out of bounds
        	try {
		        if (direction.equalsIgnoreCase("u")) {
		        	currTile = grid.map[players.getTile(curr).getRow() - 1][players.getTile(curr).getColumn()];
		  
		        }
		        else if(direction.equalsIgnoreCase("d")) {
		        	currTile = grid.map[players.getTile(curr).getRow() + 1][players.getTile(curr).getColumn()];
		        	
		        }
		        else if(direction.equalsIgnoreCase("l")) {
		        	currTile = grid.map[players.getTile(curr).getRow()][players.getTile(curr).getColumn() - 1];
		        	
		        }
		        else if (direction.equalsIgnoreCase("r")){
		        	currTile = grid.map[players.getTile(curr).getRow()][players.getTile(curr).getColumn() + 1];
		      
		        }
		        else {
		        	ui.displayString("Invalid direction... retry");
		        }
        	}
        	catch(ArrayIndexOutOfBoundsException e) {
        		JOptionPane.showMessageDialog(null, "Invalid direction...Direction does not exist[Off the board]");
        	}
        	if(currTile.getSlot() == 1) {
	        	players.getPlayer(curr).getToken().moveBy(currTile);
	        	ui.display();
	        	dice--;
        	}
        	else {
        		ui.displayString("Cannot walk through walls");
        	}
		}while(dice > 0);
	}
	

	
	public static void main(String[] args) {
		Main game = new Main();
		game.capacity();
		game.addPlayers();
		game.turns();
	}

}
