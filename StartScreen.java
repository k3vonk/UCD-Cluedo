import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartScreen  extends Component{
    private BufferedImage image;

    public StartScreen(){
        try {
            image = ImageIO.read(new File("CluedoStart.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g){
        g.drawImage(image, 0, 0, null);
    }
}
