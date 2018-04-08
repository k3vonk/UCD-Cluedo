import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This UI combines three main panel concepts together onto one frame in which
 * users can interact with.
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class CluedoUI {

    //Size of the frame
    private static final int FRAME_WIDTH = 1015;
    private static final int FRAME_HEIGHT = 830;

    private BoardPanel board;
    private InformationPanel info;
    private CommandPanel command;
    private JFrame frame;
    private Random rand = new Random();

    //Constructor
    public CluedoUI(Players players, Weapons weapons, Players dummies) {
    	frame = new JFrame();
        JPanel mainPanel = new JPanel();
        board = new BoardPanel(players, weapons, dummies);
        command = new CommandPanel();
        info = new InformationPanel();

        //Set up the main panel to look good, this panel contains two panels.
        mainPanel.add(board);
        mainPanel.add(info);
        mainPanel.setPreferredSize(new Dimension(1000, 750));
        mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cluedo"));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));


        //Position all the panels into their correct places
        frame.add(mainPanel);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle("Cluedo");
        frame.add(mainPanel, BorderLayout.LINE_START);
        frame.add(command, BorderLayout.PAGE_END);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Sets the board with weapons[default] and tokens which depends on the user inputs
     */
    public void setBoard(Players players, Weapons weapons, Players dummies) {
        board.set(players, weapons, dummies);
    }

    /**
     * Shows the dice rolling on-screen
     */
    public void drawDice(int roll1, int roll2){
        if(roll1!=0) { //If roll is 0, we are hiding the dice and thus no roll animation needs to be shown
            for (int i = 0; i < 8; i++) {
                board.drawDice(rand.nextInt(6) + 1,rand.nextInt(6) + 1);
                display();
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        board.drawDice(roll1, roll2); //Draws the final result
    }

    /**
     * @return A String that the user types
     */
    public String getCommand() {
        String input = command.getCommand();
    	if(input.equalsIgnoreCase("quit")) { //The program ends whenever quit is entered
    		System.exit(0);
    	}
        return input;
    }

    /**
     * Repaint the board to show updated tokens
     */
    public void display() {
        board.repaint();
    }

    /**
     * Takes a string in which it gets updated onto the information panel
     *
     * @param string contains information of a tokens turn
     */
    public void displayString(String string) {
        info.updateContent(string);
    }

    /**
     * Method to clear information panel
     */
    public void clearContent(){
        info.clearContent();
    }

    /**
     * Finds the path of an image
     * @param path
     * @return
     * @throws IOException
     */
    public static JLabel imageToLabel(String path) throws IOException {
        BufferedImage myPicture = ImageIO.read(CluedoUI.class.getClassLoader().getResourceAsStream(path));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        return picLabel;
    }


    /**
     * Method that converts an image into a jlabel object to easily place onto the panel
     *
     * @param path Location to the image
     * @param width sets the images width
     * @param height sets the image's height
     * @return A JLabel that contains the image
     * @throws IOException When provided a path that doesn't exist
     */
    public static JLabel imageToResizedLabel(String path, int width, int height) throws IOException {
        BufferedImage myPicture = ImageIO.read(CluedoUI.class.getClassLoader().getResourceAsStream(path)); // Reads the file
        Image resizedImage = myPicture.getScaledInstance(width, height,
                myPicture.SCALE_SMOOTH); // Resize it so that it looks better.
        JLabel picLabel = new JLabel(new ImageIcon(resizedImage)); // Convert to JLabel object
        return picLabel; // Return
    }

}
