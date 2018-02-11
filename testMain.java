
import javax.swing.*;

public class testMain extends JFrame {
    public static void main(String[] args) {
        BoardPanel test =
                new BoardPanel(); //NEW! This allows us to use Boardpanel methods through the
        // CluedoUI class
        CluedoUI ui = new CluedoUI(test);
    }
}
