
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class BoardPanel extends JPanel{
	private BufferedImage boardImage;
	
	public BoardPanel() {
		try {
			boardImage = ImageIO.read(this.getClass().getResource("Board.jpg"));
		}catch(IOException ex) {
			System.out.println("Couldn't find image...." + ex);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.drawImage(boardImage, 0, 0, 600, 600, this);
	}
	
	
}
