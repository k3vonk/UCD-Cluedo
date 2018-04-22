package bots;

import gameengine.*;

public class Bot1 implements BotAPI {

    // The public API of Bot must not change
    // This is ONLY class that you can edit in the program
    // Rename Bot to the name of your team. Use camel case.
    // Bot may not alter the state of the board or the player objects
    // It may only inspect the state of the board and the player objects

    private Player player;
    private PlayersInfo playersInfo;
    private Map map;
    private Dice dice;
    private Log log;
    private Deck deck;
    private int diceTotal;

    public Bot1 (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        this.player = player;
        this.playersInfo = playersInfo;
        this.map = map;
        this.dice = dice;
        this.log = log;
        this.deck = deck;
        this.diceTotal = dice.getTotal();
    }

    public String getName() {
        return "MAGA"; // must match the class name
    }

    public String getVersion () {
        return "0.1";   // change on a new release
    }

    public String getCommand() {
        //Roll dice
    	if(diceTotal == -1) {
    		return "roll";
    	}//In corridor and has moves
    	else if(map.isCorridor(player.getToken().getPosition()) && diceTotal > 0) {
    		diceTotal--;
    		//If walks into room diceTotal == 0;
    		return getMove();
    	}//In room and has moves (recently rolled)
    	else if(!map.isCorridor(player.getToken().getPosition()) && diceTotal > 0) {
    		//exit room or 
    		//passage
    	}//Recently entered a room
    	else if(!map.isCorridor(player.getToken().getPosition()) && diceTotal == 0) {
    		//just entered a room 
    		//question
    	}else if(false) { //Accusation room
    		//Accuse
    	}
    	
    	//Ending
    	diceTotal = -1;
        return "done";
    }

    public String getMove() {
       
        return "r";
    }

    public String getSuspect() {
        // Add your code here
        return Names.SUSPECT_NAMES[0];
    }

    public String getWeapon() {
        // Add your code here
        return Names.WEAPON_NAMES[0];
    }

    public String getRoom() {
        // Add your code here
        return Names.ROOM_NAMES[0];
    }

    public String getDoor() {
        // Add your code here
        return "1";
    }

    public String getCard(Cards matchingCards) {
        // Add your code here
        return matchingCards.get().toString();
    }

    public void notifyResponse(Log response) {
        // Add your code here
    }

    public void notifyPlayerName(String playerName) {
        // Add your code here
    }

    public void notifyTurnOver(String playerName, String position) {
        // Add your code here
    }

    public void notifyQuery(String playerName, String query) {
        // Add your code here
    }

    public void notifyReply(String playerName, boolean cardShown) {
        // Add your code here
    }

}
