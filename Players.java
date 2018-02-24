import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class Players implements Iterable<Player>, Iterator<Player> {

	private static final int DEFAULT_CAPACITY = 6;
	private int capacity;
	private final ArrayList<Player> players;
	private Iterator<Player> iterator;
	private TileGrid grid = new TileGrid();

	  	 
	public Players() {
		players = new ArrayList<Player>(DEFAULT_CAPACITY);
	}
	 
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
		case 1:
			players.get(curr).setToken(new Token("Plum", Color.magenta, grid.map[19][23]));
			break;
		case 2:
			players.get(curr).setToken(new Token("White", Color.white, grid.map[0][9]));
			break;
		case 3:
			players.get(curr).setToken(new Token("Scarlet", Color.red, grid.map[24][7]));
			break;
		case 4:
			players.get(curr).setToken(new Token("Green", Color.green, grid.map[0][14]));
			break;
		case 5:
			players.get(curr).setToken(new Token("Mustard", Color.yellow, grid.map[17][0]));
			break;
		case 6:
			players.get(curr).setToken(new Token("Peacock", Color.blue, grid.map[6][23]));
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
	
	public int getCapacity() {
		return capacity;
	}
	
	public Player getPlayer(int index) {
		return players.get(index);
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
	/*
	public boolean getChoice(int choice) {
		for (Player Player : players) {
			if (Player.hasChoice(choice)) {
				return true;
			}
		}
		return false;
	}*/


}