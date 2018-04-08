/**
 * Card class will represent a single card by its name and type
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class Card {

    // To store name and type
    private String name;
    private int type;
    
    //Bunch of available cards in the game
	public static String[] suspects =
	        {"Plum", "White", "Scarlet", "Green", "Mustard", "Peacock"};
	
	public static String[] weapons =
	        {"Candle Stick", "Dagger", "Lead Pipe", "Revolver", "Rope",
	                "Spanner"};
	
	public static String[] rooms =
	        {"Kitchen", "Ball Room", "Conservatory", "Dining Room", "Billiard Room", "library",
	                "Lounge", "Hall", "Study"};

    // Intialize the card
    public Card(String name, int type) {
        this.name = name;
        this.type = type;
    }

    // Get the card's name
    public String getName() {
        return name;
    }

    // Get Type
    public int getType() {
        return type;
    }

    // In order to print out the card while testing.
    public String toString() {
        return this.name;
    }
    
    /**
     * Searches given string array for the given string(findMe)
     * @param stringArray an array of strings to search through
     * @param findMe string to find within the array of strings
     * @return true if string is found
     */
    public static Boolean findInStringArray(String[] stringArray, String findMe) {
        for (String currentString : stringArray) {
            if (currentString.equalsIgnoreCase(findMe) || currentString.replaceAll("\\s+",
                    "").equalsIgnoreCase(findMe)) {
                return true;
            }
        }
        return false;
    }
}
