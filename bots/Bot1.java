package bots;

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
    
    private Random rand = new Random();
    
    private boolean hasAccused = false;
    private boolean accuse = false;
    private boolean hasRolled = false;
    private int squaresMoved = 0;
    private int switchX = 1;
    private int switchWE = 1;
    private int pathLeft;

    private String mapDirections[] = {"u", "d", "l", "r"};
    private ArrayList<Coordinates> path;
    private String goToRoom = null;
    
    //Log things
    private int logSizeCounter = 0;
    private HashMap<String, HashMap<String, Integer>> guessGame = new HashMap<>();
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

    public String getName() {
        return "MAGA"; // must match the class name
    }

    public String getVersion() {
        return "0.1";   // change on a new release
    }

    public String getCommand() {
    	
    	//Setting up predictions log
        if (guessGame.isEmpty()) {
            for (String s : playersInfo.getPlayersNames()) {
                answerCounter.put(s, 0);
                if (s.equals(player.getName())) {
                    continue;
                }
                HashMap<String, Integer> cardMap = new HashMap<>();
                for (String y : Names.SUSPECT_NAMES) {
                    cardMap.put(y, 0);
                }
                for (String y : Names.WEAPON_NAMES) {
                    cardMap.put(y, 0);
                }
                for (String y : Names.ROOM_CARD_NAMES) {
                    cardMap.put(y, 0);
                }
                System.out.println(cardMap);
                guessGame.put(s, cardMap);
            }
        }
        
        //Taking information from log & review your predictions
        if (!log.isEmpty()) {
            int count = 0;
            ArrayList<String> tmp = new ArrayList<>();
            for (String s : log) {
                tmp.add(s);
                count++;

            }

            while (tmp.size() != count - logSizeCounter) {
                System.out.println(tmp.size() + " " + (count - logSizeCounter));
                tmp.remove(0);
            }

            analyseLog(tmp);

            logSizeCounter += count - logSizeCounter;

        }

    	//When a player is in a room, reset 
        if (player.getToken().isInRoom()) {
            pathLeft = 0;
            goToRoom = null;
        }

        //If no room to go to and depending on the size of unseen cards
        if (goToRoom == null) {
            if (getUnseenWeapons().size() > 1 && getUnseenTokens().size() > 1) {
                goToRoom = getRoomCard();
            } else if (accuse) {
                goToRoom = "Cellar";
            } else {
                goToRoom = getRandomRoomCard();
            }
        }

        //Only take passage if it is a seen card, otherwise no point leaving
        if (player.getToken().isInRoom() && squaresMoved == 0) {
            if (player.getToken().getRoom().hasPassage()) {
                if (player.hasSeen(player.getToken().getRoom().toString())) {
                	hasRolled = true;
                	return "passage";
                }
            }
        }

        //If unseen cards are 1 for each category then accuse, else do nothing
        if (getUnseenRooms().size() == 1 && getUnseenTokens().size() == 1 && getUnseenWeapons().size() == 1) {
            System.out.println("TIME TO ACCUSE");
            System.out.println(getUnseenRooms().get(0));
            System.out.println(getUnseenTokens().get(0));
            System.out.println(getUnseenWeapons().get(0));
            accuse = true;
        } else {
        	System.out.println("===================  Remaining: BOT1 ==================="
            + "\nRemaining Cards [T,W,R]: " + getUnseenTokens().size() + "," + getUnseenWeapons().size() + ","
                            + getUnseenRooms().size());
            System.out.println(privateSeen);
        }

        //Has the player rolled their dice for the start of the round
        if (!hasRolled) {
            //resets (start of turn)
            hasAccused = false;
            hasRolled = true;
            squaresMoved = 0;
            return "roll";
        } else if (!map.isCorridor(player.getToken().getPosition())
                && player.getToken().getRoom().accusationAllowed()) {
            return "accuse";
        } else if (!map.isCorridor(player.getToken().getPosition()) && squaresMoved > 0) {
            if (!hasAccused) {
                System.out.println("I'm in a room can accuse");
                // accuse
                hasAccused = true;
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

    public String getMove() {

        Coordinates playerPosition = player.getToken().getPosition();
        
        if (pathLeft == 0) {
            System.out.println(player.getName() + "is moving towards room: " + goToRoom);
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

            System.out.println("Path: " + path);
        }
        String randMove = getDirection(player.getToken().getPosition(),
                path.remove(path.size() - 1));
        System.out.println("Direction:" + randMove);
        pathLeft--;
        squaresMoved += 1;
        return randMove;
    }

    public String getSuspect() {
	        String suspect = Names.SUSPECT_NAMES[0]; //Default

        //Accusation 
        if (accuse) {
            return getUnseenTokens().get(0);
        }

      //Ask random cards as long as its not seen
    	do {
    		do {
	    		suspect = Names.SUSPECT_NAMES[rand.nextInt(Names.SUSPECT_NAMES.length)];
	    
	    		if(switchX == 0 && !player.hasSeen(suspect) && !player.hasCard(suspect)) {
	    			switchX = 1;
	    		}
    		}while(switchX == 0);
    	}while(player.hasSeen(suspect));

        //If you bluffed last turn, you can't bluff again
        if (player.hasCard(suspect)) {
            System.out.println("Just bluffed haha [Tokens]");
            switchX = 0;
        } else {
            switchX = 1;
        }

        return getUnseenTokens().get(rand.nextInt(getUnseenTokens().size()));
    }

    public String getWeapon() {
        String weapon = Names.WEAPON_NAMES[0]; //Default

        //Accusation 
        if (accuse) {
            return getUnseenWeapons().get(0);
        }
/*
      //Ask random cards as long as its not seen
    	do {
    		do {
    			weapon = Names.WEAPON_NAMES[rand.nextInt(Names.WEAPON_NAMES.length)];
	    
	    		if(switchWE == 0 && !player.hasSeen(weapon) && !player.hasCard(weapon)) {
	    			switchWE = 1;
	    		}
    		}while(switchWE == 0);
    	}while(player.hasSeen(weapon));

        //If you bluffed last turn, you can't bluff again
        if (player.hasCard(weapon)) {
            System.out.println("Just bluffed haha [Weapons]");
            switchWE = 0;
        } else {
        	switchWE = 1;
        }
	*/
        return getUnseenWeapons().get(rand.nextInt(getUnseenWeapons().size()));
    }

    //only used in accusation
    public String getRoom() {
        if (accuse) {
            return getUnseenRooms().get(0);
        }
        return getUnseenRooms().get(rand.nextInt(getUnseenRooms().size()));
    }

    public String getDoor() {
    	int i = 0;
    	
    	ArrayList<Coordinates> doorPath = calculatePath(player.getToken().getRoom().getDoorCoordinates(i), map.getRoom(goToRoom).getDoorCoordinates(i));
    	ArrayList<Coordinates> tmp = new ArrayList<Coordinates>();
    	
    	//Finds best path between my current room doors and the next room doors
    	for(; i < player.getToken().getRoom().getNumberOfDoors(); i++) {
    		
    		for(int j = 0; j < map.getRoom(goToRoom).getNumberOfDoors(); j++) {
    			tmp = calculatePath(player.getToken().getRoom().getDoorCoordinates(i), map.getRoom(goToRoom).getDoorCoordinates(j));
    			
    			if(doorPath.size() > tmp.size()) {
        			doorPath = tmp;

        		}
    		}
    	}
    	return Integer.toString(i);	
    }

    public String getCard(Cards matchingCards) {

        // Basic strategy for getCard. Returns room if possible since they are harder to access.
        // Then returns suspect, then weapon rather arbitrarily.
        boolean cardFound = false;
        String bestChoice = matchingCards.get().toString();
        for (String room : Names.ROOM_NAMES) {
            for (Card card : matchingCards) {
                if (card.hasName(room)) {
                    bestChoice = card.toString();
                    cardFound = true;
                }
            }
        }
        if (!cardFound) {
            for (String suspect : Names.SUSPECT_NAMES) {
                for (Card card : matchingCards) {
                    if (card.hasName(suspect)) {
                        bestChoice = card.toString();
                        cardFound = true;
                    }
                }
            }
        }
        if (!cardFound) {
            for (String weapon : Names.WEAPON_NAMES) {
                for (Card card : matchingCards) {
                    if (card.hasName(weapon)) {
                        bestChoice = card.toString();
                    }
                }
            }
        }
        return bestChoice;
    }
    
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
    private String getRoomCard() {
        for (Card card : player.getCards()) {
            for (String room : Names.ROOM_CARD_NAMES) {
                System.out.println("Comparing:" + card + room);
                if (card.toString().equals(room)) {
                    return card.toString();
                }
            }
        }

        return Names.ROOM_CARD_NAMES[rand.nextInt(Names.ROOM_CARD_NAMES.length)];
    }

    //Obtains a random card
    private String getRandomRoomCard() {
        ArrayList<String> rooms = new ArrayList<>();
        for (String room : Names.ROOM_CARD_NAMES) {
            if (getUnseenRooms().contains(room)) {
                rooms.add(room);
            }
        }
        return rooms.get(rand.nextInt(rooms.size()));
    }
    
    
    private void analyseLog(ArrayList<String> logx) {

        for (int i = 0; i < logx.size(); i++) {
            System.out.println(logx.get(i));
            if (logx.get(i).contains("questioned")) {
                int z = i + 1;
                System.out.println(logx.get(z));
                if (logx.get(z).contains("showed")) {
                    String token = logx.get(i).split("with", 2)[0].trim();
                    token = token.split("about", 2)[1].trim();
                    String rest = logx.get(i).split("with the ", 2)[1];
                    String room = rest.split("in the", 2)[1];
                    room = room.substring(1, room.length() - 1);
                    String weapon = rest.split(" in", 2)[0];
                    String user = logx.get(z).split(" ", 2)[0];
                    System.out.println(user + "XD" + token + "xd" + weapon + "XD" + room + "XD");
                    learn(user, token, weapon, room);
                }
            }
        }
    }
    
    private void learn(String user, String token, String weapon, String room) {

        if (!user.equals(player.getName())) {
            int currentToken = answerCounter.get(user) + 1;
            int counter = 0;
            String singleValue = "";

            if (guessGame.get(user).get(token) != 0) {
                currentToken = guessGame.get(user).get(token);
            }
            if (guessGame.get(user).get(weapon) != 0) {
                currentToken = guessGame.get(user).get(weapon);
            }
            if (guessGame.get(user).get(room) != 0) {
                currentToken = guessGame.get(user).get(room);
            }

            if (!player.hasCard(token)) {
                guessGame.get(user).put(token, currentToken);
                singleValue = token;
                counter++;
            }

            if (!player.hasCard(weapon)) {
                guessGame.get(user).put(weapon, currentToken);
                singleValue = weapon;
                counter++;
            }

            if (!player.hasCard(room)) {
                guessGame.get(user).put(room, currentToken);
                singleValue = room;
                counter++;
            }

            answerCounter.put(user, currentToken + 1);


            for (Card c : player.getCards()) {
                if (!hasSeen(c.toString())) {
                    System.out.println("Player has :" + c.toString());
                }
            }

            if (counter == 1) {
                if (!privateSeen.contains(singleValue)) {
                    privateSeen.add(singleValue);
                    System.out.println("EHM?\n\n\n\\n\\n\n\n????");
                }
            } else {
                System.out.println(counter);
            }
            System.out.println(guessGame);
            System.out.println(answerCounter);
        }

    }
    
    private boolean hasSeen(String card) {
        if (player.hasSeen(card) || privateSeen.contains(card)) {
            return true;
        }
        return false;
    }

    public void notifyResponse(Log response) {
        String user = "";
        String cardShown = "";
        Boolean saw = false;
        for (String s : response) {
            if (s.contains("showed")) {
                saw = true;
                System.out.println(s);
                user = s.split(" ", 2)[0];
                System.out.println("User: " + user);
                cardShown = s.split(": ", 2)[1];
                cardShown = cardShown.substring(0, cardShown.length() - 1);
                System.out.println("Card: " + cardShown);
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
                System.out.println(c);
                if (c.contains("questioned")) {
                    foundQ = true;
                    token = c.split("with", 2)[0].trim();
                    token = token.split("about", 2)[1].trim();
                    String rest = c.split("with the ", 2)[1];
                    room = rest.split("in the", 2)[1];
                    room = room.substring(1, room.length() - 1);
                    weapon = rest.split(" in", 2)[0];
                    System.out.println(user + "XD" + token + "xd" + weapon + "XD" + room + "XD");
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
        // Add your code here
        System.out.println(playerName + " " + position);
    }

    public void notifyQuery(String playerName, String query) {
        // Add your code here
    	System.out.println("QUERY: " + query);
        System.out.println(playerName + query);
    }

    public void notifyReply(String playerName, boolean cardShown) {
        // Add your code here
    }
    
    public void updateGuessMap(String user, String card) {
        for (String u : playersInfo.getPlayersNames()) {
            if (u.equals(user) || u.equals(player.getName())) {
                continue;
            } else if (guessGame.get(u).get(card) != 0) {
                guessGame.get(u).put(card, 0);
                System.out.println("Updated value of card " + card + " on user " + u);
                System.out.println(guessGame);
            }
        }
    }

    public void reloadGuessMap() {
        String current = "";
        for (String c : playersInfo.getPlayersNames()) {
            int currentCount = answerCounter.get(c);
            for (int i = 1; i <= currentCount; i++) {
                Iterator it = guessGame.get(c).entrySet().iterator();
                int d = 0;
                while (it.hasNext()) {
                    java.util.Map.Entry pair = (java.util.Map.Entry) it.next();
                    System.out.println(pair.getKey() + " = " + pair.getValue());
                    if (pair.getValue().equals(i)) {
                        d++;
                        current = pair.getKey().toString();
                    }
                }
                if (d == 1) {
                    if (!privateSeen.contains(current)) {
                        privateSeen.add(current);
                        JOptionPane.showMessageDialog(null, "n1" + current);
                    }
                }
            }
        }
    }
    
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

