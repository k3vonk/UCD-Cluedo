/**
 * A class that represents a player containing their name and player they choose
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class Player {
	private String name; 	//Name of player
	private int choice;  	//player's choice
	private Token token;   	//Token of a player
	
	//Constructor
	public Player(String name, int choice) {
		this.name = name;
		this.choice = choice;
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
	
	//Returns true if matching player
    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }
    
    //Returns true if matching choice
    public boolean hasChoice(int choice) {
        return !(this.choice == choice);
    }
}
