import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * A class that represents the turns of each player
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */

public class Turn {

	private CluedoUI ui;
	private TileGrid grid = new TileGrid();
	ArrayList<Card> murderEnvelope = new ArrayList<>(); //Holds the murder envelope contents
	ArrayList<Card> unusedCards = new ArrayList<>(); //Holds the contents of undealt cards

	public Turn(CluedoUI ui) {
		this.ui = ui;
	}

	//Cards get compared to these lists when seeing if a player owns them or not
	String playerList[] = {"Plum","White","Scarlet","Green","Mustard","Peacock"};
	String weaponsList[] = {"Candle Stick","Dagger","Lead Pipe","Revolver","Rope","Spanner"};
	String roomList[] = {"Dining Room","Conservatory","Study","Billard Room","Lounge","Library","Ball Room","Kitchen","Hall"};

	/**
	 * Each player takes turns
	 */
	public void turns(Players players) {
		String command; 				//Text that is entered
		boolean valid;  				//Check if action is valid
		Dice dice = new Dice();

		do {
			for (int i = 0; i < players.getCapacity(); i++) {
				valid = false;

				//First prompt for first board action
				ui.displayString("===" + players.currPlayer(i) + "'s TURN===");
				ui.displayString("Type a command");
				CommandPanel.updateUserImage(players.getPlayer(i).getImagePath());

				//Available commands
				String[] commands = {"roll","notes","cheat","help"};
				CommandPanel.updateCommands(commands);
				CommandPanel.updateMovesReamining(-1);

				do {
					command = ui.getCommand();
					ui.displayString(players.currPlayer(i) + ": " + command);

					if (command.equalsIgnoreCase("roll")) {

						//Rolls the dice and displays the result on-screen
						dice.rollDice();
						CommandPanel.updateCommands();
						ui.drawDice(dice.getRoll1(), dice.getRoll2());
						ui.displayString(players.currPlayer(i) + " rolled " + (dice.getRoll1() + dice.getRoll2()));
						ui.display();
						try { //The resulting dice roll is on-screen for 2 seconds
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						ui.drawDice(0, 0); //This hides the dice
						ui.display();
						valid = true;
					} else if (command.equalsIgnoreCase("notes")) {
						//Displays all cards apart from murder envelope ones, indicating cards they own or cards everyone can see
						notes(players,i);
					} else if (command.equalsIgnoreCase("cheat")) {
						//Displays the murder envelope cards
						cheat();
					} else if(command.equalsIgnoreCase("help")){
						//Displays various commands and an explanation on what they do
						help();
					}else {
						ui.displayString("Whoops! Wrong command.\nType 'help' if you're unsure what to do.");
					}
				} while (!valid); //The first part of their turn ends only when they make a roll

				//After rolling, the player decides where and how to move
				movement(players, dice.getRoll1()+dice.getRoll2(), i);

				//After all actions
				do {
					valid = false;
					ui.displayString(players.currPlayer(i)
							+ " no moves left.\nType a command");
					String[] commandsEnd = {"done","notes","cheat","help"};
					CommandPanel.updateCommands(commandsEnd);
					command = ui.getCommand();
					ui.displayString(players.currPlayer(i) + ": " + command);
					if (command.equalsIgnoreCase("done")){
						valid = true;
					} else if (command.equalsIgnoreCase("notes")){
						notes(players,i);
					}else if(command.equalsIgnoreCase("cheat")){
						cheat();
					}else if(command.equalsIgnoreCase("help")){
						help();
					}else {
						ui.displayString("Whoops! Wrong command.\nType 'help' if you're unsure what to do.");
					}
				} while (!valid); //Their turn ends after they type the 'done' command
			}
		} while (true);
	}
	//The following method iterates through the contents of the murder envelope and displays it to the player
	private void cheat(){
		for(Card x: murderEnvelope){
			ui.displayString(x.toString());
		}
	}
	//The following method displays info about each command to the player
	private void help(){
		ui.displayString("'roll' - to roll the dice and begin your turn."
				+ "\nA roll ranges from 1 to 6 and you can move that many spaces on the board."
				+ "\n\n'notes' - Type this to inspect your notes."
				+ "\nThis lists all players, weapons and rooms,\nand shows an 'X' mark for cards"
				+ " you own and an 'A' mark for cards everybody sees."
				+ "\n\n'cheat' - Allows you to inspect the murder envelope."
				+ "\n\n'u,r,d,l' - Type one of these to move up, right, down or left respectively."
				+ "\n\n'Passage' - Type to move from one corner of the board using a room to room passageway"
				+ "\n\n'quit' - This ends the game immediately.");
	}
	//This method displays each card indicating if they own it, or everyone can see it.
	private void notes(Players players, int i){


		// Ovverride the columnclass so that it now supports checkboxes, will be required on later releases.
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};

		// Create a new table to store notes. 
		JTable table = new JTable(model);

		// Add the column names one by one.
		model.addColumn("TYPE");
		model.addColumn("NAME");
		model.addColumn("YOUR HAND");
		model.addColumn("UNDEALT");


		JScrollPane tata = new JScrollPane(table);
		// set the size as it was being crowded and didn't look good at all.
		tata.setPreferredSize(new Dimension(1000, 170));
		// output the table within the pane using joptionpane with the object.
		JFrame displayProperties = new JFrame();
		// Add the table to the frame
		displayProperties.add(tata);
		displayProperties.setSize(900, 400);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		displayProperties.setLocation(dim.width / 2 - displayProperties.getSize().width / 2, dim.height / 2 - displayProperties.getSize().height / 2);
		displayProperties.setVisible(true);

		ArrayList<Card> arrayList = players.getPlayer(i).getCards(); //This list holds the current player's cards
		for(int count = 0; count < 6;count++) { //This loop iterates through each of the six characters
			boolean found = false; //Checks if a card was found so we dont display it twice
			int count2 = 0;
			while (count2 < arrayList.size()) { //This loop iterates through every card owned by the player
				if (arrayList.get(count2).toString().equalsIgnoreCase(playerList[count])) {
					model.addRow(new Object[]{"Player",playerList[count],"X",""});
					found = true;
				}
				count2++;
			}
			if (!found) { //The following if statements basically find and indicate undealt cards
				if (unusedCards.size() == 1) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(playerList[count])) {
						model.addRow(new Object[]{"Player",playerList[count],"","A"});
						found=true;
					}
				} else if (unusedCards.size() == 2) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(playerList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(playerList[count])) {
						model.addRow(new Object[]{"Player",playerList[count],"","A"});
						found = true;
					}
				}else if (unusedCards.size() == 3) {//There can only be at most 3 undealt cards with 2-6 players
					if (unusedCards.get(0).toString().equalsIgnoreCase(playerList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(playerList[count]) ||
							unusedCards.get(2).toString().equalsIgnoreCase(playerList[count])) {
						model.addRow(new Object[]{"Player",playerList[count],"","A"});
						found=true;
					}
				} if(!found){ //If a card doesent match the player's cards and isn't undealt, it's added normally without a marker
					model.addRow(new Object[]{"Player",playerList[count],"",""});
				}
			}
		}
		//The following is much like the previous except with weapon cards instead of players
		for(int count = 0; count < 6;count++) {
			boolean found = false;
			int count2 = 0;
			while (count2 < arrayList.size()) {
				if (arrayList.get(count2).toString().equalsIgnoreCase(weaponsList[count])) {
					model.addRow(new Object[]{"Weapon",weaponsList[count],"X",""});
					found = true;
				}
				count2++;
			}
			if (!found) {
				if (unusedCards.size() == 1) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(weaponsList[count])) {
						model.addRow(new Object[]{"Weapon",weaponsList[count],"","A"});
						found=true;
					}
				} else if (unusedCards.size() == 2) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(weaponsList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(weaponsList[count])) {
						model.addRow(new Object[]{"Weapon",weaponsList[count],"","A"});
						found = true;
					}
				}else if (unusedCards.size() == 3) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(weaponsList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(weaponsList[count]) ||
							unusedCards.get(2).toString().equalsIgnoreCase(weaponsList[count])) {
						model.addRow(new Object[]{"Weapon",weaponsList[count],"","A"});
						found=true;
					}
				} if(!found){
					model.addRow(new Object[]{"Weapon",weaponsList[count],"",""});
				}
			}
		}
		//The following is much like the previous except with room cards instead of players
		for(int count = 0; count < 9;count++) {
			boolean found = false;
			int count2 = 0;
			while (count2 < arrayList.size()) {
				if (arrayList.get(count2).toString().equalsIgnoreCase(roomList[count])) {
					model.addRow(new Object[]{"Room",roomList[count],"X",""});
					found = true;
				}
				count2++;
			}
			if (!found) {
				if (unusedCards.size() == 1) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(roomList[count])) {
						model.addRow(new Object[]{"Room",roomList[count],"","A"});
						found=true;
					}
				} else if (unusedCards.size() == 2) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(roomList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(roomList[count])) {
						model.addRow(new Object[]{"Room",roomList[count],"","A"});
						found = true;
					}
				} else if (unusedCards.size() == 3) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(roomList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(roomList[count]) ||
							unusedCards.get(2).toString().equalsIgnoreCase(roomList[count])) {
						model.addRow(new Object[]{"Room",roomList[count],"","A"});
						found=true;
					}
				}  if(!found){
					model.addRow(new Object[]{"Room",roomList[count],"",""});
				}
			}
		}
	}
	//Method used in main to tell the turn class what cards were undealt
	public void setUnusedCards(ArrayList<Card> array){
		unusedCards = array;
	}
	//Method used in main to tell the turn class the contents of the murder envelope
	public void setMurderEnvelope(ArrayList<Card> array){
		murderEnvelope = array;
	}

	/**
	 * Allows players to move depending on their dice roll
	 *
	 * @param players
	 * @param dice
	 * @param currPlayer
	 */
	public void movement(Players players, int dice, int currPlayer) {
		String direction; 									//Contains direction of where the user wants to go
		boolean validDirection;								//If direction is valid
		int sentinel = 0; 									//Ensures right warning is displayed
		Tile currTile = players.getTile(currPlayer);


		ui.displayString(players.currPlayer(currPlayer) + "make your move!");

		//Set of commands a player could possibly use
		String[] commands = {"u - up", "d - down", "l - left", "r - right"};
		CommandPanel.updateCommands(commands);
		CommandPanel.updateMovesReamining(dice);

		do {
			validDirection = false; //Reset
			sentinel = 0;  //Reset

			if(players.getTile(currPlayer).getSlot() == 5 || players.getTile(currPlayer).getSlot() == 3 || players.getTile(currPlayer).getSlot() == 4) {
				if(players.getTile(currPlayer).getSlot() == 3) {
					ui.displayString("Choose another exit");
				}
				Boolean tookSecretPath = exitRoom(players, currPlayer, players.getTile(currPlayer).getRoom());
				ui.display();
				if(!tookSecretPath){
					ui.displayString(players.currPlayer(currPlayer) + " now choose a direction");
					CommandPanel.updateMovesReamining(dice);
				}else{
					dice = 0;
					break;
				}
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
					ui.displayString(players.currPlayer(currPlayer) + " cannot move onto an occupied tile");
					sentinel = -1;
					break;
				}

			}

			//If the moved tile is a path & the move is valid
			if((currTile.getSlot() == 1 || currTile.getSlot() == 7) && validDirection) {

				if(players.getTile(currPlayer).getSlot() == 3 && currTile.getSlot() == 7) {
					ui.displayString("There's a wall in your way...");
				}else {
					players.getPlayer(currPlayer).getToken().moveBy(currTile);
					ui.display();
					dice--;
					CommandPanel.updateMovesReamining(dice);
				}
			}
			else if((currTile.getSlot() == 3 && players.getTile(currPlayer).getSlot() != 7) && validDirection) {
				//Enter a room
				players.getPlayer(currPlayer).getToken().moveBy(currTile);

				//Move into center of room and no more movement
				roomCenter(players, currPlayer, players.getTile(currPlayer).getRoom());
				ui.display();
				dice = 0;
				CommandPanel.updateMovesReamining(dice);
			}
			else if(sentinel == 0 && (currTile.getSlot() != 3 || (currTile.getSlot() == 3 && players.getTile(currPlayer).getSlot() == 7)
					|| currTile.getSlot() == 7 && players.getTile(currPlayer).getSlot() == 3)){
				ui.displayString("There's a wall in your way...");
			}

		}while(dice > 0);

	}

	/**
	 * Reposition players onto the centre of the room
	 * @param players
	 * @param currPlayer
	 * @param room
	 */
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

	/**
	 * Gives a list of exits to players if there are exits
	 * @param players
	 * @param currPlayer
	 * @param room
	 * @return
	 */
	public Boolean exitRoom(Players players, int currPlayer, int room) {
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
		if(room == 9 || room == 7 || room == 1 || room  == 3){
			ui.displayString("Enter 'passage' to take the secret passage!");
		}
		CommandPanel.updateMovesReamining(-2);

		//Player chooses an exit that isn't blocked
		do {
			do {
				exitChoice = ui.getCommand();
				ui.displayString(players.currPlayer(currPlayer) + ": " + exitChoice);
				if(exitChoice.equalsIgnoreCase("passage") && (room == 9 || room == 7 || room == 1 || room  == 3)){
					break;
				}
				if(!StartUp.isNum(exitChoice)) {
					ui.displayString("'" + exitChoice +"'" + " is not a valid choice");
				}
			}while(!StartUp.isNum(exitChoice));

			if(exitChoice.equalsIgnoreCase("passage") && (room == 9 || room == 7 || room == 1 || room  == 3)){
				break;
			}
			if(Integer.parseInt(exitChoice) < 1 || Integer.parseInt(exitChoice) > exits.size()) {
				ui.displayString(exitChoice + " is not a valid exit choice");
			}
		}while(Integer.parseInt(exitChoice) < 1 || Integer.parseInt(exitChoice) > exits.size());

		if(exitChoice.equalsIgnoreCase("passage") && ((room == 9 || room == 7 || room == 1 || room  == 3))){
			switch(room){
				case 9:
					roomCenter(players, currPlayer, 1);
					break;
				case 3:
					roomCenter(players, currPlayer, 7);
					break;
				case 7:
					roomCenter(players, currPlayer, 3);
					break;
				case 1:
					roomCenter(players, currPlayer, 9);
				default:
					break;
			}
			return true;
		}else {
			players.getPlayer(currPlayer).getToken().moveBy(
					exits.get(Integer.parseInt(exitChoice) - 1));
			return false;
		}

	}
}