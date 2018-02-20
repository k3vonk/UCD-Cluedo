import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This creates all the tokens and allows the interface iterable to be used on the class
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class Players implements Iterable<Player>, Iterator<Player> {

	 private final ArrayList<Player> players = new ArrayList<>();
	 private Iterator<Player> iterator;
	 private TileGrid grid = new TileGrid();
	    
	 
	 public Players() {
	       createPlayers();
	    }
	 
	 //Creating a list of playerss [Fixed starting location]
	 public void createPlayers() {
		 players.add(new Player("Plum", Color.magenta, grid.map[23][19]));
		 players.add(new Player("White", Color.white, grid.map[9][0]));
		 players.add(new Player("Scarlet", Color.red, grid.map[7][24]));
		 players.add(new Player("Green", Color.green, grid.map[14][0]));
		 players.add(new Player("Mustard", Color.yellow, grid.map[0][17]));
		 players.add(new Player("Peacock", Color.blue, grid.map[23][6]));
	 }
	  
	 //Iterates to get a specific player
	 public Player get(String name) {
		 for (Player player : players) {
			 if (player.hasName(name)) {
				 return player;
			 }
		 }
		 return null;
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
