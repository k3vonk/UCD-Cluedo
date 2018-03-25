import java.util.ArrayList;

/**
 * Main that is executed that displays everything and actions are taken here before being used by
 * the classes
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class Main {

    private int capacity;                     //Amount of players playing game.
    private ArrayList<Card> murderEnvelope = new ArrayList<>();
    private Players players;				 //Empty players (Default 6 players with no tokens)
    private Weapons weapons;         		//Fixed set of weapons on board
    private CluedoUI ui;     				//Starts with an empty board with no players
    private StartUp start;					//Start class for starting methods
    private Turn turn;                      //Turn class goes through the player's moves

    public Main() {
    	this.players = new Players();
    	this.weapons = new Weapons();
    	this.ui 	 = new CluedoUI(players, weapons);
    	this.start   = new StartUp(ui);
    	this.turn    = new Turn(ui);
    }
    
    /**
     * Start of the game to find the number of players and add players and weapons to game
     */
    public void start() {
        capacity = start.size(); //Asks the user for the number of players
        this.players = new Players(capacity); //Instantiates the players
        start.addPlayers(players); //Asks user for their name and the character they wish to play

        weapons.createWeapons(); //Instantiates the weapons
        CommandPanel.updateCommands(); //Shows users available commands
        murderEnvelope = start.divideCards(players);
        //Update and display the board
        ui.setBoard(players, weapons);
        ui.display();
    }
    
    /*
     * Player with highest roll moves first
     */
    public void position() {
    	ui.displayString("Deciding who goes first......");
    	Dice dice = new Dice();
    	int[] pos = new int[capacity];
    	int position = 0;
    	int highest = 0;
    	
    	//Set up everyones dice rolls first
    	for(int i = 0; i < players.getCapacity(); i++) {
    		//Rolls the dice and displays the result on screen
            dice.rollDice();
            
            pos[i] = dice.getRoll1() + dice.getRoll2();
			ui.drawDice(dice.getRoll1(),dice.getRoll2());
            ui.displayString(players.currPlayer(i) + " rolled " + (dice.getRoll1()+dice.getRoll2()));
            ui.display();
			try { //The resulting dice roll is onscreen for 2 seconds
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ui.drawDice(0,0); //This hides the dice
			ui.display();	
			
			if(highest < pos[i]) {
				highest = pos[i];
				position = i;
			}else if(highest == pos[i]) {
				position = -1;
			}
    	}
    	
    	
    	//Picks out the players with the highest roll (same value) and makes them reroll for next highest
    	int newHighest = 0;
    	boolean different = false;
    	while(position == -1 && !different) {
	    	ui.displayString("Player's with the same highest roll value,\n has to roll again");
	    	for(int i = 0; i < capacity; i++) {
	    		if(pos[i] == highest) {
	    			//Rolls the dice and displays the result onscreen
	                dice.rollDice();
	                
	                pos[i] = highest + dice.getRoll1() + dice.getRoll2(); //These values are higher than other players that didn't roll highest
	    			ui.drawDice(dice.getRoll1(),dice.getRoll2());
	                ui.displayString(players.currPlayer(i) + " rolled " + (dice.getRoll1()+dice.getRoll2()));
	                ui.display();
	    			try { //The resulting dice roll is onscreen for 2 seconds
	    				Thread.sleep(2000);
	    			} catch (InterruptedException e) {
	    				e.printStackTrace();
	    			}
	    			ui.drawDice(0,0); //This hides the dice
	    			ui.display();	
	    			
	    			//Sets highest value while storing their position
	    			if(newHighest < pos[i]) {
	    				newHighest = pos[i];
	    				position = i;
	    				different = true;
	    			}
	    			else if(newHighest == pos[i]) {
	    				different = false;
	    				position = -1;
	    			}
	    		}
	    	}
	    	
	    	highest = newHighest; //ensures reroll will not be the same as the lower numbers
    	}
  
   
    	Player temp = players.getPlayer(position);
    	//players.addFirst(position, temp);
    	
    }
    //Takes players turns
    public void turn() {
        turn.turns(players);
    }
   

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
        game.position();
       	game.turn();

    }

}