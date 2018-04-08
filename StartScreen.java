import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A menu that opens at the start of the game
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */

public class StartScreen extends JLabel implements ActionListener
{
    JLabel screen;
    JFrame frame;
    JButton play,quit;
    boolean letsPlay = false; //Becomes true when the play button is pressed

    public void gameStart()
    {
        setLayout(new BorderLayout());

        InputStream stream = getClass().getResourceAsStream("CluedoStart.jpg");
        ImageIcon icon = null;
        try {
            icon = new ImageIcon(ImageIO.read(stream));
        } catch (IOException e) {
            System.out.println("Couldn't find image...." + e);
        }
        screen = new JLabel(icon);
        screen.setVisible(true);
        frame = new JFrame("Cluedo");
        play=new JButton("PLAY");
        quit= new JButton("QUIT");
        frame.add(play, BorderLayout.CENTER);
        frame.add(quit, BorderLayout.PAGE_END);
        frame.add(screen, BorderLayout.PAGE_START);

        play.addActionListener(this);
        quit.addActionListener(this);

        frame.setSize(600,370);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==play){ //Closes the menu and starts the game up
            letsPlay = true;
            frame.dispose();
        }

        if(e.getSource()==quit) //Ends program if player chooses quit
        {
            System.exit(0);
        }


    }

}