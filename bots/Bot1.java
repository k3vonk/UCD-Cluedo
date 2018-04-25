package bots;

/**
 * @Team MAGA
 * @Author Royal Thomas 			- 16326926
 * @Author Richard  Otroshchenko 	- 16353416	
 * @Author Gajun Young 				- 16440714
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import gameengine.*;

public class Bot1 implements BotAPI {
	
    private Player player;
    private PlayersInfo playersInfo;
    private Map map;
    private Dice dice;
    private Log log;
    private Deck deck;
    private Random rand = new Random();
    
    //Checks
    private boolean hasQuestioned = false;
    private boolean hasRolled = false;
    private boolean accuse = false;
    
    //Movement
    private String mapDirections[] = {"u", "d", "l", "r"};
    private ArrayList<Coordinates> path;
    private String goToRoom = null;
    private int pathLeft;
    private int squaresMoved = 0;
    
    //Log/Predictions
    private int logSizeCounter = 0;
    private HashMap<String, HashMap<String, ArrayList<Integer>>> guessGame = new HashMap<>();
    private HashMap<String, Integer> answerCounter = new HashMap<>();
    private ArrayList<String> privateSeen = new ArrayList<>();
    private String[] found = {null, null, null};

    public Bot1(Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
        this.player = player;
        this.playersInfo = playersInfo;
        this.map = map;
        this.dice = dice;
        this.log = log;
        this.deck = deck;
    }
    
    //Class name
	public String getName() {
		return "MAGA";
	}

	//Release
	public String getVersion() {
		return "0.1";
	}

	@Override
	public String getCommand() {

		//Setting up predictions
		if(guessGame.isEmpty()) { guessSheetSetUp(); }
		
		
		//Update log 
		if(!log.isEmpty()) { updateGuessSheet(); }
		
		//If a player just walks into a room, reset
		if(player.getToken().isInRoom()) {
			pathLeft = 0;
			goToRoom = null;
		}

        /*Strategy: 
		 *If there is no new destination
		 *1. We have a lot of unseen cards, move to a room where our bot has the roomCard
		 *2. If we are allowed to accuse, move to cellar
		 *3. Go to an unseen card room [Most likely only room left (envelope)]
		 */
        if (goToRoom == null) {
            if (getUnseenWeapons().size() > 1 && getUnseenTokens().size() > 1) {
                goToRoom = getRoomCard();
            } else if (accuse) {
                goToRoom = "Cellar";
            } else {
                goToRoom = getRandomRoomCard();
            }
        }
        
        //Passage
        if (player.getToken().isInRoom() && !hasRolled) {
            if (player.getToken().getRoom().hasPassage()) {
                if (goToRoom.equals(player.getToken().getRoom().getPassageDestination().toString())) {
                    //hasRolled = true;
                    JOptionPane.showMessageDialog(null, "used passage");
                    return "passage";
                }
            }
        }
        
        //If unseen cards are 1 for each category then accuse, else do nothing
        if (getUnseenRooms().size() == 1 && getUnseenTokens().size() == 1 && getUnseenWeapons().size() == 1) {
        	//Accusation
        	/*  System.out.println("TIME TO ACCUSE");
            System.out.println(getUnseenRooms().get(0));
            System.out.println(getUnseenTokens().get(0));
            System.out.println(getUnseenWeapons().get(0)); */
            accuse = true;
        }/* else {
        	System.out.println("===================  Remaining: MAGA ==================="
            + "\nRemaining Cards [T,W,R]: " + getUnseenTokens().size() + "," + getUnseenWeapons().size() + ","
                            + getUnseenRooms().size());
            System.out.println(privateSeen);
        }*/
		
        //Has the player rolled their dice for the start of the round
        if (!hasRolled) {
            //resets (start of turn)
            hasQuestioned = false;
            hasRolled = true;
            squaresMoved = 0;
            return "roll";
        } else if (!map.isCorridor(player.getToken().getPosition())
                && player.getToken().getRoom().accusationAllowed()) {
            return "accuse";
        } else if (!map.isCorridor(player.getToken().getPosition()) && squaresMoved > 0) {
            if (!hasQuestioned) {
                // System.out.println("I'm in a room can accuse");
                hasQuestioned = true;
                return "question";
            } else { //Player already questioned, nothing left to do
                hasRolled = false;
                return "done";
            }
        }

        //resets
        hasRolled = false;
        return "done";       
	}


	//Movement of the bot
	public String getMove() {
		
		Coordinates playerPosition = player.getToken().getPosition();
		
		//Calculates the path from current position to destination
		if(pathLeft == 0) {
			//(test) message
			//System.out.println(player.getName() + " is moving towards room " + goToRoom);
            path = calculatePath(player.getToken().getPosition(),
                    map.getRoom(goToRoom).getDoorCoordinates(0));
            pathLeft += path.size();
		}
		
        if (path.size() == 0) {
            // When the AI tries to go back into the room it is in.
            Coordinates up = map.getNewPosition(playerPosition, "u");
            Coordinates down = map.getNewPosition(playerPosition, "d");
            Coordinates left = map.getNewPosition(playerPosition, "l");
            Coordinates right = map.getNewPosition(playerPosition, "r");

            if (map.isDoor(up, playerPosition)) {
                path.add(playerPosition);
                path.add(up);
            } else if (map.isDoor(down, playerPosition)) {
                path.add(playerPosition);
                path.add(down);
            } else if (map.isDoor(left, playerPosition)) {
                path.add(playerPosition);
                path.add(left);
            } else if (map.isDoor(right, playerPosition)) {
                path.add(playerPosition);
                path.add(right);
            }

           // System.out.println("Path: " + path);
        }
        
        //Direction bot is moving in
        String randMove = getDirection(player.getToken().getPosition(),
                path.remove(path.size() - 1));
        //System.out.println("Direction:" + randMove);
        pathLeft--;
        squaresMoved += 1;
        return randMove;
	}

	/**
	 * Strategy:
	 * 1. Just get unseen suspects
	 * 2. If unseen suspect = 1, then we bluff
	 */
	public String getSuspect() {
		
        ArrayList<String> unseen = getUnseenTokens();
        
        //Accusation
        if (accuse) {
        	System.out.println(privateSeen);
            System.out.println(guessGame);
            return getUnseenTokens().get(0);
        }

        //Bluffing
        if (!accuse && getUnseenTokens().size() == 1) {
            for (String s : Names.SUSPECT_NAMES) {
                if (player.hasCard(s)) {
                    return s;
                }
            }
        }

        return unseen.get(rand.nextInt(unseen.size()));
	}

	/**
	 * Strategy:
	 * 1. Just get unseen suspects
	 * 2. If unseen suspect = 1, then we bluff
	 */
	public String getWeapon() {

        ArrayList<String> unseen = getUnseenWeapons();

        //Accusation
        if (accuse) {
            return getUnseenWeapons().get(0);
        }

        //Bluffing
        if (!accuse && getUnseenWeapons().size() == 1) {
            for (String s : Names.WEAPON_NAMES) {
                if (player.hasCard(s)) {
                    return s;
                }
            }
        }
        
        return unseen.get(rand.nextInt(unseen.size()));
	}

	/**
	 * Strategy:
	 * 1. Only get unseen rooms 
	 */
	public String getRoom() {
        if (accuse) {
            return getUnseenRooms().get(0);
        }
        return getUnseenRooms().get(rand.nextInt(getUnseenRooms().size()));
	}
	
	/**
	 * Strategy:
	 * 1. Finds the room the player is in
	 * 2. Its available doors to exit from
	 * 3. The destination room it wants to go to & its doors
	 * 4. Calculates the best path from door to door
	 * 5. Best path is our exit
	 */
	public String getDoor() {
    	int door = 0;
    	ArrayList<Coordinates> doorPath = calculatePath(player.getToken().getRoom().getDoorCoordinates(0), map.getRoom(goToRoom).getDoorCoordinates(0));
    	ArrayList<Coordinates> tmp = new ArrayList<Coordinates>();
    	
    	//Finds best path between my current room doors and the next room doors
    	for(int i = 0; i < player.getToken().getRoom().getNumberOfDoors(); i++) {
    		for(int j = 0; j < map.getRoom(goToRoom).getNumberOfDoors(); j++) {
    			tmp = calculatePath(player.getToken().getRoom().getDoorCoordinates(i), map.getRoom(goToRoom).getDoorCoordinates(j));
    			
    			//If found better path
    			if(doorPath.size() > tmp.size()) {
        			doorPath = tmp;
        			door = i;
        		}
    		}
    	}

    	return Integer.toString(door + 1);	
	}

	/**
	 * Strategy:
	 * 1. Return not a room if possible
	 * Reason:
	 * 1. Player takes more time walking from room to room
	 */
	public String getCard(Cards matchingCards) {
        boolean cardFound = false;
        String bestChoice = matchingCards.get().toString();
        
        //Get rid of bot's own token card
        for(Card card : matchingCards){
            if(card.hasName(player.getToken().getName())){
                bestChoice = card.toString();
                cardFound = true;
            }
        }
        
        if(!cardFound){
          for (String weapon : Names.WEAPON_NAMES) {
               for (Card card : matchingCards) {
                    if (card.hasName(weapon)) {
                        bestChoice = card.toString();
                        cardFound = true;
                    }
               }
            }
        }if (!cardFound) {
            for (String suspect : Names.SUSPECT_NAMES) {
                for (Card card : matchingCards) {
                    if (card.hasName(suspect)) {
                        bestChoice = card.toString();
                        cardFound = true;
                    }
                }
            }
        }
        if (!cardFound){
            for (String room : Names.ROOM_NAMES) {
                for (Card card : matchingCards) {
                    if (card.hasName(room)) {
                        bestChoice = card.toString();
                    }
                }
            }
        }

        return bestChoice;
	}

	/**
	 * Strategy
	 * 1. If someone responds to a question
	 * 2. Update our guessing sheet
	 */
	public void notifyResponse(Log response) {
		
        String user = "";
        String cardShown = "";
        Boolean saw = false;
        
        for (String s : response) {
            if (s.contains("showed")) {
                saw = true;
                //System.out.println(s);
                user = s.split(" ", 2)[0];
                //System.out.println("User: " + user);
                cardShown = s.split(": ", 2)[1];
                cardShown = cardShown.substring(0, cardShown.length() - 1);
                //System.out.println("Card: " + cardShown);
            }
        }

        if (saw) {
            updateGuessMap(user, cardShown);
            reloadGuessMap();
        } else {
            String token = "";
            String weapon = "";
            String room = "";
            Boolean foundQ = false;
            for (String c : response) {
               // System.out.println(c);
                if (c.contains("questioned")) {
                    foundQ = true;
                    token = c.split("with", 2)[0].trim();
                    token = token.split("about", 2)[1].trim();
                    String rest = c.split("with the ", 2)[1];
                    room = rest.split("in the", 2)[1];
                    room = room.substring(1, room.length() - 1);
                    weapon = rest.split(" in", 2)[0];
                   // System.out.println(user + "XD" + token + "xd" + weapon + "XD" + room + "XD");
                }
            }
            if (foundQ) {
                if (!player.hasCard(token)) {
                    found[0] = token;
                }
                if (!player.hasCard(weapon)) {
                    found[1] = weapon;
                }
                if (!player.hasCard(room)) {
                    found[2] = room;
                }
            }

        }
	}

	public void notifyPlayerName(String playerName) {
        System.out.println("PLAYER NAME:" + playerName);
	}

	public void notifyTurnOver(String playerName, String position) {
		
		System.out.println(playerName + " " + position);
	}

	public void notifyQuery(String playerName, String query) {
        System.out.println(playerName + query);
	}

	public void notifyReply(String playerName, boolean cardShown) {

	}
	
	//Prediction sheet
	private void guessSheetSetUp() {
		for(String name: playersInfo.getPlayersNames()) {
			answerCounter.put(name, 0);
			
			//Don't record yourself
			if(name.equals(player.getName())) {
				continue;
			}
			
			HashMap<String, ArrayList<Integer>> cardMap = new HashMap<>();
			
			for(String token: Names.SUSPECT_NAMES) {
				cardMap.put(token, null);
			}
			for(String weapon: Names.WEAPON_NAMES) {
				cardMap.put(weapon, null);
			}
			for(String room: Names.ROOM_CARD_NAMES) {
				cardMap.put(room, null);
			}
			
			//System.out.println(cardMap); 	//(test) Displays deck of cards for other players
			guessGame.put(name, cardMap); 	//Each player has a deck with integer beside each card
		}
	}
	
	//When log is updated, update guessGame
	private void updateGuessSheet() {
		ArrayList<String> tmp = new ArrayList<>();
		int count = 0;
		
		//The amount of query in log
		for(String info: log) {
			tmp.add(info);
			count++;
		}
		
		//If there are any new logs
		while(tmp.size() != count - logSizeCounter) {
			tmp.remove(0);
		}
		
		analyseLog(tmp);
		logSizeCounter += count - logSizeCounter;
	}
	
	
	//Parsing the new log
	private void analyseLog(ArrayList<String> tmp) {
		for(int i = 0; i < tmp.size(); i++) {
			
			if(tmp.get(i).contains("questioned")) {
				int z = i + 1;
				
				//Answered
				if(tmp.get(z).contains("showed")) {
					String token = tmp.get(i).split("with", 2)[0].trim();
					token = token.split("about", 2)[1].trim();
					
					String rest = tmp.get(i).split("with the ", 2)[1];
					
					String room = rest.split("in the", 2)[1];
					room = room.substring(1, room.length() - 1);
					
					String weapon = rest.split(" in", 2)[0];
					String user = tmp.get(z).split(" ", 2)[0];
					
					//(test) to see if parsing is right
					//System.out.println("USER: " + user + " TOKEN: " + token + " WEAPON: " + weapon + " ROOM: " + room);
					learn(user, token, weapon, room);
				}else { //not answered
					String token = tmp.get(i).split("with", 2)[0].trim();
					token = token.split("about", 2)[1].trim();
					
					String rest = tmp.get(i).split("with the ", 2)[1];
					String room = rest.split("in the", 2)[1];
					room = room.substring(1, room.length() - 1);
					
					String weapon = rest.split(" in", 2)[0];
					String user = tmp.get(z).split(" ", 2)[0];
					removeGuess(user, token, weapon, room);
				}
			}
		}
	}
	
	//Remove guess from the guess sheet
    private void removeGuess(String user, String token, String weapon, String room) {
        if (!user.equals(player.getName())) {
            if (guessGame.get(user).get(token) != null) {
                if (guessGame.get(user).get(token).size() != 0) {
                    guessGame.get(user).put(token, new ArrayList<Integer>());
                }
            }

            if (guessGame.get(user).get(weapon) != null) {
                if (guessGame.get(user).get(weapon).size() != 0) {
                    guessGame.get(user).put(weapon, new ArrayList<Integer>());
                }
            }

            if (guessGame.get(user).get(room) != null) {
                if (guessGame.get(user).get(room).size() != 0) {
                    guessGame.get(user).put(room, new ArrayList<Integer>());
                }
            }
        }

        reloadGuessMap();
    }
	
	
	private void learn(String user, String token, String weapon, String room) {
		//Can't learn anything new if, the bot itself answered the question
		if(!user.equals(player.getName())) {
			int currentToken = answerCounter.get(user) + 1;
			int counter = 0;
			String singleValue = "";
					
			//Unseen cards, increment their counters for guessing
            if (!player.hasCard(token)) {
                if (guessGame.get(user).get(token) != null && guessGame.get(user).get(token).size()
                        == 0) {
                } else {
                    ArrayList<Integer> privateList = new ArrayList<>();
                    if (guessGame.get(user).get(token) != null) {
                        privateList = guessGame.get(user).get(token);
                    }
                    privateList.add(currentToken);

                    //System.out.println("Added " + token + privateList);
                    guessGame.get(user).put(token, privateList);
                    singleValue = token;
                    counter++;

                }
            }
            if (!player.hasCard(weapon)) {
                if (guessGame.get(user).get(weapon) != null && guessGame.get(user).get(
                        weapon).size()
                        == 0) {
                } else {
                    ArrayList<Integer> privateList = new ArrayList<>();
                    privateList.clear();
                    if (guessGame.get(user).get(weapon) != null) {
                        privateList = guessGame.get(user).get(weapon);
                    }
                    privateList.add(currentToken);
                   // System.out.println("Added " + weapon + privateList);
                    guessGame.get(user).put(weapon, privateList);
                    singleValue = weapon;
                    counter++;
                }
            }
            if (!player.hasCard(room)) {
                if (guessGame.get(user).get(room) != null && guessGame.get(user).get(room).size()
                        == 0) {
                } else {
                    ArrayList<Integer> privateList = new ArrayList<>();
                    privateList.clear();
                    if (guessGame.get(user).get(room) != null) {
                        privateList = guessGame.get(user).get(room);
                    }
                    privateList.add(currentToken);
                    guessGame.get(user).put(room, privateList);
                   // System.out.println("Added " + room + privateList);
                    singleValue = room;
                    counter++;
                }
            }
            
            answerCounter.put(user, currentToken);
                    
            if (counter == 1) {
                if (!privateSeen.contains(singleValue)) {
                    privateSeen.add(singleValue);
                    
                    //(test visuals)
                    /*System.out.println("SEEN:");
                    System.out.println(privateSeen);
                    for (String s : Names.ROOM_CARD_NAMES) {
                        if (player.hasSeen(s)) {
                            System.out.println(s);
                        }
                    }
                    for (String s : Names.SUSPECT_NAMES) {
                        if (player.hasSeen(s)) {
                            System.out.println(s);
                        }
                    }
                    for (String s : Names.WEAPON_NAMES) {
                        if (player.hasSeen(s)) {
                            System.out.println(s);
                        }
                    }
                    System.out.println("Player's cards:");
                    for (Card c : player.getCards()) {
                        System.out.print("[" + c.toString() + "]");
                    }
             		*/
                    reloadGuessMap();
                 
                }
            } else {
              //  System.out.println(counter);
            }
          //  System.out.println(answerCounter);
        }

       // System.out.println(guessGame);		
	}
	
	//Checks to see if card has been seen 
    private boolean hasSeen(String card) {
        if (player.hasSeen(card) || privateSeen.contains(card)) {
            return true;
        }
        return false;
    }
    
    //Unseen token cards
    private ArrayList<String> getUnseenTokens() {
        ArrayList<String> unseenTokens = new ArrayList<>();
        
        //Token accusation found
        if (found[0] != null) {
            unseenTokens.add(found[0]);
            return unseenTokens;
        }
        
        for (String token : Names.SUSPECT_NAMES) {
            if (!player.hasCard(token) && !player.hasSeen(token)) {
                unseenTokens.add(token);
            }
        }
        return unseenTokens;
    }

    //Unseen weapon cards
    private ArrayList<String> getUnseenWeapons() {
        ArrayList<String> unseenWeapons = new ArrayList<>();
        
        //Weapon accusation found
        if (found[1] != null) {
            unseenWeapons.add(found[1]);
            return unseenWeapons;
        }
        
        for (String weapon : Names.WEAPON_NAMES) {
            if (!player.hasCard(weapon) && !player.hasSeen(weapon)) {
                unseenWeapons.add(weapon);
            }
        }
        return unseenWeapons;
    }
    
    //Unseen room cards
    private ArrayList<String> getUnseenRooms() {
        ArrayList<String> unseenRooms = new ArrayList<>();
        
        //Accusation of room is found
        if(found[2] != null) {
        	unseenRooms.add(found[2]);
        	return unseenRooms;
        }
        
        for (String room : Names.ROOM_CARD_NAMES) {
            if (!player.hasCard(room) && !player.hasSeen(room)) {
                unseenRooms.add(room);
            }
        }
        return unseenRooms;
    }
    
    /**
     * Strategy:
     * 1. Acquires best path from player to room
     * 2. Take account into different doors, to find the best door to enter to
     * @return
     */
    private String getRoomCard() {        
        ArrayList<String> myRooms = new ArrayList<>();

        //Gets a list of room cards that the bot has
        for (Card card : player.getCards()) {
            for (String room : Names.ROOM_CARD_NAMES) {
                if (card.toString().equals(room)) {
                    myRooms.add(room);
                }
            }
        }

        if (!myRooms.isEmpty()) {

            return getClosestRoom(myRooms);

        } else if (!getUnseenRooms().isEmpty()) {
            ArrayList<Room> unseenRooms = new ArrayList<Room>();
            ArrayList<String> rooms = getUnseenRooms();

            //Adds to arrayList
            for (int i = 0; i < rooms.size(); i++) {
                unseenRooms.add(map.getRoom(rooms.get(i)));
            }

            if (!player.getToken().isInRoom()) {
                int room = 0;
                ArrayList<Coordinates> doorPath = calculatePath(
                        player.getToken().getPosition(), unseenRooms.get(0).getDoorCoordinates(0));

                ArrayList<Coordinates> tmp = new ArrayList<Coordinates>();

                //Rooms we have
                for (int i = 0; i < unseenRooms.size(); i++) {
                    //The doors of that room
                    for (int j = 0; j < unseenRooms.get(i).getNumberOfDoors(); j++) {
                        tmp = calculatePath(player.getToken().getPosition(),
                                unseenRooms.get(i).getDoorCoordinates(j));

                        if (doorPath.size() > tmp.size()) {
                            doorPath = tmp;
                            room = i;
                        }
                    }
                }

                return unseenRooms.get(room).toString();
            } else {
                int room = 0;
                ArrayList<Coordinates> doorPath = calculatePath(
                        player.getToken().getRoom().getDoorCoordinates(0),
                        unseenRooms.get(0).getDoorCoordinates(0));
                ArrayList<Coordinates> tmp = new ArrayList<Coordinates>();

                //Token position - doors
                for (int i = 0; i < player.getToken().getRoom().getNumberOfDoors(); i++) {
                    //Rooms we have
                    for (int j = 0; j < unseenRooms.size(); j++) {
                        //The doors of that room
                        for (int k = 0; k < unseenRooms.get(j).getNumberOfDoors(); k++) {
                            tmp = calculatePath(player.getToken().getRoom().getDoorCoordinates(i),
                                    unseenRooms.get(j).getDoorCoordinates(k));

                            if (doorPath.size() > tmp.size()) {
                                doorPath = tmp;
                                room = j;
                            }
                        }
                    }
                }
                return unseenRooms.get(room).toString();
            }
        }
        
        return Names.ROOM_CARD_NAMES[rand.nextInt(Names.ROOM_CARD_NAMES.length)];
    }
    
  
    /**
     * Strategy:
     * 1. Goes around to find the right unseen room
     * @return
     */
    private String getRandomRoomCard() {

        if (!hasSeen(player.getToken().getRoom().toString()) && !player.hasCard(
                player.getToken().getRoom().toString())) {
            return player.getToken().getRoom().toString();
        }
        return getClosestRoom(getUnseenRooms());
    }
    
    //Acquires shortest distance between player and room
    private String getClosestRoom(ArrayList<String> rooms) {
    	
    	//Acquires shortest distance from room A to room B
        if (player.getToken().isInRoom()) {
            if (rooms.contains(player.getToken().getRoom().toString())) {
                return player.getToken().getRoom().toString();
            }else{
                goToRoom = rooms.get(0);
                ArrayList<Coordinates> bestDoor = calculatePath(player.getToken().getRoom().getDoorCoordinates(Integer.parseInt(getDoor())-1),
                        map.getRoom(rooms.get(0)).getDoorCoordinates(0));
                int bestRoom = 0;
                for (int i = 1; i < rooms.size(); i++) {
                    goToRoom = rooms.get(i);
                    ArrayList<Coordinates> tmp = calculatePath(player.getToken().getRoom().getDoorCoordinates(Integer.parseInt(getDoor())-1),
                            map.getRoom(rooms.get(i)).getDoorCoordinates(0));
                    if (tmp.size() < bestDoor.size()) {
                        bestDoor = tmp;
                        bestRoom = i;
                    }
                }
                return rooms.get(bestRoom);
            }
        }else {

            ArrayList<Coordinates> bestDoor = calculatePath(player.getToken().getPosition(),
                    map.getRoom(rooms.get(0)).getDoorCoordinates(0));
            int bestRoom = 0;
            for (int i = 1; i < rooms.size(); i++) {
                ArrayList<Coordinates> tmp = calculatePath(player.getToken().getPosition(),
                        map.getRoom(rooms.get(i)).getDoorCoordinates(0));
                if (tmp.size() < bestDoor.size()) {
                    bestDoor = tmp;
                    bestRoom = i;
                }
            }
            return rooms.get(bestRoom);
        }
    }
    
    //A* calculating path
    private ArrayList<Coordinates> calculatePath(Coordinates s, Coordinates e) {
        BZAstar pathFinder = new BZAstar(24, 25);
        ArrayList<Coordinates> path = pathFinder.calculateAStarNoTerrain(s, e);
        return path;
    }

    private String getDirection(Coordinates start, Coordinates end) {

        System.out.println("Moving from: " + start + " to: " + end);
        if (start.getRow() < end.getRow()) {
            return "d";
        } else if (start.getRow() > end.getRow()) {
            return "u";
        } else if (start.getCol() > end.getCol()) {
            return "l";
        } else if (start.getCol() < end.getCol()) {
            return "r";
        }

        return null;
    }
    
    //Update guess sheet
    private void updateGuessMap(String user, String card) {
        for (String u : playersInfo.getPlayersNames()) {
            if (u.equals(user) || u.equals(player.getName())) {
                continue;
            } else if (guessGame.get(u).get(card) != null) {
                guessGame.get(u).put(card, new ArrayList<Integer>());
               // System.out.println("Updated value of card " + card + " on user " + u);
              //  System.out.println(guessGame);
            }
        }
    }

    //Reload guess sheet with new data 
    private void reloadGuessMap() {

        for (String c : playersInfo.getPlayersNames()) {
           // System.out.println(c + " IS MY NAME !");
            int currentCount = answerCounter.get(c);
            
            if (c != player.getName()) {
                for (int i = 1; i <= currentCount; i++) {
                    Iterator<java.util.Map.Entry<String, ArrayList<Integer>>> it = guessGame.get(
                            c).entrySet().iterator();
                    int d = 0;
                    String found = "";
                    while (it.hasNext()) {
                        java.util.Map.Entry<String, ArrayList<Integer>> pair = (java.util.Map.Entry) it.next();
                        if (pair.getValue() != null && pair.getValue().contains(i)) {
                            d++;
                            found = pair.getKey();
                        }
                    }

                    if (d == 1) {
                        if (!privateSeen.contains(found)) {
                            //JOptionPane.showMessageDialog(null, "We've found" + found);
                            privateSeen.add(found);
                        }
                    }
                }

            }
        }
    }
 

  
    
    //////////////////////////////////////////////////////////////////////////////
    //																			//
    //					A* PATH FINDING											//		
    //																			//
    //////////////////////////////////////////////////////////////////////////////
    class BZAstar {

        private final int width;
        private final int height;

        private final HashMap<String, AStarNode> nodes = new HashMap<>();

        @SuppressWarnings("rawtypes")
        private final Comparator fComparator = new Comparator<AStarNode>() {
            public int compare(AStarNode a, AStarNode b) {
                return Integer.compare(a.getFValue(), b.getFValue()); //ascending to get the lowest
            }
        };

        public BZAstar(int width, int height) {
            this.width = width;
            this.height = height;

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    String pointS = "(" + x + "," + y + ")";
                    Coordinates point = new Coordinates(x, y);
                    this.nodes.put(pointS, new AStarNode(point));
                }
            }


        }

        @SuppressWarnings("unchecked")
        public ArrayList<Coordinates> calculateAStarNoTerrain(Coordinates p1, Coordinates p2) {

            List<AStarNode> openList = new ArrayList<AStarNode>();
            List<AStarNode> closedList = new ArrayList<AStarNode>();

            AStarNode currentNode, destNode;
            destNode = nodes.get("(" + p2.getCol() + "," + p2.getRow() + ")");
            currentNode = nodes.get("(" + p1.getCol() + "," + p1.getRow() + ")");
            currentNode.parent = null;
            currentNode.setGValue(0);
            openList.add(currentNode);


            while (!openList.isEmpty()) {

                Collections.sort(openList, this.fComparator);
                currentNode = openList.get(0);
                if (currentNode.point.equals(destNode.point)) {
                    return this.calculatePath(destNode);
                }

                if (!map.isCorridor(currentNode.point) && map.getRoom(
                        currentNode.point).toString().equals(
                        map.getRoom(destNode.point).toString())) {
                    return this.calculatePath(currentNode);
                }

                openList.remove(currentNode);
                closedList.add(currentNode);

                for (String direction : mapDirections) {
                    Coordinates adjPoint = map.getNewPosition(currentNode.point, direction);
                    if (!this.isInsideBounds(adjPoint)) {
                        continue;
                    }
                    AStarNode adjNode = nodes.get(
                            "(" + adjPoint.getCol() + "," + adjPoint.getRow() + ")");
                    if (!map.isValidMove(currentNode.point, direction)) {
                        continue;
                    }

                    if (!closedList.contains(adjNode)) {
                        if (!openList.contains(adjNode)) {
                            adjNode.parent = currentNode;
                            adjNode.calculateGValue(currentNode);
                            adjNode.calculateHValue(destNode);
                            openList.add(adjNode);
                        } else {
                            if (adjNode.gValue < currentNode.gValue) {
                                adjNode.calculateGValue(currentNode);
                                currentNode = adjNode;
                            }
                        }
                    }
                }
            }

            return null;
        }

        private ArrayList<Coordinates> calculatePath(AStarNode destinationNode) {
            ArrayList<Coordinates> path = new ArrayList<Coordinates>();
            AStarNode node = destinationNode;
            while (node.parent != null) {
                path.add(node.point);
                node = node.parent;
            }
            return path;
        }

        private boolean isInsideBounds(Coordinates point) {
            return point.getCol() >= 0 &&
                    point.getCol() < this.width &&
                    point.getRow() >= 0 &&
                    point.getRow() < this.height;
        }


    }


    class AStarNode {

        public final Coordinates point;

        public AStarNode parent;

        public int gValue; //points from start
        public int hValue; //distance from target

        private final int MOVEMENT_COST = 10;

        public AStarNode(Coordinates point) {
            this.point = point;
        }

        /**
         * Used for setting the starting node value to 0
         */
        public void setGValue(int amount) {
            this.gValue = amount;
        }

        public void calculateHValue(AStarNode destPoint) {
            this.hValue = (Math.abs(point.getCol() - destPoint.point.getCol()) + Math.abs(
                    point.getRow() - destPoint.point.getRow())) * this.MOVEMENT_COST;
        }

        public void calculateGValue(AStarNode point) {
            this.gValue = point.gValue + this.MOVEMENT_COST;
        }

        public int getFValue() {
            return this.gValue + this.hValue;
        }
    }

	
}
