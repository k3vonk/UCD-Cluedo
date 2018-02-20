import javax.swing.JOptionPane;

/**
 * Main that is executed that displays everything and actions are taken here before being used by the classes
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class Main {
	
	private final Players tokens = new Players();
	private final Weapons weapons = new Weapons();
	private final CluedoUI ui = new CluedoUI(tokens, weapons);
	private final TileGrid grid = new TileGrid();

	private void testUI() {
        String command;
        //Starting message
        ui.displayString(">Hello Mr tester dude! \n>You're now controlling player Mrs.White!\nType:\n\tup"
                        + "\n\tdown\n\tleft\n\tright\nto move him accordingly!\nHave fun!");

        Weapon currWeapon = weapons.get("Dagger");
        Tile currTile = currWeapon.getPosition();
        
        
        //Movement
	do {
    	   command = ui.getCommand();
    	 //Catch if array is out of bounds
       	try {
		        if (command.equals("up")) {
		        	currTile = grid.map[currWeapon.getPosition().getColumn()][currWeapon.getPosition().getRow() - 1];
		        
		        }
		        else if(command.equals("down")) {
		        	currTile = grid.map[currWeapon.getPosition().getColumn()][currWeapon.getPosition().getRow() + 1];
		        
		        }
		        else if(command.equals("left")) {
		        	currTile = grid.map[currWeapon.getPosition().getColumn() - 1][currWeapon.getPosition().getRow()];
		        
		        }
		        else if (command.equals("right")){
		        	currTile = grid.map[currWeapon.getPosition().getColumn() + 1][currWeapon.getPosition().getRow()];
		        }
		        
		    	
       	}
       	catch(ArrayIndexOutOfBoundsException e) {
       		JOptionPane.showMessageDialog(null, "Invalid direction...Direction does not exist");
       	}
       	
       	currWeapon.moveBy(currTile);
       	ui.display();
       		
       }while(!command.equals("-1"));
        
        do {
            command = ui.getCommand();
            ui.displayString(command);
            ui.display();
        } while (!command.equals("quit"));
    }
	
	public static void main(String[] args) {
		Main game = new Main();
		game.testUI();
	}

}
