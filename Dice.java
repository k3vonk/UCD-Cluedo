/**
 * A class that simulates a dice
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Dice extends JPanel{
    private Random rand = new Random();
    private int numSides = 6;
    private int roll;

    public Dice(){}

    //Method to roll the dice
    public void rollDice() {
        roll = rand.nextInt(numSides) + 1;
    }

    //Method to display the roll result
    public int getRoll() {
        return roll;
    }
}
