import java.util.ArrayList;

/**
 * A class that represents a player containing their name and player they choose
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class Player {
    private String name;    								//Name of player
    private int choice;    									//player's choice
    private Token token;
    private String imagePath;   							//tokens image
    private ArrayList<Card> cards = new ArrayList<>();      //Cards that a player has
    private NoteBook note;                           	 	//A players notebook
    private Boolean alive = true;							// Ensure that a player that made a bad accusation doesn't play again.
    

    //Constructor
    public Player(String name, int choice) {
        this.name = name;
        this.choice = choice;
        this.token = null;
        this.note = null;
    }

    //Accessor of player name
    public String getName() {
        return name;
    }

    //Returns the player's choice
    public int getChoice() {
        return choice;
    }

    //Sets the player's token
    public void setToken(Token token) {
        this.token = token;
    }

    //Return the player's token
    public Token getToken() {
        return token;
    }

    //Sets the path of the token image
    public void setImagePath(String path) {
        this.imagePath = path;
    }

    //Returns the tokens image
    public String getImagePath() {
        return imagePath;
    }

    //Returns true if matching token
    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }

    //Returns true if matching choice
    public boolean hasChoice(int choice) {
        return !(this.choice == choice);
    }

    //Checks if two tokens are on the same tile
    public boolean hasTile(Tile tile) {
        return this.token.getPosition().equals(tile);
    }

    //Gives a card to the player
    public void giveCard(Card card) {
        this.cards.add(card);
    }

    //Getter for cards of a player
    public ArrayList<Card> getCards() {
        return cards;
    }

    //Boolean check if player has a card
    public Boolean hasCard(String y) {
        for (Card x : cards) {
            if (x.toString().equalsIgnoreCase(y)) {
                return true;
            }
        }
        return false;
    }
    
    //Returns card, see if user has that same exact card
    public Card getCard(String name) {
    	for(Card x: cards) {
    		if(x.toString().replaceAll("\\s+","").equalsIgnoreCase(name)) {
    			return x;
    		}
    	}
    	return null;
    }

    //Sets notebook
    public void setNoteBook(ArrayList<Card> undealt) {
        this.note = new NoteBook(undealt, cards);
    }
    
    //Get notebook
    public NoteBook getNoteBook() {
    	return note;
    }

    //Displays player's note
    public void displayNote() {
        note.showNotes();
    }

    //Kills the player if he/she makes a wrong accusation
    public void killPlayer() {
        alive = false;
    }

    //Checks if player is alive
    public Boolean isAlive() {
        return alive;
    }

}
