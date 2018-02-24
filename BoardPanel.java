import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;

/**
 * A panel that displays the gameboard
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class BoardPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private BufferedImage boardImage; //Image of the board
	private TileGrid grid = new TileGrid(); //The game grid
	private Weapons weapons;
	//private Tokens tokens;
	private Players players;
	
	public BoardPanel(Players players, Weapons weapons){	
		this.weapons = weapons;
		this.players = players;
		
		//Check if image input is available [Exceptions]
		 try {
	            boardImage = ImageIO.read(this.getClass().getResource("Images/Board.jpg"));
	     }catch (IOException ex) {
	            System.out.println("Couldn't find image...." + ex);
	     }
		 
		 //Set the size of the panel
		 setPreferredSize(new Dimension(boardImage.getHeight(), boardImage.getWidth()));
	}
	
	public void set(Players players, Weapons weapons) {
		this.weapons = weapons;
		this.players = players;
	}
	@Override
	/**
	 * A paint component to draw tokens onto the board
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 =(Graphics2D) g;
        g2.drawImage(boardImage, 0, 0, boardImage.getHeight(), boardImage.getWidth(), this);
        grid.drawGrid(g);
        
        for(Weapon weapon: weapons) {
        	weapon.drawWeapon(g2);
        }
        
        for(Player player: players) {
        	player.getToken().drawToken(g2);
        }
	}

}
