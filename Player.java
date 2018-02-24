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
	private String imagePath;

	
	//Constructor
	public Player(String name, int choice) {
		this.name = name;
		this.choice = choice;
		switch(choice){
			case 1:
				imagePath = "Profiler/Plum.png";
				break;
			case 2:
				imagePath = "Profiler/White.png";
				break;
			case 3:
				imagePath = "Profiler/Scarlet.png";
				break;
			case 4:
				imagePath = "Profiler/Green.png";
				break;
			case 5:
				imagePath = "Profiler/Mustard.png";
				break;
			case 6:
				imagePath = "Profiler/Peacock.png";
				break;
			default:
				break;
		}
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

	public void setImagePath(String path) {this.imagePath = path;}

	public String getImagePath() {return imagePath;}
	
	//Returns true if matching player
    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }
    
    //Returns true if matching choice
    public boolean hasChoice(int choice) {
        return !(this.choice == choice);
    }
}
