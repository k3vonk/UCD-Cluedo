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
	}
	 
	public Tokens(int capacity) {
		tokens = new ArrayList<Token>(capacity);
	}
	 
	//Creating a list of playerss [Fixed starting location]
	public void createPlayers(String name, int choice) {
		
		switch(choice) {
		case 1:
			tokens.add(new Token("Plum", Color.magenta, grid.map[23][19], name));
			break;
		case 2:
			tokens.add(new Token("White", Color.white, grid.map[9][0], name));
			break;
		case 3:
			tokens.add(new Token("Scarlet", Color.red, grid.map[7][24], name));
			break;
		case 4:
			tokens.add(new Token("Green", Color.green, grid.map[14][0], name));
			break;
		case 5:
			tokens.add(new Token("Mustard", Color.yellow, grid.map[0][17], name));
			break;
		case 6:
			tokens.add(new Token("Peacock", Color.blue, grid.map[23][6], name));
			break;
		default:
			break;
		}
		
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
