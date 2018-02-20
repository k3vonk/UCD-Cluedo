

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

       /* 
        Weapon curr = weapons.get("Dagger");
        
        curr.moveBy(grid.map[curr.getPosition().getRow() - 1][curr.getPosition().getColumn()]);
        */
        
        
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
