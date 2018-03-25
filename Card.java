public class Card {
    private String name;
    private int type;

    public Card(String name, int type){
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public int getType(){
        return type;
    }

    public String toString(){
        return this.name;
    }
}
