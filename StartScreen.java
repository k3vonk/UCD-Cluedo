import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.*;

public class StartScreen extends JLabel implements ActionListener
{
    JLabel screen;
    JFrame frame;
    JButton play,quit;
    boolean letsPlay = false;
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

        frame.add(play, BorderLayout.PAGE_START);
        frame.add(quit, BorderLayout.PAGE_END);
        frame.add(screen, BorderLayout.CENTER);

        play.addActionListener(this);
        quit.addActionListener(this);

        frame.setSize(600,370);
        frame.setResizable(false);
        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==play){
            letsPlay = true;
            frame.dispose();
        }

        if(e.getSource()==quit)
        {
            System.exit(0);
        }


    }

}