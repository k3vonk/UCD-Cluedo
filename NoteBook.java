import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * A notebook that keeps track of players cards
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class NoteBook {

    private ArrayList<Card> unusedCards;                            //Undealt cards
    private ArrayList<Card> hand;                                    //Player cards
    private ArrayList<Card> seenCard = new ArrayList<Card>();
            //Someone answered a question and showed you the card

    public NoteBook(ArrayList<Card> unusedCards, ArrayList<Card> hand) {
        this.unusedCards = unusedCards;
        this.hand = hand;
    }

    //Someone showed u a card, add it as seen
    public void addSeenCard(Card card) {
        seenCard.add(card);
    }

    //Testing to see if cards are added
    public void getSeenCard() {
        for (Card card : seenCard) {
            System.out.println(card.toString());
        }
    }

    public String getLatestCard() {
        return seenCard.get(seenCard.size() - 1).toString();
    }

    /**
     * Displays the notes of a player
     */
    public void showNotes() {

        // Ovverride so that the cells are no longer editable
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            // Makes the cells not editable
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        // Create a new table to store notes.
        JTable table = new JTable(model);

        // Prevent user from dragging around columns.
        table.getTableHeader().setReorderingAllowed(false);

        // Add the column names one by one.
        model.addColumn("TYPE");
        model.addColumn("NAME");
        model.addColumn("YOUR HAND");
        model.addColumn("UNDEALT/SEEN");


        JScrollPane tata = new JScrollPane(table);
        // set the size as it was being crowded and didn't look good at all.
        tata.setPreferredSize(new Dimension(1000, 170));
        // output the table within the pane using joptionpane with the object.
        JFrame displayProperties = new JFrame("NoteBook");
        // Add the table to the frame
        displayProperties.add(tata);
        displayProperties.setSize(900, 400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        displayProperties.setLocation(dim.width / 2 - displayProperties.getSize().width / 2,
                dim.height / 2 - displayProperties.getSize().height / 2);
        displayProperties.setVisible(true);

        for (int count = 0; count < 6;
                count++) { //This loop iterates through each of the six characters
            boolean found = false; //Checks if a card was found so we dont display it twice
            int count2 = 0;
            while (count2
                    < hand.size()) { //This loop iterates through every card owned by the player
                if (hand.get(count2).toString().equalsIgnoreCase(Card.suspects[count])) {
                    model.addRow(new Object[]{"Player", Card.suspects[count], "X", ""});
                    found = true;
                }
                count2++;
            }
            if (!found) { //The following if statements basically find and indicate undealt cards
                if (unusedCards.size() == 1) {
                    if (unusedCards.get(0).toString().equalsIgnoreCase(Card.suspects[count])) {
                        model.addRow(new Object[]{"Player", Card.suspects[count], "", "A"});
                        found = true;
                    }
                } else if (unusedCards.size() == 2) {
                    if (unusedCards.get(0).toString().equalsIgnoreCase(Card.suspects[count]) ||
                            unusedCards.get(1).toString().equalsIgnoreCase(Card.suspects[count])) {
                        model.addRow(new Object[]{"Player", Card.suspects[count], "", "A"});
                        found = true;
                    }
                } else if (unusedCards.size()
                        == 3) {//There can only be at most 3 undealt cards with 2-6 players
                    if (unusedCards.get(0).toString().equalsIgnoreCase(Card.suspects[count]) ||
                            unusedCards.get(1).toString().equalsIgnoreCase(Card.suspects[count]) ||
                            unusedCards.get(2).toString().equalsIgnoreCase(Card.suspects[count])) {
                        model.addRow(new Object[]{"Player", Card.suspects[count], "", "A"});
                        found = true;
                    }
                }
                if (!found) { //If a card doesent match the player's cards and isn't undealt,
                	// it's added normally without a marker
                    Boolean foundCard = false;
                    for (Card c : seenCard) {
                        if (c.getName().equalsIgnoreCase(Card.suspects[count])) {
                            foundCard = true;
                        }
                    }
                    if (foundCard) {
                        model.addRow(new Object[]{"Player", Card.suspects[count], "", "V"});
                    } else {
                        model.addRow(new Object[]{"Player", Card.suspects[count], "", ""});
                    }

                }
            }
        }
        //The following is much like the previous except with weapon cards instead of players
        for (int count = 0; count < 6; count++) {
            boolean found = false;
            int count2 = 0;
            while (count2 < hand.size()) {
                if (hand.get(count2).toString().equalsIgnoreCase(Card.weapons[count])) {
                    model.addRow(new Object[]{"Weapon", Card.weapons[count], "X", ""});
                    found = true;
                }
                count2++;
            }
            if (!found) {
                if (unusedCards.size() == 1) {
                    if (unusedCards.get(0).toString().equalsIgnoreCase(Card.weapons[count])) {
                        model.addRow(new Object[]{"Weapon", Card.weapons[count], "", "A"});
                        found = true;
                    }
                } else if (unusedCards.size() == 2) {
                    if (unusedCards.get(0).toString().equalsIgnoreCase(Card.weapons[count]) ||
                            unusedCards.get(1).toString().equalsIgnoreCase(Card.weapons[count])) {
                        model.addRow(new Object[]{"Weapon", Card.weapons[count], "", "A"});
                        found = true;
                    }
                } else if (unusedCards.size() == 3) {
                    if (unusedCards.get(0).toString().equalsIgnoreCase(Card.weapons[count]) ||
                            unusedCards.get(1).toString().equalsIgnoreCase(Card.weapons[count]) ||
                            unusedCards.get(2).toString().equalsIgnoreCase(Card.weapons[count])) {
                        model.addRow(new Object[]{"Weapon", Card.weapons[count], "", "A"});
                        found = true;
                    }
                }
                if (!found) {
                    Boolean foundCard = false;
                    for (Card c : seenCard) {
                        if (c.getName().equalsIgnoreCase(Card.weapons[count])) {
                            foundCard = true;
                        }
                    }
                    if (foundCard) {
                        model.addRow(new Object[]{"Weapon", Card.weapons[count], "", "V"});
                    } else {
                        model.addRow(new Object[]{"Weapon", Card.weapons[count], "", ""});
                    }
                }
            }
        }
        //The following is much like the previous except with room cards instead of players
        for (int count = 0; count < 9; count++) {
            boolean found = false;
            int count2 = 0;
            while (count2 < hand.size()) {
                if (hand.get(count2).toString().equalsIgnoreCase(Card.rooms[count])) {
                    model.addRow(new Object[]{"Room", Card.rooms[count], "X", ""});
                    found = true;
                }
                count2++;
            }
            if (!found) {
                if (unusedCards.size() == 1) {
                    if (unusedCards.get(0).toString().equalsIgnoreCase(Card.rooms[count])) {
                        model.addRow(new Object[]{"Room", Card.rooms[count], "", "A"});
                        found = true;
                    }
                } else if (unusedCards.size() == 2) {
                    if (unusedCards.get(0).toString().equalsIgnoreCase(Card.rooms[count]) ||
                            unusedCards.get(1).toString().equalsIgnoreCase(Card.rooms[count])) {
                        model.addRow(new Object[]{"Room", Card.rooms[count], "", "A"});
                        found = true;
                    }
                } else if (unusedCards.size() == 3) {
                    if (unusedCards.get(0).toString().equalsIgnoreCase(Card.rooms[count]) ||
                            unusedCards.get(1).toString().equalsIgnoreCase(Card.rooms[count]) ||
                            unusedCards.get(2).toString().equalsIgnoreCase(Card.rooms[count])) {
                        model.addRow(new Object[]{"Room", Card.rooms[count], "", "A"});
                        found = true;
                    }
                }
                if (!found) {
                    Boolean foundCard = false;
                    for (Card c : seenCard) {
                        if (c.getName().equalsIgnoreCase(Card.rooms[count])) {
                            foundCard = true;
                        }
                    }
                    if (foundCard) {
                        model.addRow(new Object[]{"Room", Card.rooms[count], "", "V"});
                    } else {
                        model.addRow(new Object[]{"Room", Card.rooms[count], "", ""});
                    }
                }
            }
        }
    }

}
