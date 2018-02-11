import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import jdk.nashorn.internal.scripts.JO;

@SuppressWarnings("serial")
public class CluedoUI extends JFrame {

    JPanel mainPanel = new JPanel();
    private BoardPanel test;
    Boolean testingUser = true;
    Boolean alerted = false;

    public CluedoUI(BoardPanel test) {
        JFrame frame = new JFrame();
        this.test = test;
        // initialize panel and add it to the frame
        DrawMainPanel();
        frame.add(mainPanel);
        // Set up main frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setTitle("Cluedo");
        frame.setVisible(true);
        frame.setResizable(false);


    }

    //Methods to move the characters 1 space.
    //For now these move both players and weapons. The input is the name of what you want to move
    // and direction to move.
    public boolean Movement(String direction, String name) {
        return test.moveToken(direction, name);
    }

    /*
    Method to set the weapons somewhere. Unused for assignment 1.
    public void setWeaponTile(String name, int newRow, int newColumn){test.setWeaponTile
    (name,newRow,newColumn);}
    */

    /*
     * Method that draws the main panel and the panels within it.
     */
    public void DrawMainPanel() {
        // Set up the three panels.
        CommandPanel bottomContainerPanel = new CommandPanel();
        JPanel containerPanel = new JPanel();
        InformationPanel InformationPanel = new InformationPanel();

        // Set up the main panel to look good, this panel contains all other panels.
        containerPanel.setPreferredSize(new Dimension(1000, 700));
        containerPanel
                .setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                        "Cluedo"));
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));

        // GUI things, setting elements up to look their best. Y_AXIS layout for a top-down
        // structure
        test.setPreferredSize(new Dimension(600, 700));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Implement the smaller panels and other elements into the parent panels.
        containerPanel.add(test);
        containerPanel.add(InformationPanel);
        mainPanel.add(containerPanel);
        mainPanel.add(bottomContainerPanel);


        bottomContainerPanel.submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = bottomContainerPanel.getInput();
                if (input.equals("up") || input.equals("down") || input.equals("left")
                        || input.equals("right")) {
                    if (testingUser) {
                        test.moveToken(bottomContainerPanel.getInput(), "White");
                        if (!alerted) {
                            InformationPanel.updateContent(false,
                                    ">In order to start moving the weapon, type \"-1\" into the "
                                            + "input!");
                            alerted = true;
                        }
                    } else {
                        test.moveToken(bottomContainerPanel.getInput(), "C");
                    }
                } else if (input.equals("-1")) {
                    testingUser = false;
                    InformationPanel.updateContent(false,
                            ">You're now controlling a weapon.");

                } else {
                    InformationPanel.updateContent(false,
                            "Mrs.White: " + bottomContainerPanel.getInput());
                }
            }
        });

        //bottomContainerPanel.submit.addActionListener(e -> test.playerMovementTest("white"));

        InformationPanel.updateContent(false,
                ">Hello Mr tester dude! \n>You're now controlling player Mrs.White! Type either up,"
                        + "down,left or maybe even right to move him accordingly! Have fun!");

    }

    /**
     * Method that reads images from the disk and converts them into a JLabel to display them on the
     * GUI
     * Used to show current player and player's cards for the time being.
     */
    public static JLabel imageToLabel(String path) throws IOException {
        BufferedImage myPicture = ImageIO.read(new File(path));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        return picLabel;
    }


}
