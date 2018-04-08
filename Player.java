import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A class that represents a player containing their name and player they choose
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class Player {
    private String name;    //Name of player
    private int choice;    //player's choice
    private Token token;
    private String imagePath;    //tokens image
    private ArrayList<Card> cards = new ArrayList<>();        //Cards that a player has
    private NoteBook note;                            //A players notebook
    private Boolean alive = true;
    // Ensure that a player that made a bad accusation doesn't play again.

    //Constructor
    public Player(String name, int choice) {
        this.name = name;
        this.choice = choice;
        this.token = null;
        this.note = null;
    }

    //Accessor of player name
    public String getName() {
        return name;
    }

    //Returns the player's choice
    public int getChoice() {
        return choice;
    }

    //Sets the player's token
    public void setToken(Token token) {
        this.token = token;
    }

    //Return the player's token
    public Token getToken() {
        return token;
    }

    //Sets the path of the token image
    public void setImagePath(String path) {
        this.imagePath = path;
    }

    //Returns the tokens image
    public String getImagePath() {
        return imagePath;
    }

    //Returns true if matching token
    public boolean hasName(String name) {
        return this.name.toLowerCase().equals(name.toLowerCase().trim());
    }

    //Returns true if matching choice
    public boolean hasChoice(int choice) {
        return !(this.choice == choice);
    }

    //Checks if two tokens are on the same tile
    public boolean hasTile(Tile tile) {
        return this.token.getPosition().equals(tile);
    }

    public void giveCard(Card card) {
        this.cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    //Sets notebook
    public void setNoteBook(ArrayList<Card> undealt) {
        this.note = new NoteBook(undealt, cards);
    }

    //Displays player's note
    public void displayNote() {
        note.showNotes();
    }

    //Kills the player if he/she makes a wrong accusation
    public void killPlayer() {
        alive = false;
    }

    //Checks if player is alive
    public Boolean isAlive() {
        return alive;
    }



    public static void pickACard(ArrayList<Card> cards, Player fromPlayer, Player toPlayer) {

        JDialog gameFinished = new JDialog();
        JPanel congratsPanel = new JPanel();
        JPanel cardsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        congratsPanel.setLayout(new GridBagLayout());

        // Make sure user knows that this is the most important frame for them.
        gameFinished.setAlwaysOnTop(true);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        StringBuilder xd = new StringBuilder();
        xd.append("Pick a card to show from (");
        for (Card x : cards) {
            xd.append(" - " + x.getName());
        }
        xd.append(" - )");
        JLabel name = new JLabel(xd.toString());
        name.setFont(new Font(new JLabel().getFont().toString(), Font.PLAIN, 17));

        //HouseKeeping
        c.gridx = 0;
        c.gridy = 0;
        congratsPanel.add(name);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;

        ArrayList<Card> hand = toPlayer.getCards();
        try {
            for (Card x : hand) {
                System.out.println(x.getName());
                JLabel cardLabel = imageToResizedLabel(x.getName().toLowerCase().replaceAll(" ", "")
                        + ".jpg");
                cardsPanel.add(cardLabel, c);
                cardLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        /**
                         *
                         *
                         *
                         *
                         * Do anything with card ( x )
                         * NoteBook.setSeen(x);
                         *
                         *
                         *
                         *
                         *
                         */

                    }
                });
            }
        } catch (IOException ex) {
            System.out.println("Unable to find cards to present to player");
        }

        congratsPanel.add(cardsPanel, c);
        c.gridy = 3;
        c.gridwidth = 1;
        JButton cancel = new JButton("I have none");
        cancel.setPreferredSize(new Dimension(400, 40));
        buttonsPanel.add(cancel, c);
        c.gridy = 4;
        congratsPanel.add(buttonsPanel, c);


        //name.setBorder(BorderFactory.createEmptyBorder(100, 20, 10, 20));
        gameFinished.add(congratsPanel);
        gameFinished.setModal(true);
        gameFinished.setAlwaysOnTop(true);
        gameFinished.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        gameFinished.setSize(864, 500);
        gameFinished.setLocationRelativeTo(null);
        gameFinished.setVisible(true);

    }

    /**
     * Method that converts an image into a jlabel object to easily place onto the panel
     *
     * @param path Location to the image
     * @return A JLabel that contains the image
     * @throws IOException When provided a path that doesn't exist
     */
    public static JLabel imageToResizedLabel(String path) throws IOException {
        BufferedImage myPicture = ImageIO.read(
                CluedoUI.class.getClassLoader().getResourceAsStream(path)); // Reads the file
        Image resizedImage = myPicture.getScaledInstance(105, 190,
                myPicture.SCALE_SMOOTH); // Resize it so that it looks better.
        JLabel picLabel = new JLabel(new ImageIcon(resizedImage)); // Convert to JLabel object
        return picLabel; // Return
    }

}
