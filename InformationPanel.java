
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * An information panel that displays information to users
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class InformationPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 33;
    private static final int WIDTH = 24;

    private JTextArea infoArea = new JTextArea("", HEIGHT, WIDTH);

    private static JPanel remainingCards = new JPanel();
    //Constructor
    public InformationPanel() {
        JScrollPane scroll = new JScrollPane(infoArea);
        setPreferredSize(new Dimension(300,690));

        //border style
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Information Panel"));

        //Ensures text area doesn't expand to the right, but pushes text downwards
        infoArea.setLineWrap(true);
        infoArea.setEditable(false); //Non editable, so players can't text here
        infoArea.setMaximumSize(infoArea.getPreferredSize());
        add(scroll);
        add(remainingCards);
    }

    /**
     * A method that updates the content on the information panel that is displayed to everyone
     *
     * @param value - The string of text to be displayed
     */
    public void updateContent(String value) {
        // Add string below current value.
        infoArea.append(value + "\n");
        
        // Auto scroll down to current position.
        infoArea.setCaretPosition(infoArea.getDocument().getLength());
    }




    public static JLabel imageToResizedLabel(String path) throws IOException {
        BufferedImage myPicture = ImageIO.read(CluedoUI.class.getClassLoader().getResourceAsStream(path));
        Image resizedImage =  myPicture.getScaledInstance(45, 70,
                myPicture.SCALE_SMOOTH);
        JLabel picLabel = new JLabel(new ImageIcon(resizedImage));
        return picLabel;
    }


    public static void updateRemainingCards(ArrayList<Card> cards){
        remainingCards.removeAll();
        remainingCards.add(new JLabel("Undealt Cards:"));
        for (Card c : cards) {
            try {
                String cardName = c.getName().toLowerCase().replaceAll(" ", "") + ".jpg";
                System.out.println(cardName);
                remainingCards.add(imageToResizedLabel(cardName));
            } catch (IOException ex) {
                System.out.println("Unable to locate files for player's hand.");
            }
        }
        remainingCards.updateUI();
    }




}
