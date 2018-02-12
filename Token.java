import javax.swing.JPanel;

/**
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
@SuppressWarnings("serial")
public class Token extends JPanel {
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
