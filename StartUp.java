import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * A class that takes in player information before the gameplay starts
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */

public class StartUp {

    private CluedoUI ui; //Just a ui

    private enum Token {PLUM, WHITE, SCARLET, GREEN, MUSTARD, PEACOCK}

    private ArrayList<Card> murderEnvelope = new ArrayList<>();

    public StartUp(CluedoUI ui) {
        this.ui = ui;
    }

    /**
     * A method that checks if a string contains a number
     *
     * @return true = string consists of numbers only, false = string consists of non-numbers
     */
    public static boolean isNum(String str) {
        if (str.equals("")) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * Asks the users, the number of players playing the game
     */
    public int size() {

        String size; //Holds the size of the players
        
        ui.displayString("======GAME SETUP [CAPACITY]======");

        ui.displayString("Enter the number of players: [min: 2, max: 6]");

        //Ensures choice is within the [min, max] range
        do {

            //Checks if size contains numbers only
            do {
                size = ui.getCommand();
                ui.displayString(size);
                if (!isNum(size)) { //Error message
                    ui.displayString("\'" + size + "\'" + " is not an integer.....");
                }
            } while (!isNum(size));

            if (Integer.parseInt(size) < 2 || Integer.parseInt(size) > 6) { //Error message
                ui.displayString("Enter a valid integer between [2 - 6].....");
            }
        } while (Integer.parseInt(size) < 2 || Integer.parseInt(size) > 6);

        return Integer.parseInt(size);
    }

    /**
     * Finds name and choice of player and sets their token
     */
    public void addPlayers(Players players) {
        String name;          //Name of player
        String verifyName;    //Y/N to the current name
        String choice;        //The token they want to choose

        ui.displayString("=====GAME SETUP [Name & Token]=====");

        //Iterates through each player and allows each one to choose their name and token
        for (int i = 0; i < players.getCapacity(); i++) {
            ui.displayString("======" + "PLAYER " + (i + 1) + "======");
            //Acquire players name
            ui.displayString("Player " + (i + 1) + "'s name: ");
            do {
                name = ui.getCommand();
            } while (name.equals(""));

            do {//Ensures if the name they choose, is the name they want
                ui.displayString("\'" + name + "\'" + ", Are you sure about this name? [Y/N]");
                verifyName = ui.getCommand();

                if (verifyName.equalsIgnoreCase("N")) { //Allows users to change name
                    ui.displayString("Player " + (i + 1) + ", please choose a new name:");
                    name = ui.getCommand();
                }
            } while (!verifyName.equalsIgnoreCase("Y"));


            //Character choice text
            ui.displayString(
                    "Player " + (i + 1) + "(" + name + "), " + " Please choose a character");

            int j = 0;

            //Method to show only the character options available to the user.
            for (Token p : Token.values()) {
                j++;
                boolean playerExists = false;
                for (Player x : players) { //Ensures there's no two players having the same token
                    if (!x.hasChoice(j)) {
                        playerExists = true;
                    }
                }
                if (!playerExists) {
                    ui.displayString(j + ". " + p.toString());
                }
            }

            boolean valid = true;
            do { //Ensures valid choice
                do { //Ensures choice is a number
                    choice = ui.getCommand();
                    ui.displayString("Player " + (i + 1) + ": " + choice);

                    if (!isNum(choice)) { //Error message for non numbers
                        ui.displayString("\'" + choice + "\'" + " is not a valid choice.");
                    }
                } while (!isNum(choice));

                //Ensures there is no two players having the same token
                for (Player p : players) {
                    valid = p.hasChoice(Integer.parseInt(choice));
                    if (!valid) {
                        ui.displayString("Character unavailable, please retry.");
                        break;
                    }
                }


                if (Integer.parseInt(choice) > 6 || Integer.parseInt(choice)
                        < 1) { //Error message for out of bound
                    ui.displayString("Character unavailable, please retry.");
                }
            } while ((Integer.parseInt(choice) > 6 || Integer.parseInt(choice) < 1) || !valid);

            //Create the players & give them tokens
            players.createPlayers(name, Integer.parseInt(choice));
            players.createTokens(i);

        }
    }

    public ArrayList<Card> getMurderEnvelope() {
        return murderEnvelope;
    }

    /**
     * Method to divide the stack of cards among the players and choose three for the murder
     * envelope
     *
     * @param players an input of the current list of players of type Players
     * @return nothing
     */
    public void divideCards(Players players) {
        ArrayList<Card> tokenList = new ArrayList<Card>(); // Arraylist to store the entire deck
        Random rand = new Random(); // To pick out random cards from the deck

        /* ArrayLists of characters, weapons and rooms to pick a random from each to store into
         murder envelope */
        ArrayList<String> characters = new ArrayList<>(
                Arrays.asList("Mustard", "Plum", "Green", "Peacock",
                        "Scarlet", "White"));

        ArrayList<String> weapons = new ArrayList<>(
                Arrays.asList("Dagger", "Candle Stick", "Revolver", "Rope", "Lead Pipe",
                        "Spanner"));
        ArrayList<String> rooms = new ArrayList<>(
                Arrays.asList("Hall", "Lounge", "Dining Room", "Kitchen", "Ball Room",
                        "Conservatory",
                        "Billiard Room", "Library", "Study"));


        // Pick out cards randomly for murder envelope
        Card characterChosen = new Card(characters.get(rand.nextInt(characters.size())), 1);
        Card weaponChosen = new Card(weapons.get(rand.nextInt(weapons.size())), 1);
        Card roomChosen = new Card(rooms.get(rand.nextInt(rooms.size())), 1);

        // Put them into the envelope
        murderEnvelope.addAll(Arrays.asList(characterChosen, weaponChosen, roomChosen));

        // Remove selected cards in the envelope from the main deck
        characters.remove(characterChosen.getName());
        weapons.remove(weaponChosen.getName());
        rooms.remove(roomChosen.getName());

        // Add the remaining card to the ArrayList to divide among players.
        for (String x : characters) {
            tokenList.add(new Card(x, 1));
        }

        for (String x : weapons) {
            tokenList.add(new Card(x, 2));
        }

        for (String x : rooms) {
            tokenList.add(new Card(x, 3));
        }

        // Divides cards evenly among players
        while (tokenList.size() >= players.getCapacity()) {
            for (int i = 0; i < players.getCapacity(); i++) {
                Card chosenCard = tokenList.get(rand.nextInt(tokenList.size()));
                players.getPlayer(i).giveCard(chosenCard);
                tokenList.remove(chosenCard);
            }
        }

        // Show the cards that didn't get divided to the user in the GUI
        InformationPanel.updateRemainingCards(tokenList);

        //Sets every players notebook
        for(int i = 0;  i < players.getCapacity(); i++) {
        	players.getPlayer(i).setNoteBook(tokenList);
        }
        
    }

    /*
     * Player with highest roll moves first
     * The rest of the players are ordered in terms of who entered their names first
     */
    public void position(Players players) {
    	Dice dice = new Dice();
    	boolean unique = false;							//Unique highest roll
    	int[] pos = new int[players.getCapacity()]; 	//Saves the roll of each player
    	int position = 0; 								//memorises position of highest roller
    	int highest = 0; 								//memorises highest roll
    	int diff = 0;									//Keeps track of highest after everyone rolls
    	
    	ui.displayString("=====GAME SETUP [HIGHEST ROLL]=====");
    	
    	while(!unique) {
    		
	    	//Rolling dice
	    	for(int i = 0; i < players.getCapacity(); i++) {
	    		
	    		//Only roll for players who hasn't rolled or with highest rolls
	    		if(pos[i] == diff) {
		    		dice.rollDice(); 											
		    		pos[i] = diff + dice.getRoll1() + dice.getRoll2(); 			//Sum of dice for the ith player
		    		
		    		//Draw dice image onto the screen
		    		ui.drawDice(dice.getRoll1(), dice.getRoll2());
		    		ui.displayString(players.currPlayer(i) + " rolled " + (dice.getRoll1() + dice.getRoll2()));
		    		ui.display();
		    		try { //The resulting dice roll is onscreen for 2 seconds
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ui.drawDice(0,0); //This hides the dice
					ui.display();
					
					//Records highest roll and position
					if(highest < pos[i]) {
						highest = pos[i];
						position = i;
						unique = true;
					}else if(highest == pos[i]) {
						//if two or more players have the same highest roll,
						//there is no set highest position -> they have to reroll
						unique = false;
					}
	    		}
	    	}
	    	
	    	if(!unique) {
	    		ui.displayString("Players with the same highest roll value,\nhas to re-roll");
	    	}
	    	diff = highest; //ensures reroll will not be the same as the lower numbers
    	}
    	
    	//Reposition players into their new order
    	ui.displayString("===" + players.currPlayer(position) + " HIGHEST ROLL===");
    	players.addFirst(position, players.getPlayer(position));

    }
    
    /**
     * Add dummies if the amount of players is less than 6
     * @param players
     * @param dummies
     */
    public void addDummies(Players players, Players dummies) {
    	
    //Create a default set of tokens for dummies
   	for(int i = 0; i < dummies.getCapacity(); i++) {
   		dummies.createPlayers("dummy", i+1);
   		dummies.createTokens(i);
   	}
   	
   	
   	//Remove dummy tokens that players already have
   	for(int i = 0; i < players.getCapacity(); i++) {
   		for(int j = 0; j < dummies.getCapacity(); j++) {
   			if(players.getTokenName(i).equals(dummies.getTokenName(j))) {
   				dummies.remove(j);
   				break;
   			}
   		}
   	} 
		
    }

}
