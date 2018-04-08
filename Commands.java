/**
 * Contains commands that can be used
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 *
 */
public class Commands {

	//The commands available to the player change according to the state of the game
	public static String[] firstCommands = {"roll", "notes", "log", "cheat", "help"};
	public static String[] endCommands = {"done", "notes", "log", "cheat", "help"};
	
	public static String[] questionCommands = {"question", "notes", "log","done", "cheat", "help"};
	public static String[] accuseCommands ={"accuse", "notes", "log", "cheat", "help"};

	public static String[] movementCommands = {"u - up", "d - down", "l - left", "r - right"};
}
