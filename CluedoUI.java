/**
 * A cluedoUI that consists of board panel, command panel, information panel
 * 
 * @author Royal, Richard, Gajun
 */
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

@SuppressWarnings("serial")
public class CluedoUI extends JFrame {
	
	//Panel & Frame of the cluedo board
    private JPanel mainPanel;
    private JFrame frame;
    private BoardPanel board;
    
    
    private Boolean testingUser = true;
    private Boolean alerted = false;

    public CluedoUI(BoardPanel board) {
        frame = new JFrame();
        mainPanel = new JPanel();
        this.board = board;
       
        DrawMainPanel();// initialize panel and add it to the frame
     
        // Set up main frame
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 820);
        frame.setTitle("Cluedo");
        frame.setVisible(true);
        frame.setResizable(false);


    }

    /*Methods to move the characters 1 space.
    For now these move both players and weapons. The input is the name of what you want to move
    and direction to move.*/
    public boolean Movement(String direction, String name) {
        return board.moveToken(direction, name);
    }

    /*
    Method to set the weapons somewhere. Unused for assignment 1.
    public void setWeaponTile(String name, int newRow, int newColumn){board.setWeaponTile
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
        containerPanel.setPreferredSize(new Dimension(1000, 750));
        containerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Cluedo"));
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));

        // GUI things, setting elements up to look their best. Y_AXIS layout for a top-down structure
        board.setPreferredSize(new Dimension(600, 700));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Implement the smaller panels and other elements into the parent panels.
        containerPanel.add(board);
        containerPanel.add(InformationPanel);
        mainPanel.add(containerPanel);
        mainPanel.add(bottomContainerPanel);


        /**
         * Submitting movement and then carrying out the action if conditions are met
         * Hard coded
         */
        bottomContainerPanel.submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String input = bottomContainerPanel.getInput();
            	
            	//move player else move weapon
            	if(testingUser) {
            		board.moveToken(input, "White");
            		
            		//Display this message if not shown before when moving
            		 if (!alerted) {
                         InformationPanel.updateContent(false,
                                 ">In order to start moving the weapon, type \"-1\" into the "
                                         + "input!");
                         alerted = true;
                     }

            		 InformationPanel.updateContent(false, "Mrs.White: " + input);
            	}
            	else { //move weapon
            		board.moveToken(input, "Ca");
            		 InformationPanel.updateContent(false, "Candle Stick: " + input);
            	}
            	
            	//Change from player to weapon
            	if(input.equals("-1")) {
            		testingUser = false;
            		InformationPanel.updateContent(false,">You're now controlling a weapon.");
            	}
            }
        });

        //bottomContainerPanel.submit.addActionListener(e -> board.playerMovementTest("white"));

        //Starting message
        InformationPanel.updateContent(true,
                ">Hello Mr tester dude! \n>You're now controlling player Mrs.White!\nType:\n\tup"
                        + "\n\tdown\n\tleft\n\tright\nto move him accordingly!\nHave fun!");

    }

    /**
     * Method that reads images from the disk and converts them into a JLabel to display them on the
     * GUI
     * Used to show current player and player's cards for the time being.
     */
    public static JLabel imageToLabel(String path) throws IOException {
        BufferedImage myPicture = ImageIO.read(CluedoUI.class.getResourceAsStream(path));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        return picLabel;
    }


}
