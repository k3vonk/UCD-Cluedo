import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class CluedoUI extends JFrame {


    JPanel mainPanel = new JPanel();
    private BoardPanel test; //NEW! I instatiate the Boardpanel object OUTSIDE Drawmainpanel so it can be used by other methods

    //NEW!
    public CluedoUI(BoardPanel test) {

        JFrame frame = new JFrame();
        this.test = test; // NEW! I need an instance of Boardpanel as part of the constructor.
        DrawMainPanel();

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setSize(1000, 1000);
        frame.setTitle("Cluedo");
        frame.setVisible(true);
        frame.setResizable(false);

    }

    //Methods to move the characters 1 space.
    //For now these move both players and weapons. The input is the name of what you want to move and direction to move.
    public boolean Movement(String direction, String name) {
        return test.Movement(direction, name);
    }
    //Method to set the weapons somewhere. Unused for assignment 1.
    //  public void setWeaponTile(String name, int newRow, int newColumn){test.setWeaponTile(name,newRow,newColumn);}

    public void DrawMainPanel() {
        JPanel bottomContainerPanel = new JPanel();
        JPanel containerPanel = new JPanel();
        InformationPanel InformationPanel = new InformationPanel();

        containerPanel.setPreferredSize(new Dimension(1000, 700));
        InformationPanel.setPreferredSize(new Dimension(300, 700));
        test.setPreferredSize(new Dimension(600, 700));
        bottomContainerPanel.setPreferredSize(new Dimension(1000, 120));

        containerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cluedo"));
        InformationPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Information Panel"));


        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
        bottomContainerPanel.setLayout(new BoxLayout(bottomContainerPanel, BoxLayout.X_AXIS));
        bottomContainerPanel.setBorder(new EmptyBorder(0, 10, 0, 0));


        containerPanel.add(test);
        containerPanel.add(InformationPanel);
        mainPanel.add(containerPanel);
        mainPanel.add(bottomContainerPanel);

        try {
            BufferedImage myPicture = ImageIO.read(this.getClass().getResource("Cluedo Images/mrsPlumPlayer.png"));
            //myPicture.
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            //picLabel.setPreferredSize(new Dimension(100,100));
            bottomContainerPanel.add(picLabel);
            picLabel.setBorder(BorderFactory.createTitledBorder("Current Player"));

        } catch (IOException ex) {
            //ex.printStackTrace();
            System.out.println("Unable to locate file(s)");
        }

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(80, 0, 0, 0));
        JTextField inputText = new JTextField(".start", 30);
        JButton submit = new JButton("Submit");
        inputPanel.add(inputText);
        inputPanel.add(submit);
        bottomContainerPanel.add(inputPanel);

        JPanel inputsAvailablePanel = new JPanel();
        inputsAvailablePanel.setLayout(new BoxLayout(inputsAvailablePanel, BoxLayout.Y_AXIS));
        inputsAvailablePanel.setPreferredSize(new Dimension(353, 0));
        inputsAvailablePanel.setBorder(BorderFactory.createTitledBorder("Available Inputs"));
        inputsAvailablePanel.add(new JLabel(".start"));
        inputsAvailablePanel.add(new JLabel(".suggest"));
        inputsAvailablePanel.add(new JLabel(".roll"));
        inputsAvailablePanel.add(new JLabel(".quit"));
        inputsAvailablePanel.add(new JLabel(".movePlayer"));

        bottomContainerPanel.add(inputsAvailablePanel);


        JTextArea infArea = new JTextArea("", 15, 26);
        infArea.setLineWrap(true);
        infArea.setEditable(false);
        infArea.setMaximumSize(infArea.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(infArea);
        InformationPanel.add(scrollPane);
        InformationPanel.add(new JLabel("Your Hand:"));

        try {
            InformationPanel.add(imageToLabel("Images/pplumm.png"));
            InformationPanel.add(imageToLabel("Images/Dagger.png"));
            InformationPanel.add(imageToLabel("Images/ballroom.png"));
        } catch (IOException ex) {
            System.out.println("Unable to locate image(s)");
        }


        submit.addActionListener(e -> infArea.insert("Mrs.White: " + inputText.getText() + "\n", 0));
        submit.addActionListener(e -> Movement("down", "White")); //For testing purposes. Press the submit button and see what happens :)
    }

    // Method that reads images from the disk and converts them into a JLabel to display them on the GUI
    // Used to show current player and player's cards for the time being.

    public JLabel imageToLabel(String path) throws IOException {

        BufferedImage myPicture = ImageIO.read(new File(path));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        return picLabel;

    }
}
