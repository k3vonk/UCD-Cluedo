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

    JTextArea infArea = new JTextArea("", 34, 26);

    public InformationPanel() {
        setPreferredSize(new Dimension(300, 700));
        setBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                        "Information Panel"));

        infArea.setLineWrap(true);
        infArea.setEditable(false);
        infArea.setMaximumSize(infArea.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(infArea);
        add(scrollPane);
        add(new JLabel("Your Hand:"));

        try {
            add(CluedoUI.imageToLabel("Images/pplumm.png"));
            add(CluedoUI.imageToLabel("Images/Dagger.png"));
            add(CluedoUI.imageToLabel("Images/ballroom.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unable to locate file(s)");
        }


    }

    public void updateContent(Boolean replace, String value) {
        if (replace) {
            infArea.setText(value);
        } else {
            infArea.append("\n"  + value);
        }
    }


}
