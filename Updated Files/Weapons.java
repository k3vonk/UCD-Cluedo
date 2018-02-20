import java.util.Iterator;
import java.util.ArrayList;

/**
 * A weapons class which initializes all the possible types of weapons. It also allows for weapons to be iterable and searched 
 * through their names.
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class Weapons implements Iterable<Weapon>, Iterator<Weapon>{

    private final ArrayList<Weapon> weapons = new ArrayList<>();
    private Iterator<Weapon> iterator;
    private TileGrid grid = new TileGrid();
    
    public Weapons() {
       createWeapons();
    }
    
    //Creates a bunch of weapons for the game
    public void createWeapons() {
        weapons.add(new Weapon("Candle Stick", grid.map[21][1])); 
        weapons.add(new Weapon("Dagger", grid.map[2][21])); 
        weapons.add(new Weapon("Lead Pipe", grid.map[1][12])); 
        weapons.add(new Weapon("Revolver", grid.map[22][22])); 
        weapons.add(new Weapon("Rope", grid.map[19][16])); 
        weapons.add(new Weapon("Spanner", grid.map[2][2])); 
    }
    
    //Iterates to get a specific weapon
    public Weapon get(String name) {
        for (Weapon weapon : weapons) {
            if (weapon.hasName(name)) {
                return weapon;
            }
        }
        return null;
    }
    
	@Override
	public Iterator<Weapon> iterator() {
		iterator = weapons.iterator();
		return iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Weapon next() {
		 return iterator.next();
	}

}
