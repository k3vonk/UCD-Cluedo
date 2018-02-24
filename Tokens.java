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
public class Tokens implements Iterable<Token>, Iterator<Token> {

	private static final int DEFAULT_CAPACITY = 6;
	private final ArrayList<Token> tokens;
	private Iterator<Token> iterator;
	private TileGrid grid = new TileGrid();
	    
	 
	public Tokens() {
		tokens = new ArrayList<Token>(DEFAULT_CAPACITY);
		createPlayers();
	}
	 
	public Tokens(int capacity) {
		tokens = new ArrayList<Token>(capacity);
	}
	 
	  
	//Iterates to get a specific Token
	public Token get(String name) {
		for (Token Token : tokens) {
			if (Token.hasName(name)) {
				return Token;
			}
		}
		return null;
	}
	
	 //Creating a list of players [Fixed starting location]
    public void createPlayers() {
        tokens.add(new Token("Plum", Color.magenta, grid.map[19][23]));
        tokens.add(new Token("White", Color.white, grid.map[0][9]));
        tokens.add(new Token("Scarlet", Color.red, grid.map[24][7]));
        tokens.add(new Token("Green", Color.green, grid.map[0][14]));
        tokens.add(new Token("Mustard", Color.yellow, grid.map[17][0]));
        tokens.add(new Token("Peacock", Color.blue, grid.map[6][23]));
    }
	@Override
	public Iterator<Token> iterator() {
		iterator = tokens.iterator();
		return iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Token next() {
		return iterator.next();
	}


}
