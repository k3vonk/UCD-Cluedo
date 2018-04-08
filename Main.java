/**
 * Everything is happening in Main, it glues different functionality together
 * Allows players to set up the game
 * Allows turns to be occured
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class Main {

    private int capacity;				//Amount of players playing game.
    private Players players;			//Empty players (Default 6 players with no tokens)
    private Players dummies; 			//Tokens that haven't been selected
    private Weapons weapons;			//Fixed set of weapons on board
    private CluedoUI ui;				//Starts with an empty board with no players
    private StartUp start;				//Start class for start up methods
    private Turn turn;					//Turn class goes through the player's moves

    public Main() {
        this.players = new Players();
        this.dummies = new Players();
        this.weapons = new Weapons();
        this.ui 	 = new CluedoUI(players, weapons, dummies);
        this.start   = new StartUp(ui);
        this.turn    = new Turn(ui, players, weapons, dummies);
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
        capacity = start.size();							//Asks the user for the number of players
        this.players = new Players(capacity);				//Instantiates the players
        this.dummies = new Players(6);
        start.addPlayers(players);							//Asks user for their name and the character they wish to play as
        start.position(players);							//Reposition players in array list
        weapons.createWeapons();							//Instantiates the weapons
        CommandPanel.updateCommands();						//Shows users available commands

        start.divideCards(players); 						//Divides cards according to the rules and shows undealt cards to turn class
        turn.setMurderEnvelope(start.getMurderEnvelope()); 	//Shows murder envelope contents to turn class

        //Create dummies if there isn't 6 players
        if(capacity < 6) {
        	start.addDummies(players, dummies);
        }
        
        //Update and display the board
        ui.setBoard(players, weapons, dummies);
        ui.display();
        
        //Sets up turn with updated Classes
        turn.setTurn(players, weapons, dummies);
    }

    //Takes players turns
    public void turn() {
        turn.turns();
    }



    public static void main(String[] args) {
        StartScreen startscreen = new StartScreen();
        startscreen.gameStart();
        while(!startscreen.letsPlay){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Main game = new Main();

        game.start();
        game.turn();
    }

}