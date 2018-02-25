
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
    	boolean validDirection = false; //If direction is valid
    	Tile currTile = players.getTile(currPlayer); 
    	
    	
    	ui.displayString(players.currPlayer(currPlayer) + "make your move :");
    	
    	//Set of commands a player could possibly use
		String[] commands = {"u(up)", "d(down)", "l(left)", "r(right)"};
		CommandPanel.updateCommands(commands);
		
		do {
			direction = ui.getCommand();
			ui.displayString(players.currPlayer(currPlayer) + ": " + direction);
			
			//Catch if array is out of bounds
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
		        }
        	}
        	catch(ArrayIndexOutOfBoundsException e) {
        		ui.displayString("Invalid direction...[Off the board]");
        	}
        	
        	if(currTile.getSlot() == 1 && validDirection) {
	        	players.getPlayer(currPlayer).getToken().moveBy(currTile);
	        	ui.display();
	        	dice--;
	        	validDirection = false;
        	}
        	else {
        		ui.displayString("Can't walk through walls matey");
        	}
        	
		}while(dice > 0);
		
    }
}
