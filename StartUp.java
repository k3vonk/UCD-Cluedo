

public class StartUp {
	
	private CluedoUI ui; //Just a ui
	private enum Token {PLUM, WHITE, SCARLET, GREEN, MUSTARD, PEACOCK};
	
	public StartUp(CluedoUI ui){
		this.ui = ui;
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
     * Asks the users, the number of players playing the game
     */
    public int size() {
    	String size; //Holds the size of the players
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

        //Iterates through each player and allows each one to choose their name and token
        for (int i = 0; i < players.getCapacity(); i++) {

            //Acquire players name
            ui.displayString("Player " + (i + 1) + " name: ");
            name = ui.getCommand();

            do {//Ensures if the name they choose, is the name they want
                ui.displayString("\'" + name + "\'" + ", Are you sure with this name [Y/N]");
                verifyName = ui.getCommand();

                if (verifyName.equalsIgnoreCase("N")) { //Allows users to change name
                    ui.displayString("Player " + (i + 1) + " choose a new name:");
                    name = ui.getCommand();
                }
            } while (!verifyName.equalsIgnoreCase("Y"));


            //Character choice text
            ui.displayString("Player " + (i + 1) + "(" + name + "): " + " Please choose a character");

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

              //Ensures theres no two players having the same token
                for (Player p : players) { 
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
    }
    
}
