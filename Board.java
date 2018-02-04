/**
 * A class that contains the image of the board
 */
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel{

	private BufferedImage boardImage;
	
	//Constructor
	public Board() {
		/**
		 * Catch any errors if the image doesnt exist
		 */
		try {
			boardImage = ImageIO.read(this.getClass().getResource("Board.jpg"));
		}catch(IOException ex) {
			System.out.println("Couldn't find image...." + ex);
		}
		
	}
	
	//Draw the image 
	public void drawMe(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(boardImage, 0, 0, boardImage.getWidth(), boardImage.getHeight(), null);	
	}
	
}
