/**
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class Main {
    public static void main(String[] args) {
        BoardPanel board =
                new BoardPanel(); //NEW! This allows us to use board panel methods through the

        // CluedoUI class
        new CluedoUI(board);
    }
}
