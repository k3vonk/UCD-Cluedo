import java.awt.*;
import javax.swing.*;

/**
 * A frame that congratulates the user for winning the game
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */

public class Congrats {

    public Congrats(String userName){


        // declare frames.
        JDialog gameFinished = new JDialog();
        JPanel congratsPanel = new JPanel();

        // Make sure user knows that this is the most important frame for them.
        gameFinished.setAlwaysOnTop (true);

        // Add banner
        try{
            congratsPanel.add(CluedoUI.imageToLabel("won.png"));
        }catch(Exception ex){
            System.out.println(ex);
        }


        JLabel name = new JLabel("Congrats, "+userName+"! You're one hell of a detective!");
        name.setFont(new Font("TimesRoman", Font.PLAIN, 30));


        //name.setBorder(BorderFactory.createEmptyBorder(100, 20, 10, 20));
        gameFinished.add(congratsPanel);
        gameFinished.setLayout(new GridBagLayout());


        // Housekeeping
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        c.ipady = 40;
        congratsPanel.setBackground(new Color(170,134, 188));
        gameFinished.add(name, c);
        gameFinished.setModal (true);
        gameFinished.setAlwaysOnTop (true);
        gameFinished.setModalityType (Dialog.ModalityType.APPLICATION_MODAL);
        gameFinished.setSize(864,500);
        gameFinished.setLocationRelativeTo(null);
        gameFinished.setVisible(true);

    }

}
