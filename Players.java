import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * A players class that represents an arraylist of players
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class Players implements Iterable<Player>, Iterator<Player> {

	private static final int DEFAULT_CAPACITY = 6; //Maximum amount of players
	private int capacity; 						   //Number of players in the game
	private final ArrayList<Player> players;       
	private Iterator<Player> iterator;
	private TileGrid grid = new TileGrid();        //Grid to set players on locations

	//Default constructor
	public Players() {
		players = new ArrayList<Player>(DEFAULT_CAPACITY);
	}
	
	//Constructor
	public Players(int capacity) {
		this.capacity = capacity;
		players = new ArrayList<Player>(capacity);
	}
	
	
	//Creating a list of players [Fixed starting location]
	public void createPlayers(String name, int choice) {
		players.add(new Player(name, choice));
	}
	
	//Create tokens for players
	public void createTokens(int curr) {
		int choice = players.get(curr).getChoice();
		
		switch(choice) {
		case 1: //PLUM
			players.get(curr).setToken(new Token("Plum", Color.magenta, grid.map[19][23]));
            players.get(curr).setImagePath("Profiler/Plum.png");
			break;
		case 2: //WHITE
			players.get(curr).setToken(new Token("White", Color.white, grid.map[0][9]));
            players.get(curr).setImagePath("Profiler/White.png");
			break;
		case 3:	//SCARLET
			players.get(curr).setToken(new Token("Scarlet", Color.red, grid.map[24][7]));
            players.get(curr).setImagePath("Profiler/Scarlet.png");
			break;
		case 4: //GREEN
			players.get(curr).setToken(new Token("Green", Color.green, grid.map[0][14]));
            players.get(curr).setImagePath("Profiler/Green.png");
			break;
		case 5: //MUSTARD
			players.get(curr).setToken(new Token("Mustard", Color.yellow, grid.map[17][0]));
            players.get(curr).setImagePath("Profiler/Mustard.png");
			break;
		case 6: //PEACOCK
			players.get(curr).setToken(new Token("Peacock", Color.blue, grid.map[6][23]));
            players.get(curr).setImagePath("Profiler/Peacock.png");
			break;
		default:
			break;
		}
	}
	 
	//Iterates to get a specific Player
	public Player getName(String name) {
		for (Player Player : players) {
			if (Player.hasName(name)) {
				return Player;
			}
		}
		return null;
	}
	
	//Gets the number of people in the arraylist
	public int getCapacity() {
		return capacity;
	}
	
	
	public Player getPlayer(int index) {
		return players.get(index);
	}
	
	//Returns the Tile of the token/player
	public Tile getTile(int index) {
		return players.get(index).getToken().getPosition();
	}
	
	//Start of string containing the player token and the player's name
	public String currPlayer(int index) {
		return players.get(index).getName()  + " [" + players.get(index).getToken().getTokenName() + "] ";
	}
	    
	@Override
	public Iterator<Player> iterator() {
		iterator = players.iterator();
		return iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Player next() {
		return iterator.next();
	}
}