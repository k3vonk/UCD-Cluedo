import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

public class Questions implements Iterable<Question>, Iterator<Question> {
    private ArrayList<Question> questions = new ArrayList<Question>();
    private Iterator<Question> iterator;

    public boolean check = false;

    //Adds new question to the array list
    public void addQuestion(Player player, String token, String weapon, String room) {
        questions.add(new Question(player, token, weapon, room));
    }

    //Gets the size of the questions array list
    public int getCapacity() {
        return questions.size();
    }

    public List<String> getLog() {
        List<String> log = new ArrayList<String>();
        for (int i = getCapacity() - 1; i >= 0; i--) {
            log.add(questions.get(i).toString());
        }
        return log;
    }

    /**
     * A suggestion of who was the murder, murder weapon & the room it was done in
     */
    public boolean question(Players players, int curr, CluedoUI ui, Turn turn) {
        ui.displayString("==========SUGGESTION==========");
        ui.displayString(players.currPlayer(curr) + ": " + "I suggest it was done by[token]");
        CommandPanel.updateCommands(Card.suspects);

        String token, weapon;
        boolean valid = false;
        int selectedOption = -1;

        do {
            token = ui.getCommand();
            ui.displayString(players.currPlayer(curr) + ": " + token);

            //Search if name input is correct
            valid = Card.findInStringArray(Card.suspects, token);

            //Error message
            if (!valid) {
                ui.displayString(token + " is not a valid token");
            }
        } while (!valid);

        ui.displayString(players.currPlayer(curr) + ": " + "I suggest it was done with[weapon]");
        CommandPanel.updateCommands(Card.weapons);

        do {
            weapon = ui.getCommand();
            ui.displayString(players.currPlayer(curr) + ": " + weapon);

            //Search if weapon input is correct
            valid = Card.findInStringArray(Card.weapons, weapon);

            if (!valid) {
                ui.displayString(weapon + " is not a valid weapon");
            }
        } while (!valid);

        //The room they are in
        ui.displayString(players.currPlayer(curr) + ": " + "I suggest it was done in[room]");
        ui.displayString(players.currPlayer(curr) + ": " + Card.rooms[players.getTile(
                curr).getRoom()
                - 1]);

        //Teleport the weapon & player to the room
        turn.weaponTeleport(weapon, players.getTile(curr).getRoom());
        turn.playerTeleport(token, players.getTile(curr).getRoom());
        ui.display();

        //Save the question into the array
        addQuestion(players.getPlayer(curr), token, weapon, Card.rooms[players.getTile(
                curr).getRoom() - 1]);

        questionHandOver(players, ui); //Question every player

        ui.clearContent(); //Clears info panel

        do {
            //Pops up a JOptionPane and ensures turn is passed
            selectedOption = JOptionPane.showConfirmDialog(null,
                    "Turn handed over to " + players.currPlayer(curr) + "?", "TURN",
                    JOptionPane.YES_NO_OPTION);

        } while (selectedOption != JOptionPane.YES_NO_OPTION);

        ui.displayString("=====" + players.currPlayer(curr) + "TURN" + "======");

        //Checks if a player answered question
        if (check) {
            ui.displayString("A player has answered your question!");
            ui.displayString("Card: " + players.getPlayer(
                    curr).getNoteBook().getLatestCard().toUpperCase());
        } else {
            ui.displayString("Nobody could answer your question");
        }

        CommandPanel.updateUserImage(players.getPlayer(curr).getImagePath());
        CommandPanel.updateMovesReamining(-1);

        return true; //player has asked a question
    }

