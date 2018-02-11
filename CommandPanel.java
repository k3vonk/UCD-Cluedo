import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CommandPanel extends JPanel {

    public JButton submit = new JButton("Submit");
    private JTextField inputText = new JTextField(".start", 30);

    public CommandPanel() {
        // Beautification.
        setPreferredSize(new Dimension(1000, 120));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(0, 10, 0, 0));

        // Hardcoded current player's image.
        // Change this later to fit the current player.
        try {
            JLabel picLabel = CluedoUI.imageToLabel("Images/mrsPlumPlayer.png");
            add(picLabel);
            picLabel.setBorder(BorderFactory.createTitledBorder("Current Player"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Unable to locate file(s)");
        }

        // A container that holds the textbox and the submit button.
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(50, 0, 0, 0));
        inputPanel.add(new JLabel("To test player and weapon movement: up/down/left/right"));
        inputPanel.add(inputText);
        inputPanel.add(submit);

        // Add a panel to make a list of labels and set its layout to allow for that.
        JPanel inputsAvailablePanel = new JPanel();
        inputsAvailablePanel.setLayout(new BoxLayout(inputsAvailablePanel, BoxLayout.Y_AXIS));
        inputsAvailablePanel.setPreferredSize(new Dimension(353, 0));
        inputsAvailablePanel.setBorder(BorderFactory.createTitledBorder("Available Inputs"));

        // Add the following as available inputs, although hardcoded now, it's made so that
        // it's going to be easier to implement a set of inputs into the panel later when we do
        // have them.
        String[] listInputs = {"up", "down", "left", "right", ".hello"};
        for (String x : listInputs) {
            inputsAvailablePanel.add(new JLabel(x));
        }

        //Finishing touches by adding it all to panel.
        add(inputPanel);
        add(inputsAvailablePanel);
    }

    public String getInput() {
        return inputText.getText();
    }


}
