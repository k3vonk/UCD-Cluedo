/**
 * Card class will represent a single card by its name and type
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class Card {

    // To store name and type
    private String name;
    private int type;

    // Intialize the card
    public Card(String name, int type) {
        this.name = name;
        this.type = type;
    }

    // Get the card's name
    public String getName() {
        return name;
    }

    // Get Type
    public int getType() {
        return type;
    }

    // In order to print out the card while testing.
    public String toString() {
        return this.name;
    }
}
