import javax.swing.JPanel;

/**
 * A Token superclass that represents the basic entity of weapons and players
 * @author Gajun, Richard
 *
 */
@SuppressWarnings("serial")
public class Token extends JPanel{
	Tile position; //A tile position of a weapon/player
	
	//Constructor
	public Token(Tile position) {
		this.position = position;
	}
	
	//Return the Tokens current position
	public Tile getTile() {
		return position;
	}
	
	//Changes the Tokens position
	public void setTile(Tile position) {
		this.position = position;
	}

}
