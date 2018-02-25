
public class Turn {
	
	private CluedoUI ui;
	
	public Turn(CluedoUI ui) {
		this.ui = ui;
	}
	
	/**
     * Each player takes turns
     */
    public void turns(Players players) {
        String command; //Text that is entered
        Dice dice = new Dice();
        boolean valid;  //Check if action is valid
        do {
            for (int i = 0; i < players.getCapacity(); i++) {
                valid = false;

                //First prompt for first board action
                ui.displayString(players.currPlayer(i) + "'s turn to move.\nType roll to roll the dice.");
                CommandPanel.updateUserImage(players.getPlayer(i).getImagePath());

                do {

                    command = ui.getCommand();
                    ui.displayString(players.currPlayer(i) + ": " + command);

                    if (command.equalsIgnoreCase("roll")) {
                        dice.rollDice();
                        ui.displayString(players.currPlayer(i) + " rolled " + dice.getRoll());
                        valid = true;
                    } else {
                        ui.displayString("Whoops! Wrong command. Try 'roll' this time :)");
                    }
                } while (!valid);

               // movement(dice.getRoll(), i);


                //After all actions
                do {
                    ui.displayString(players.currPlayer(i)
                            + " no actions left. Type 'done' to pass turn, or 'quit' to end the "
                            + "game");
                    command = ui.getCommand();
                    ui.displayString(players.currPlayer(i) + ": " + command);
                } while (!command.equalsIgnoreCase("done"));
            }
        } while (true);
    }
}
