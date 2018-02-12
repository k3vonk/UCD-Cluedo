/**
 * The main class to execute other classes for the cluedo game
 * 
 * @author Gajun, Richard, Royal
 *
 */
public class Main {
    public static void main(String[] args) {
        BoardPanel board = new BoardPanel(); //NEW! This allows us to use board panel methods through the
        
        // CluedoUI class
        new CluedoUI(board);
    }
}
