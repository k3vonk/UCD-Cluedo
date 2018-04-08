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
	
	private ArrayList<Card> unusedCards;		//Undealt cards
	private ArrayList<Card> hand;			//Player cards
	private ArrayList<Card> seenCard = new ArrayList<Card>();       //Someone answered a question and showed you the card
	
	//Cards get compared to these lists when seeing if a player owns them or not
	String playerList[] = {"Plum","White","Scarlet","Green","Mustard","Peacock"};
	String weaponsList[] = {"Candle Stick","Dagger","Lead Pipe","Revolver","Rope","Spanner"};
	String roomList[] = {"Dining Room","Conservatory","Study","Billard Room","Lounge","Library","Ball Room","Kitchen","Hall"};

	
	public NoteBook(ArrayList<Card> unusedCards, ArrayList<Card> hand) {
		this.unusedCards = unusedCards;
		this.hand	= hand;
	}
	
	public void addSeenCard(Card card) {
		seenCard.add(card);
	}
	
	public void getSeenCard() {
		for(Card card: seenCard) {
			System.out.println(card.toString());
		}
	}
	
	/**
	 * Displays the notes of a player
	 */
	public void showNotes() {


		// Ovverride the columnclass so that it now supports checkboxes, will be required on later releases.
		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public Class getColumnClass(int column) {
				return getValueAt(0, column).getClass();
			}
		};

		// Create a new table to store notes. 
		JTable table = new JTable(model);

		// Add the column names one by one.
		model.addColumn("TYPE");
		model.addColumn("NAME");
		model.addColumn("YOUR HAND");
		model.addColumn("UNDEALT");


		JScrollPane tata = new JScrollPane(table);
		// set the size as it was being crowded and didn't look good at all.
		tata.setPreferredSize(new Dimension(1000, 170));
		// output the table within the pane using joptionpane with the object.
		JFrame displayProperties = new JFrame();
		// Add the table to the frame
		displayProperties.add(tata);
		displayProperties.setSize(900, 400);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		displayProperties.setLocation(dim.width / 2 - displayProperties.getSize().width / 2, dim.height / 2 - displayProperties.getSize().height / 2);
		displayProperties.setVisible(true);

		for(int count = 0; count < 6;count++) { //This loop iterates through each of the six characters
			boolean found = false; //Checks if a card was found so we dont display it twice
			int count2 = 0;
			while (count2 < hand.size()) { //This loop iterates through every card owned by the player
				if (hand.get(count2).toString().equalsIgnoreCase(playerList[count])) {
					model.addRow(new Object[]{"Player",playerList[count],"X",""});
					found = true;
				}
				count2++;
			}
			if (!found) { //The following if statements basically find and indicate undealt cards
				if (unusedCards.size() == 1) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(playerList[count])) {
						model.addRow(new Object[]{"Player",playerList[count],"","A"});
						found=true;
					}
				} else if (unusedCards.size() == 2) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(playerList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(playerList[count])) {
						model.addRow(new Object[]{"Player",playerList[count],"","A"});
						found = true;
					}
				}else if (unusedCards.size() == 3) {//There can only be at most 3 undealt cards with 2-6 players
					if (unusedCards.get(0).toString().equalsIgnoreCase(playerList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(playerList[count]) ||
							unusedCards.get(2).toString().equalsIgnoreCase(playerList[count])) {
						model.addRow(new Object[]{"Player",playerList[count],"","A"});
						found=true;
					}
				} if(!found){ //If a card doesent match the player's cards and isn't undealt, it's added normally without a marker
					model.addRow(new Object[]{"Player",playerList[count],"",""});
				}
			}
		}
		//The following is much like the previous except with weapon cards instead of players
		for(int count = 0; count < 6;count++) {
			boolean found = false;
			int count2 = 0;
			while (count2 < hand.size()) {
				if (hand.get(count2).toString().equalsIgnoreCase(weaponsList[count])) {
					model.addRow(new Object[]{"Weapon",weaponsList[count],"X",""});
					found = true;
				}
				count2++;
			}
			if (!found) {
				if (unusedCards.size() == 1) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(weaponsList[count])) {
						model.addRow(new Object[]{"Weapon",weaponsList[count],"","A"});
						found=true;
					}
				} else if (unusedCards.size() == 2) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(weaponsList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(weaponsList[count])) {
						model.addRow(new Object[]{"Weapon",weaponsList[count],"","A"});
						found = true;
					}
				}else if (unusedCards.size() == 3) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(weaponsList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(weaponsList[count]) ||
							unusedCards.get(2).toString().equalsIgnoreCase(weaponsList[count])) {
						model.addRow(new Object[]{"Weapon",weaponsList[count],"","A"});
						found=true;
					}
				} if(!found){
					model.addRow(new Object[]{"Weapon",weaponsList[count],"",""});
				}
			}
		}
		//The following is much like the previous except with room cards instead of players
		for(int count = 0; count < 9;count++) {
			boolean found = false;
			int count2 = 0;
			while (count2 < hand.size()) {
				if (hand.get(count2).toString().equalsIgnoreCase(roomList[count])) {
					model.addRow(new Object[]{"Room",roomList[count],"X",""});
					found = true;
				}
				count2++;
			}
			if (!found) {
				if (unusedCards.size() == 1) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(roomList[count])) {
						model.addRow(new Object[]{"Room",roomList[count],"","A"});
						found=true;
					}
				} else if (unusedCards.size() == 2) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(roomList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(roomList[count])) {
						model.addRow(new Object[]{"Room",roomList[count],"","A"});
						found = true;
					}
				} else if (unusedCards.size() == 3) {
					if (unusedCards.get(0).toString().equalsIgnoreCase(roomList[count]) ||
							unusedCards.get(1).toString().equalsIgnoreCase(roomList[count]) ||
							unusedCards.get(2).toString().equalsIgnoreCase(roomList[count])) {
						model.addRow(new Object[]{"Room",roomList[count],"","A"});
						found=true;
					}
				}  if(!found){
					model.addRow(new Object[]{"Room",roomList[count],"",""});
				}
			}
		}
	}
	
}
