import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Questions implements Iterable<Question>, Iterator<Question>{
	private ArrayList<Question> questions = new ArrayList<Question>();
	private Iterator<Question> iterator;
	
	//Adds new question to the arraylist
	public void addQuestion(Player player,String token, String weapon, String room) {
		questions.add(new Question(player, token, weapon, room));
	}
	
	//Gets the size of the questions arraylist
	public int getCapacity() {
		return questions.size();
	}
	
    /**
     * A suggestion of who was the murder, murder weapon & the room it was done in
     */
    public void question(Players players, int curr, CluedoUI ui, Turn turn) {
        ui.displayString("==========SUGGESTION==========");
        ui.displayString(players.currPlayer(curr) + ": " + "I suggest it was done by[token]");
        CommandPanel.updateCommands(Card.suspects);

        String token, weapon;
        boolean valid = false;

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
        ui.displayString(players.currPlayer(curr) + ": " + Card.rooms[players.getTile(curr).getRoom()
                - 1]);

        //Teleport the weapon & player to the room
        turn.weaponTeleport(weapon, players.getTile(curr).getRoom());
        turn.playerTeleport(token, players.getTile(curr).getRoom());
        ui.display();
        
        //Save the question into the array
        addQuestion(players.getPlayer(curr), token, weapon, Card.rooms[players.getTile(curr).getRoom() - 1]);
        
        questionHandOver(players, ui);
    }
    
    /**
     * Hands over currPlayer's question to other players according to array
     * When a player answers we stop questioning
     * @param players
     * @param ui
     */
    public void questionHandOver(Players players, CluedoUI ui) {
    	int selectedOption = -1; //Ensures the question is for the right person
    	boolean answered = false;
    	
    	//Run through the players array
    	for(int i = 0; i < players.getCapacity();i++) {
    		ui.clearContent(); //Clears info panel
    		CommandPanel.updateUserImage(players.getPlayer(i).getImagePath());
			CommandPanel.updateMovesReamining(-3);
    		//Cannot ask the question to the questioner.
    		if(players.getPlayer(i) != questions.get(getCapacity() - 1).getQuestioner()) {
    			do {
    			  //Pops up a JOptionPane and ensures question is passed
    			  selectedOption = JOptionPane.showConfirmDialog(null,
    					  "Question handed over to " + players.currPlayer(i) + "?", "QUESTIONING", JOptionPane.YES_NO_OPTION); 
    	
    			}while(selectedOption != JOptionPane.YES_NO_OPTION);
    			
    			do {
	    			ui.displayString("=====" + players.currPlayer(i) + "ANSWERING=====");
	    			ui.displayString(questions.get(getCapacity() - 1).toString());
	    		//	CommandPanel.updateCommands(commands);
	    			Questions.pickACard(questions.get(getCapacity() - 1), players.getPlayer(i));
	    			if(ui.getCommand().equals("done")) {
	    				answered = true;
	    				
	    			}
    			}while(!answered);
    		}
    	}
    }
    
    public static void pickACard(Question question, Player toPlayer) {

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
        xd.append(" - " + question.getTokenName());
        xd.append(" - " + question.getWeaponName());
        xd.append(" - " + question.getRoomName());
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
            	String xName = x.getName().toLowerCase().replaceAll(" ", "");
                JLabel cardLabel = imageToResizedLabel(xName
                        + ".jpg");
                cardsPanel.add(cardLabel, c);
                cardLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                    	if(xName.equalsIgnoreCase(question.room) || xName.equalsIgnoreCase(question.token) || xName.equalsIgnoreCase(question.weapon)){
							gameFinished.dispose();
						}

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

        if(!toPlayer.hasCard(question.room) && !toPlayer.hasCard(question.weapon) && !toPlayer.hasCard(question.token))
		{
			buttonsPanel.add(cancel, c);
			//Dispose frame if user responds that he/she has none of the cards
			cancel.addActionListener(e -> gameFinished.dispose());
		}

		System.out.println(toPlayer.getCards());

        c.gridy = 4;
        congratsPanel.add(buttonsPanel, c);


        //name.setBorder(BorderFactory.createEmptyBorder(100, 20, 10, 20));
        gameFinished.add(congratsPanel);
        gameFinished.setModal(true);
        gameFinished.setAlwaysOnTop(true);
		gameFinished.setUndecorated(true);
		gameFinished.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        gameFinished.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        gameFinished.setSize(964, 500);
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
        Image resizedImage = myPicture.getScaledInstance(70, 130,
                myPicture.SCALE_SMOOTH); // Resize it so that it looks better.
        JLabel picLabel = new JLabel(new ImageIcon(resizedImage)); // Convert to JLabel object
        return picLabel; // Return
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
