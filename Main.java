import java.util.ArrayList;

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

    private String capacity;                                //Amount of players playing game.

    private Players players = new Players();
            //Empty players (Default 6 players with no tokens)
    private final Weapons weapons = new Weapons();            //Fixed set of weapons on board
    private CluedoUI ui = new CluedoUI(players, weapons);
            //Starts with an empty board with no players
    private TileGrid grid = new TileGrid();

    public enum Token {PLUM, WHITE, SCARLET, GREEN, MUSTARD, PEACOCK}

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
     * Asks the users, the number of players playing the game and instantiates the arraylist of
     * players.
     */
    private void capacity() {
        ui.displayString("Enter the number of players: [min: 2, max: 6]");

        //Ensures choice is within the [min, max] range
        do {

            //Checks if capacity contains numbers only
            do {
                capacity = ui.getCommand();
                ui.displayString(capacity);
                if (!isNum(capacity)) { //Error message
                    ui.displayString("\'" + capacity + "\'" + " is not a valid input!");
                }
            } while (!isNum(capacity));

            if (Integer.parseInt(capacity) < 2 || Integer.parseInt(capacity) > 6) { //Error message
                ui.displayString("Enter a valid integer between [2 - 6].....");
            }
        } while (Integer.parseInt(capacity) < 2 || Integer.parseInt(capacity) > 6);

        this.players = new Players(
                Integer.parseInt(capacity)); //Instantiate the players with a new set of players
    }


    public Tile findSpot(int mRow, int mColumn) {

        Tile currentGrid = grid.map[mRow][mColumn];
        // First loop to keep track of blocks to move right and left.
        for (int j = 0; j < players.getCapacity(); j++) {
            // Keep track to see if we found a player at a given distance from center
            Boolean lExists = false;
            Boolean rExists = false;

            //second loop to keep track of players.
            for (int i = 0; i < players.getCapacity(); i++) {
                Tile tileRight = grid.map[mRow][mColumn + j];
                Tile tileLeft = grid.map[mRow][mColumn - j];

                if (tileRight == players.getTile(i)) {
                    rExists = true;
                }
                if (tileLeft == players.getTile(i)) {
                    lExists = true;
                }
            }

            if (!lExists) {
                currentGrid = grid.map[mRow][mColumn - j];
                break;
            }

            if (!rExists) {
                currentGrid = grid.map[mRow][mColumn + j];
                break;
            }

        }

        return currentGrid;

    }


    /**
     * Finds name and choice of player and sets their token
     */
    private void addPlayers() {
        String name;        //Name of player
        String verifyName;    //If they want to change their name
        String choice;        //The token they want to choose

        //Iterates through each player and allows each one to choose their name and token
        for (int i = 0; i < Integer.parseInt(capacity); i++) {

            boolean uniqueName;
            do {//Acquire players name and ensures it is unique
                uniqueName = true;
                ui.displayString("Player " + (i + 1) + " name: ");
                name = ui.getCommand();
                for (Player p : players) {
                    if (name.equalsIgnoreCase(p.getName())) {
                        ui.displayString("Sorry that name is already taken.");
                        uniqueName = false;
                    }
                }
            }while(!uniqueName);

            do {//Ensures if the name they choose, is the name they want
                ui.displayString("\'" + name + "\'" + ", Are you sure with this name [Y/N]");
                verifyName = ui.getCommand();

                if (verifyName.equalsIgnoreCase("N")) { //Allows users to change name
                    ui.displayString("Player " + (i + 1) + " choose a new name:");
                    name = ui.getCommand();
                }
            } while (!verifyName.equalsIgnoreCase("Y"));


            //Character choice
            ui.displayString(
                    "Player " + (i + 1) + "(" + name + "): " + " Please choose a character");
            int j = 0;

            // Method to show only the character options available to the user.
            for (Token p : Token.values()) {
            	j++; boolean playerExists = false;
				for (Player x : players) { //Ensures theres no two players having the same token
					if(!x.hasChoice(j))
						playerExists = true;
				}
				if(!playerExists){
					ui.displayString(j + ". " + p.toString());
				}
            }

            boolean valid = true;
            do { //Ensures valid choice
                do { //Ensures choice is a number
                    choice = ui.getCommand();
                    ui.displayString("Player " + (i + 1) + ": " + choice);

                    if (!isNum(choice)) { //Error message for non numbers
                        ui.displayString("\'" + choice + "\'" + " is not a valid choice...");
                    }
                } while (!isNum(choice));

                for (Player p : players) { //Ensures theres no two players having the same token
                    valid = p.hasChoice(Integer.parseInt(choice));
                    if (!valid) {
                        ui.displayString("Not available character, retry. :(((");
                        break;
                    }
                }


                if (Integer.parseInt(choice) > 6 || Integer.parseInt(choice)
                        < 1) { //Error message for out of bound
                    ui.displayString("Not available character, retry....");
                }
            } while ((Integer.parseInt(choice) > 6 || Integer.parseInt(choice) < 1) || !valid);

            //Create the players & give them tokens
            players.createPlayers(name, Integer.parseInt(choice));
            players.createTokens(i);

        }

        weapons.createWeapons(); //Instantiates the weapons

        //Update and display the board
        ui.setBoard(players, weapons);
        ui.display();
    }

    /**
     * Each player takes turns
     */
    public void turns() {
        String command; //Text that is entered
        Dice dice = new Dice();
        boolean valid;  //Check if action is valid
        do {
            for (int i = 0; i < players.getCapacity(); i++) {
                valid = false;

                //First prompt for first board action
                ui.displayString(
                        players.currPlayer(i) + "'s turn to move.\nType roll to roll the dice.");
                CommandPanel.updateUserImage(players.getPlayer(i).getImagePath());

                do {

                    command = ui.getCommand();
                    ui.displayString(players.currPlayer(i) + ": " + command);

                    if (command.equalsIgnoreCase("roll")) {
                        //Rolls the dice and displays the result onscreen
                        dice.rollDice();
						ui.drawDice(dice.getRoll1(),dice.getRoll2());
                        ui.displayString(players.currPlayer(i) + " rolled " + (dice.getRoll1()+dice.getRoll2()));
                        ui.display();
						try {
							Thread.sleep(2500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						ui.drawDice(0,0); //This hides the dice
						ui.display();
                        valid = true;
                    } else {
                        ui.displayString("Whoops! Wrong command. Try 'roll' this time :)");
                    }
                } while (!valid);

                movement(dice.getRoll1()+dice.getRoll2(), i);

                //After all actions
                do {
                    ui.displayString(players.currPlayer(i)
                            + " no actions left. Type 'done' to pass turn, or 'quit' to end the "
                            + "game");
                    command = ui.getCommand();
                    ui.displayString(players.currPlayer(i) + ": " + command);
                } while (!command.equalsIgnoreCase("done"));
            }
        } while (true);
    }

    /**
     * A movement method to move a character
     */
 /*   public void movement(int dice, int curr) {
        String direction;
        boolean validDirection = false; //if a valid direction
        Tile currTile = players.getPlayer(curr).getToken().getPosition();

        ui.displayString(players.currPlayer(curr) + "make your move :");
        do {
            direction = ui.getCommand();
            ui.displayString(players.currPlayer(curr) + ": " + direction);

            //Catch if array is out of bounds
            try {
                if (direction.equalsIgnoreCase("u")) {
                    currTile = grid.map[players.getTile(curr).getRow() - 1][players.getTile(
                            curr).getColumn()];
                    validDirection = true;

                } else if (direction.equalsIgnoreCase("d")) {
                    currTile = grid.map[players.getTile(curr).getRow() + 1][players.getTile(
                            curr).getColumn()];
                    validDirection = true;

                } else if (direction.equalsIgnoreCase("l")) {
                    currTile = grid.map[players.getTile(curr).getRow()][players.getTile(
                            curr).getColumn() - 1];
                    validDirection = true;
                } else if (direction.equalsIgnoreCase("r")) {
                    currTile = grid.map[players.getTile(curr).getRow()][players.getTile(
                            curr).getColumn() + 1];
                    validDirection = true;
                } else {
                    ui.displayString("Invalid direction... retry");
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null,
                        "Invalid direction...Direction does not exist[Off the board]");
            }

            //Ensures no two players are on the same slot
            for (int i = 0; i < players.getCapacity(); i++) {
                if (currTile == players.getTile(i) && validDirection && players.getPlayer(i)
                        != players.getPlayer(curr)) {
                    validDirection = false;
                    ui.displayString(
                            players.currPlayer(curr) + " cannot move onto an occupied tile :<");
                }

            }
            if (currTile.getSlot() == 1 && validDirection) {
                players.getPlayer(curr).getToken().moveBy(currTile);
                ui.display();
                dice--;
            } else if (currTile.getSlot() == 3 && validDirection) {

                System.out.println("Row:" + currTile.getRow() + " Column:" + currTile.getColumn());
                if ((currTile.getRow() == 5 && currTile.getColumn() == 8) || (currTile.getRow() == 5
                        && currTile.getColumn() == 15) || ((currTile.getRow() == 7
                        && currTile.getColumn() == 9)) || ((currTile.getRow() == 7
                        && currTile.getColumn() == 14))) {

                    currTile = findSpot(4, 12);
                }
                if (currTile.getRow() == 6 && currTile.getColumn() == 4) {
                    currTile = findSpot(3, 2);
                }
                if (currTile.getRow() == 4 && currTile.getColumn() == 18) {
                    currTile = findSpot(3, 20);
                }
                if ((currTile.getRow() == 12 && currTile.getColumn() == 7) || (
                        currTile.getRow() == 15 && currTile.getColumn() == 6)) {
                    currTile = findSpot(13, 4);
                }
                if ((currTile.getRow() == 9 && currTile.getColumn() == 18) || (
                        currTile.getRow() == 12 && currTile.getColumn() == 22)) {
                    currTile = findSpot(11, 20);
                }
                if (currTile.getRow() == 19 && currTile.getColumn() == 6) {
                    currTile = findSpot(22, 3);
                }
                if ((currTile.getRow() == 14 && currTile.getColumn() == 20) || (
                        currTile.getRow() == 16 && currTile.getColumn() == 17)) {
                    currTile = findSpot(17, 19);
                }
                if ((currTile.getRow() == 18 && (currTile.getColumn() == 11
                        || currTile.getColumn() == 12)) || (currTile.getRow() == 20
                        && currTile.getColumn() == 14)) {
                    currTile = findSpot(20, 11);
                }
                if (currTile.getRow() == 21 && currTile.getColumn() == 17) {
                    currTile = findSpot(22, 19);
                }
                players.getPlayer(curr).getToken().moveBy(currTile);
                ui.display();
                dice = 0;
            } else if (currTile.getSlot() != 1 && validDirection) {
                ui.displayString("Can't walk through walls mate");
            } 
        } while (dice > 0);
    }
*/
	/**
	 * A movement method to move a character
	 * @param dice
	 * @param curr
	 */
	public void movement(int dice, int curr) {
		String direction;
		boolean validDirection = false; //if a valid direction
		Tile currTile = players.getPlayer(curr).getToken().getPosition();
	
		ui.displayString(players.currPlayer(curr) + "make your move :0");
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
        		JOptionPane.showMessageDialog(null, "Invalid direction...Direction does not exist[Off the board]");
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
        	else {
        		ArrayList<Tile> exits = new ArrayList<Tile>();      
        		for(int i = 0; i < grid.map.length; i++) {
    				for(int j = 0; j < grid.map[i].length; j++) {
    					if(currTile.getRoom() == grid.map[i][j].getRoom() && grid.map[i][j].getSlot() == 3) {
    						exits.add(grid.map[i][j]);
    					}
    				} 
    			}
        		
        		int numExits = 0;
        		String exitChoice;
        		for(Tile t: exits) {
        			ui.displayString(++numExits + "Exit location" + " " + t.showRoom());
        		}
        		
        		exitChoice = ui.getCommand();
        	
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