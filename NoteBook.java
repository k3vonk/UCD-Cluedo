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
	
	String playerList[] = {"Plum","White","Scarlet","Green","Mustard","Peacock"};
	String weaponsList[] = {"Candle Stick","Dagger","Lead Pipe","Revolver","Rope","Spanner"};
	String roomList[] = {"Dining Room","Conservatory","Study","Billard Room","Lounge","Library","Ball Room","Kitchen","Hall"};

	
	public NoteBook(ArrayList<Card> unusedCards, ArrayList<Card> hand) {
		this.unusedCards = unusedCards;
		this.hand	= hand;
	}
	
	/**
	 * Displays the notes of a player
	 */
	public void showNotes() {

		// Override the column class so that it now supports check boxes, will be required on later releases.
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

		//Checking if tokens cards are either in hand or undealt
		for(int count = 0; count < 6;count++) {
			boolean found = false;
			int count2 = 0;
			while (count2 < hand.size()) {
				if (hand.get(count2).toString() == playerList[count]) {
					model.addRow(new Object[]{"Player",playerList[count],"X",""});
					found = true;
				}
				count2++;
			}
			if (found == false) {
				if (unusedCards.size() == 1) {
					if (unusedCards.get(0).toString() == playerList[count]) {
						model.addRow(new Object[]{"Player",playerList[count],"","A"});
						found=true;
					}
				} else if (unusedCards.size() == 2) {
					if (unusedCards.get(0).toString() == playerList[count] ||
							unusedCards.get(1).toString() == playerList[count]) {
						model.addRow(new Object[]{"Player",playerList[count],"","A"});
						found = true;
					}
				}else if (unusedCards.size() == 3) {
					if (unusedCards.get(0).toString() == playerList[count] ||
							unusedCards.get(1).toString() == playerList[count] ||
							unusedCards.get(2).toString() == playerList[count]) {
						model.addRow(new Object[]{"Player",playerList[count],"","A"});
						found=true;
					}
				} if(found==false){
					model.addRow(new Object[]{"Player",playerList[count],"",""});
				}
			}
		}

		//Checking if weapons cards are either in hand or undealt
		for(int count = 0; count < 6;count++) {
			boolean found = false;
			int count2 = 0;
			while (count2 < hand.size()) {
				if (hand.get(count2).toString() == weaponsList[count]) {
					model.addRow(new Object[]{"Weapon",weaponsList[count],"X",""});
					found = true;
				}
				count2++;
			}
			if (found == false) {
				if (unusedCards.size() == 1) {
					if (unusedCards.get(0).toString() == weaponsList[count]) {
						model.addRow(new Object[]{"Weapon",weaponsList[count],"","A"});
						found=true;
					}
				} else if (unusedCards.size() == 2) {
					if (unusedCards.get(0).toString() == weaponsList[count] ||
							unusedCards.get(1).toString() == weaponsList[count]) {
						model.addRow(new Object[]{"Weapon",weaponsList[count],"","A"});
						found = true;
					}
				}else if (unusedCards.size() == 3) {
					if (unusedCards.get(0).toString() == weaponsList[count] ||
							unusedCards.get(1).toString() == weaponsList[count] ||
							unusedCards.get(2).toString() == weaponsList[count]) {
						model.addRow(new Object[]{"Weapon",weaponsList[count],"","A"});
						found=true;
					}
				} if(found==false){
					model.addRow(new Object[]{"Weapon",weaponsList[count],"",""});
				}
			}
		}
		
		//Checking if room cards are either in hand or undealt
		for(int count = 0; count < 9;count++) {
			boolean found = false;
			int count2 = 0;
			while (count2 < hand.size()) {
				if (hand.get(count2).toString() == roomList[count]) {
					model.addRow(new Object[]{"Room",roomList[count],"X",""});
					found = true;
				}
				count2++;
			}
			if (found == false) {
				if (unusedCards.size() == 1) {
					if (unusedCards.get(0).toString() == roomList[count]) {
						model.addRow(new Object[]{"Room",roomList[count],"","A"});
						found=true;
					}
				} else if (unusedCards.size() == 2) {
					if (unusedCards.get(0).toString() == roomList[count] ||
							unusedCards.get(1).toString() == roomList[count]) {
						model.addRow(new Object[]{"Room",roomList[count],"","A"});
						found = true;
					}
				} else if (unusedCards.size() == 3) {
					if (unusedCards.get(0).toString() == roomList[count] ||
							unusedCards.get(1).toString() == roomList[count] ||
							unusedCards.get(2).toString() == roomList[count]) {
						model.addRow(new Object[]{"Room",roomList[count],"","A"});
						found=true;
					}
				}  if(found==false){
					model.addRow(new Object[]{"Room",roomList[count],"",""});
				}
			}
		}
	}
	
}
