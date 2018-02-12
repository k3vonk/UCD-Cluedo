/**
 * A class panel that displays the echoed messages to user
 *
 * @author Royal, Gajun
 */

import javax.swing.*;

import java.awt.*;
import java.io.IOException;

@SuppressWarnings("serial")
public class InformationPanel extends JPanel {
    /*
     * Setup a textarea to show information within the information panel
     * Set the rows and columns to specific values so that it looks good
     */
    JTextArea infArea = new JTextArea("", 34, 26);


    public InformationPanel() {
        setPreferredSize(new Dimension(300, 700));
        // Informative and looks better with the border
        setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                        "Information Panel"));
        /*
        * Ensure that the textarea does not expand to the right and instead pushes text to the next
        * line when the end is reached.
        */
        infArea.setLineWrap(true);
        infArea.setEditable(false); // Make sure players do not mess with it.
        infArea.setMaximumSize(infArea.getPreferredSize()); // So that it doesn't expand downwards
        JScrollPane scrollPane = new JScrollPane(infArea); // In the casse that we need more space
        add(scrollPane);
        add(new JLabel("Your Hand:"));

        /*
        * Load images to show player's hand, currently it is hardcoded but it is the base for
        * future assignments.
        */
        try {
            add(CluedoUI.imageToLabel("pplumm.png"));
            add(CluedoUI.imageToLabel("dagger.png"));
            add(CluedoUI.imageToLabel("ballroom.png"));

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unable to locate file(s)");
        }
    }

    /**
     * Method to replace the text and or insert a new line of text into the information panel
     * if replace is set to true, it replaces the entire textarea else it appends at the end.
     */
    public void updateContent(Boolean replace, String value) {
        if (replace) {
            infArea.setText(value);
        } else {
            infArea.append("\n" + value);
        }
    }
}
