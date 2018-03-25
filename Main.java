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

    private int capacity;				//Amount of players playing game.
    private Players players;			//Empty players (Default 6 players with no tokens)
    private Weapons weapons;			//Fixed set of weapons on board
    private CluedoUI ui;				//Starts with an empty board with no players
    private StartUp start;				//Start class for starting methods
    private Turn turn;					//Turn class goes through the player's moves

    public Main() {
    	this.players = new Players();
    	this.weapons = new Weapons();
    	this.ui 	 = new CluedoUI(players, weapons);
    	this.start   = new StartUp(ui);
    	this.turn    = new Turn(ui);
    }
    
    /**
     * Start of the game - game setup
     * Amount of players
     * Getting names and token of players
     * Reorder what player goes first by dice roll
     * Create weapons
     * Divide card pile
     */
    public void start() {
        capacity = start.size();						//Asks the user for the number of players
        this.players = new Players(capacity);			//Instantiates the players
        start.addPlayers(players);						//Asks user for their name and the character they wish to play
        start.position(players);						//Reposition players in arraylist
        weapons.createWeapons();						//Instantiates the weapons
        CommandPanel.updateCommands();					//Shows users available commands
        start.divideCards(players);						//Divides cards according to the rules
        
        //Update and display the board
        ui.setBoard(players, weapons);
        ui.display();
    }
    
    //Takes players turns
    public void turn() {
        turn.turns(players);
    }
   

    public static void main(String[] args) {
        Main game = new Main();
        game.start();
       	game.turn();

    }

}