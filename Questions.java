import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

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
    		
    		//Cannot ask the question to the questioner.
    		if(players.getPlayer(i) != questions.get(getCapacity() - 1).getQuestioner()) {
    			do {
    			  //Pops up a JOptionPane and ensures question is passed
    			  selectedOption = JOptionPane.showConfirmDialog(null,
    					  "Question handed over to " + players.currPlayer(i) + "?", "QUESTIONING", JOptionPane.YES_NO_OPTION); 
    			  CommandPanel.updateUserImage(players.getPlayer(i).getImagePath());
    			  CommandPanel.updateMovesReamining(-3);
    			}while(selectedOption != JOptionPane.YES_NO_OPTION);
    			
    			do {
	    			ui.displayString("=====" + players.currPlayer(i) + "ANSWERING=====");
	    			ui.displayString(questions.get(getCapacity() - 1).toString());
	    		//	CommandPanel.updateCommands(commands);
	    			if(ui.getCommand().equals("done")) {
	    				answered = true;
	    			}
    			}while(!answered);
    		}
    	}
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
