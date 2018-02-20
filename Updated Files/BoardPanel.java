import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.*;
public class BoardPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private BufferedImage boardImage;
	private TileGrid grid = new TileGrid();
	private final Weapons weapons;
	private final Players players;
	
	public BoardPanel(Players players, Weapons weapons){	
		
		this.weapons = weapons;
		this.players = players;
		 try {
	            boardImage = ImageIO.read(this.getClass().getResource("Images/Board.jpg"));
	     }catch (IOException ex) {
	            System.out.println("Couldn't find image...." + ex);
	     }
		 
		 
		 
		 setPreferredSize(new Dimension(boardImage.getHeight(), boardImage.getWidth()));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 =(Graphics2D) g;
        g2.drawImage(boardImage, 0, 0, boardImage.getHeight(), boardImage.getWidth(), this);
        grid.drawGrid(g);
        
        for(Weapon weapon: weapons) {
        	weapon.drawWeapon(g2);
        }
        
        for(Player player: players) {
        	player.drawPlayer(g2);
        }
	}

}
