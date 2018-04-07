import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Congrats {

    public Congrats(String userName){


        JDialog gameFinished = new JDialog();
        JPanel congratsPanel = new JPanel();

        gameFinished.setAlwaysOnTop (true);

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
