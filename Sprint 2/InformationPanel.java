
import java.awt.Dimension;
import java.io.IOException;


import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * An information panel that displays information to users
 *
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko - 16353416
 */
public class InformationPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int HEIGHT = 33;
    private static final int WIDTH = 24;

    private JTextArea infoArea = new JTextArea("", HEIGHT, WIDTH);

    //Constructor
    public InformationPanel() {
        JScrollPane scroll = new JScrollPane(infoArea);
        setPreferredSize(new Dimension(300,690));

        //border style
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Information Panel"));

        //Ensures text area doesn't expand to the right, but pushes text downwards
        infoArea.setLineWrap(true);
        infoArea.setEditable(false); //Non editable, so players can't text here
        infoArea.setMaximumSize(infoArea.getPreferredSize());
        add(scroll);
        add(new JLabel("Your Hand:"));

        try {
            add(CluedoUI.imageToLabel("pplumm.png"));
            add(CluedoUI.imageToLabel("dagger.png"));
            add(CluedoUI.imageToLabel("ballroom.png"));
        } catch (IOException ex) {
          System.out.println("Unable to locate files for player's hand.");
        }



    }

    /**
     * A method that updates the content on the information panel that is displayed to everyone
     *
     * @param value - The string of text to be displayed
     */
    public void updateContent(String value) {
        // Add string below current value.
        infoArea.append(value + "\n");
        
        // Auto scroll down to current position.
        infoArea.setCaretPosition(infoArea.getDocument().getLength());
    }


}
