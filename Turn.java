import java.util.ArrayList;

public class Turn {
	
	private CluedoUI ui;
	private TileGrid grid = new TileGrid();
	
	public Turn(CluedoUI ui) {
		this.ui = ui;
	}
	
	/**
     * Each player takes turns
     */
    public void turns(Players players) {
    	   String command; //Text that is entered
           Dice dice = new Dice();
           boolean valid;  //Check if action is valid
           do {
               for (int i = 0; i < players.getCapacity(); i++) {
                   valid = false;

                   //First prompt for first board action
                   ui.displayString(players.currPlayer(i) + "'s turn to move.\nType roll to roll the dice.");
                   CommandPanel.updateUserImage(players.getPlayer(i).getImagePath());
                   
                    //Available commands
	   				String[] commands = {"roll"};
	   				CommandPanel.updateCommands(commands);

                   do {
                       command = ui.getCommand();
                       ui.displayString(players.currPlayer(i) + ": " + command);

                       if (command.equalsIgnoreCase("roll")) {

                        //Rolls the dice and displays the result onscreen
                        dice.rollDice();
   					    CommandPanel.updateCommands();
   						ui.drawDice(dice.getRoll1(),dice.getRoll2());
                        ui.displayString(players.currPlayer(i) + " rolled " + (dice.getRoll1()+dice.getRoll2()));
                        ui.display();
   						try {
   							Thread.sleep(2000);
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

                   movement(players, dice.getRoll1()+dice.getRoll2(), i);

                   //After all actions
                   do {
                       ui.displayString(players.currPlayer(i)
                               + " no actions left. Type 'done' to pass turn, or 'quit' to end the "
                               + "game");
   					String[] commandsEnd = {"done","quit"};
   					CommandPanel.updateCommands(commandsEnd);
                       command = ui.getCommand();
                       ui.displayString(players.currPlayer(i) + ": " + command);
                   } while (!command.equalsIgnoreCase("done"));
               }
           } while (true);
    }
    
    public void movement(Players players, int dice, int currPlayer) {
    	String direction; //Contains direction of where the user wants to go
    	boolean validDirection;//If direction is valid
    	int sentinel = 0; //Ensures right warning is displayed
    	Tile currTile = players.getTile(currPlayer); 
    	
    	
    	ui.displayString(players.currPlayer(currPlayer) + "make your move :");
    	
    	//Set of commands a player could possibly use
		String[] commands = {"u(up)", "d(down)", "l(left)", "r(right)"};
		CommandPanel.updateCommands(commands);
		
		do {
			validDirection = false; //Reset
			sentinel = 0;  //Reset 
			
			if(players.getTile(currPlayer).getSlot() == 5 || players.getTile(currPlayer).getSlot() == 3) {
				if(players.getTile(currPlayer).getSlot() == 3) {
					ui.displayString("Choosing another exit buddy? Sure go ahead :)");
				}
				exitRoom(players, currPlayer, players.getTile(currPlayer).getRoom());
				ui.display();
				
				ui.displayString(players.currPlayer(currPlayer) + " now choose a direction");
			}	
			
				direction = ui.getCommand();
				ui.displayString(players.currPlayer(currPlayer) + ": " + direction);
				//Catch if array is out of bounds
				//Move character to another tile depending on direction choosen
	        	try {
	        		
			        if (direction.equalsIgnoreCase("u")) {
			        	currTile = grid.map[players.getTile(currPlayer).getRow() - 1][players.getTile(currPlayer).getColumn()];
			        	validDirection = true;
			  
			        }
			        else if(direction.equalsIgnoreCase("d")) {
			        	currTile = grid.map[players.getTile(currPlayer).getRow() + 1][players.getTile(currPlayer).getColumn()];
			        	validDirection = true;
			        	
			        }
			        else if(direction.equalsIgnoreCase("l")) {
			        	currTile = grid.map[players.getTile(currPlayer).getRow()][players.getTile(currPlayer).getColumn() - 1];
			        	validDirection = true;
			        }
			        else if (direction.equalsIgnoreCase("r")){
			        	currTile = grid.map[players.getTile(currPlayer).getRow()][players.getTile(currPlayer).getColumn() + 1];
			        	validDirection = true;
			        }
			        else {
			        	ui.displayString("'" + direction + "'" + " is not a valid direction");
			        	sentinel = -1;
			        }
	        	}
	        	catch(ArrayIndexOutOfBoundsException e) {
	        		ui.displayString("Invalid direction...[Off the board]");
	        		sentinel = -1;
	        	}
	        	
	        	//Ensures no two players are on the same slot
	        	for(int i = 0; i < players.getCapacity(); i++) {
	        		if(currTile == players.getTile(i) && validDirection && players.getPlayer(i) != players.getPlayer(currPlayer)) {
	        			validDirection = false;
	        			ui.displayString(players.currPlayer(currPlayer) + " cannot move onto an occupied tile :<");
	        			sentinel = -1;
	        			break;
	        		}
	        		
	        	}
	        	
	        	//If the moved tile is a path & the move is valid
	        	if(currTile.getSlot() == 1 && validDirection) {
		        	players.getPlayer(currPlayer).getToken().moveBy(currTile);
		        	ui.display();
		        	dice--;
	        	}
	        	else if(currTile.getSlot() == 3 && validDirection) {
	        		//Enter a room
	        		players.getPlayer(currPlayer).getToken().moveBy(currTile);
		        	
		        	//Move into center of room and no more movement
		        	roomCenter(players, currPlayer, players.getTile(currPlayer).getRoom());
		        	ui.display();
		        	dice = 0;
	        	}
	        	else if(sentinel == 0 && currTile.getSlot() != 3){
	        		ui.displayString("Can't walk through walls matey");
	        	}
			
		}while(dice > 0);
		
    }
    
    
    public void roomCenter(Players players, int currPlayer, int room) {
    	Tile currTile = players.getTile(currPlayer);
    	boolean invalidRoom = true;
    	
    	//Looks for the centre in which a character is positioned
    	for(int i = 0; i < grid.map.length; i++) {
    		//Quick escape from nested for loop if the value is found early.
    		if(!invalidRoom) {
    			break;
    		}
			for(int j = 0; j < grid.map[i].length; j++) {
				if(grid.map[i][j].getSlot() == 5 && grid.map[i][j].getRoom() == room && invalidRoom) {
					currTile = grid.map[i][j];
					invalidRoom = players.getSameTile(currTile);
				}
			} 
		}
    	
    	players.getPlayer(currPlayer).getToken().moveBy(currTile);
    }
    
    public void exitRoom(Players players, int currPlayer, int room) {
    	ArrayList<Tile> exits = new ArrayList<Tile>();
    	
    	//Searches for possible exits
    	for(int i = 0; i < grid.map.length; i++) {
			for(int j = 0; j < grid.map[i].length; j++) {
				if(room == grid.map[i][j].getRoom() && grid.map[i][j].getSlot() == 3) {
					exits.add(grid.map[i][j]);
				}
			} 
		}
    	
    	//Displays an array of exits for players
		int numExits = 0;
		String exitChoice;
		ui.displayString("Available exits for " + players.currPlayer(currPlayer));
		for(Tile t: exits) {
			ui.displayString(++numExits + ". Exit location" + " " + t.showRoom());
		}
		
		//Player chooses an exit that isn't blocked
	   	do {	
    		do {
    			exitChoice = ui.getCommand();	
    			ui.displayString(players.currPlayer(currPlayer) + ": " + exitChoice);
    			if(!StartUp.isNum(exitChoice)) {
    				ui.displayString("'" + exitChoice +"'" + " is not a choice...");
    			}
    		}while(!StartUp.isNum(exitChoice));
    			
    		if(Integer.parseInt(exitChoice) < 1 || Integer.parseInt(exitChoice) > exits.size()) {
    			ui.displayString(exitChoice + " is not a valid exit choice");
    		}
    	}while(Integer.parseInt(exitChoice) < 1 || Integer.parseInt(exitChoice) > exits.size());
	   	
	   	players.getPlayer(currPlayer).getToken().moveBy(exits.get(Integer.parseInt(exitChoice) - 1));
	   	
    }
}
