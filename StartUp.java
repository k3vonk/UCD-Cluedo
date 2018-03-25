import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
            ui.displayString("Player " + (i + 1) + "'s name?: ");
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
                    "Player " + (i + 1) + "(" + name + "): " + " Please choose a character");

            int j = 0;

            // Method to show only the character options available to the user.
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

                //Ensures theres no two players having the same token
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

    public ArrayList<Card> divideCards(Players players) {
        ArrayList<Card> tokenList = new ArrayList<Card>();
        ArrayList<Card> murderEnvelope = new ArrayList<Card>();
        Random rand = new Random();

        ArrayList<String> characters = new ArrayList<>(
                Arrays.asList("Colonel Mustard", "Professor Plum", "Reverend Green", "Mrs Peacock",
                        "Miss Scarlet", "Mrs White"));
        ArrayList<String> weapons = new ArrayList<>(
                Arrays.asList("Dagger", "Candle Stick", "Revolver", "Rope", "Lead Pipe",
                        "Spanner"));
        ArrayList<String> rooms = new ArrayList<>(
                Arrays.asList("Hall", "Lounge", "Dining Room", "Kitchen", "Ball Room",
                        "Conservatory",
                        "Billard Room", "Library", "Study"));


        Card characterChosen = new Card(characters.get(rand.nextInt(characters.size())), 1);
        Card weaponChosen = new Card(weapons.get(rand.nextInt(weapons.size())), 1);
        Card roomChosen = new Card(rooms.get(rand.nextInt(rooms.size())), 1);
        murderEnvelope.addAll(Arrays.asList(characterChosen, weaponChosen, roomChosen));
        characters.remove(characterChosen.getName());
        weapons.remove(weaponChosen.getName());
        rooms.remove(roomChosen.getName());

        for (String x : characters) {
            tokenList.add(new Card(x, 1));
        }

        for (String x : weapons) {
            tokenList.add(new Card(x, 2));
        }

        for (String x : rooms) {
            tokenList.add(new Card(x, 3));
        }

        while (tokenList.size() > players.getCapacity()) {
            for (int i = 0; i < players.getCapacity(); i++) {
                Card chosenCard = tokenList.get(rand.nextInt(tokenList.size()));
                players.getPlayer(i).giveCard(chosenCard);
                tokenList.remove(chosenCard);
            }
        }
        
        System.out.println("Murder Envelope Contents:" + murderEnvelope);
        for (Player p : players) {
            System.out.println(p.getName() + "'s cards:" + p.getCards());
        }
        System.out.println("Remaining Cards that didn't divide evenly:" + tokenList);
        return murderEnvelope;
    }

}
