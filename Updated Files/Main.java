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
	
	
	
	private String capacity;
	private int[] num;
	
	private Tokens tokens = new Tokens();
	private final Weapons weapons = new Weapons();
	private CluedoUI ui = new CluedoUI(tokens, weapons);
	private final TileGrid grid = new TileGrid();
	
	public enum Token {PLUM, WHITE, SCARLET, GREEN, MUSTARD, PEACOCK}
	
	public static boolean isNum(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	/**
	 * A method that acquires the number of players, playing the game
	 */
	private void capacity() {
        ui.displayString("Enter the number of players: [min: 2, max: 6]");
        
        //Ensures right values are entered into the game
        do {
        	do {
	            capacity = ui.getCommand();
	            ui.displayString(capacity);
	            if(!isNum(capacity)) {
	            	ui.displayString("\'" + capacity + "\'" + " is not an integer.....");
	            }
        	}while(!isNum(capacity));
	        
        	if(Integer.parseInt(capacity) < 2 || Integer.parseInt(capacity) > 6) {
        		ui.displayString("Enter a valid integer between [2 - 6].....");
        	}
        } while (Integer.parseInt(capacity) < 2 || Integer.parseInt(capacity) > 6);
        
        this.tokens = new Tokens(Integer.parseInt(capacity));
        num = new int[Integer.parseInt(capacity)];
    }
	
	private void addPlayers() {
		String name;
		String verify;
		String choice;
		for(int i = 0; i < Integer.parseInt(capacity); i++) {
			int j  = 0;
			ui.displayString("Player " + (i+1) + " name: ");
			name = ui.getCommand();
			do {
				ui.displayString("\'" + name + "\'" + ", Are you sure with this name [Y/N]");
				verify = ui.getCommand();
				if(verify.equals("N")) {
					ui.displayString("Player " + (i+1) + " choose a new name:");
					name = ui.getCommand();
				}
			}while(!verify.equals("Y"));
			
			ui.displayString("Player " + (i+1) + "(" + name + "): "+ " Please choose a character");
			for(Token p: Token.values()) {
				ui.displayString(++j +". " + p.toString());
			}
			//Choose characters - if they arent picked
			
			do {
				choice = ui.getCommand();
			}while(!isNum(choice));
				
			//Add if name is null
			//get object and check if it is null Y/N 
			//If its not null make him find something else
			tokens.createPlayers(name, Integer.parseInt(choice));
		}
		ui.setBoard(tokens, weapons);
		ui.display();
	}
	
	public static void main(String[] args) {
		Main game = new Main();
		game.capacity();
		game.addPlayers();
	}

}
