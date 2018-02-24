/**
 * A class which simulates a dice roll
 */

import java.util.Random;

public class Dice {
    private Random rand = new Random();
    private int numSides = 6;
    private int roll;

   //Method to roll the dice
   public void rollDice(){
        roll = rand.nextInt(numSides) + 1;
    }
    //Method to display the roll result
    public int getRoll(){
       return roll;
    }
}