    /**
     * Hands over currPlayer's question to other players according to array
     * When a player answers we stop questioning
     */
    public void questionHandOver(Players players, CluedoUI ui) {
        int selectedOption = -1; //Ensures the question is for the right person
        boolean answered;         //Boolean if answered
        check = false;             //Sentinel to check if a player had a card
        String done = " ";

        //Run through the players array
        for (int i = 0; i < players.getCapacity(); i++) {
            ui.displayString("ROTATING PLAYER.......");
            //Have window be still for 2 seconds
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            answered = false;
            ui.clearContent(); //Clears info panel

            //Sets person picture on who we are asking
            CommandPanel.updateUserImage(players.getPlayer(i).getImagePath());
            CommandPanel.updateMovesReamining(-3);


            //Cannot ask the question to the questioner.
            if (players.getPlayer(i) != questions.get(getCapacity() - 1).getQuestioner()) {
                do {
                    //Pops up a JOptionPane and ensures question is passed
                    selectedOption = JOptionPane.showConfirmDialog(null,
                            "Question handed over to " + players.currPlayer(i) + "?", "QUESTIONING",
                            JOptionPane.YES_NO_OPTION);

                } while (selectedOption != JOptionPane.YES_NO_OPTION);

                do {
                    ui.displayString("=====" + players.currPlayer(i) + "ANSWERING=====");
                    ui.displayString(questions.get(getCapacity() - 1).toString());
                    //	CommandPanel.updateCommands("empty");
                    pickACard(questions.get(getCapacity() - 1), players.getPlayer(i));

                    if (!check) {
                        done = ui.getCommand();
                    }
                    if (done.equalsIgnoreCase("done") || check) {
                        answered = true;
                    }
                } while (!answered);
            }

            if (check) {
                ui.displayString("Question Answered");
                break;
            }
        }
    }

    public void pickACard(Question question, Player toPlayer) {

        // Initial setup

        JDialog gameFinished = new JDialog();
        JPanel congratsPanel = new JPanel();
        JPanel cardsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        congratsPanel.setLayout(new GridBagLayout());


        /* Make sure user knows that this is the most important frame for them by making it stick
           out */
        gameFinished.setAlwaysOnTop(true);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;


        // Shows the user the list of cards that the current player is looking for
        StringBuilder xd = new StringBuilder();
        xd.append("Pick a card to show from (");
        xd.append(" - " + question.getTokenName());
        xd.append(" - " + question.getWeaponName());
        xd.append(" - " + question.getRoomName());
        xd.append(" - )");
        JLabel name = new JLabel(xd.toString());
        name.setFont(new Font(new JLabel().getFont().toString(), Font.PLAIN, 17));

        // Add the label to the top of the frame
        c.gridx = 0;
        c.gridy = 0;
        congratsPanel.add(name);

        // Adjust the constraints to add cards below the label
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;

        ArrayList<Card> hand = toPlayer.getCards();
        try {
            for (Card x : hand) {
                String xName = x.getName().replaceAll("\\s+", "");
                JLabel cardLabel = CluedoUI.imageToResizedLabel(xName
                        + ".jpg", 90, 140);
                cardsPanel.add(cardLabel, c);
                cardLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        /*If mouse clicked on card is the same as the question cards, then set
                         Answerer & add to notebook */
                        if (xName.equalsIgnoreCase(question.room.replaceAll("\\s+", ""))
                                || xName.equalsIgnoreCase(question.token.replaceAll("\\s+", ""))
                                || xName.equalsIgnoreCase(question.weapon.replaceAll("\\s+", ""))) {
                            question.setAnswerer(
                                    toPlayer);
                            //This player answered the question
                            question.getQuestioner().getNoteBook().addSeenCard(
                                    toPlayer.getCard(xName));    //Questioner now sees a new card

                            gameFinished.dispose();
                            check = true; //Someone answered the question
                        } else {
                            JOptionPane.showMessageDialog(gameFinished,
                                    "That's not a card that's being questioned, pick another "
                                            + "card!");
                        }
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


        // Player doesn't have any of the cards, let them skip without picking one to show.
        if (!toPlayer.hasCard(question.room) && !toPlayer.hasCard(question.weapon)
                && !toPlayer.hasCard(question.token)) {
            buttonsPanel.add(cancel, c);
            //Dispose frame if user responds that he/she has none of the cards
            cancel.addActionListener(e -> gameFinished.dispose());
        }

        // Add button below images
        c.gridy = 4;
        congratsPanel.add(buttonsPanel, c);


        // Make it look good
        name.setBorder(BorderFactory.createEmptyBorder(100, 20, 10, 20));
        gameFinished.add(congratsPanel);
        gameFinished.setModal(true);
        gameFinished.setAlwaysOnTop(false);
        gameFinished.setUndecorated(true);
        gameFinished.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        gameFinished.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        gameFinished.setSize(964, 500);
        gameFinished.setLocationRelativeTo(null);
        gameFinished.setVisible(true);

    }

    @Override
    public Iterator<Question> iterator() {
        iterator = questions.iterator();
        return iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Question next() {
        return iterator.next();
    }

}
