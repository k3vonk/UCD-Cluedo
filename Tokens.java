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
	
	    
	 
	public Tokens() {
		tokens = new ArrayList<Token>(DEFAULT_CAPACITY);
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
