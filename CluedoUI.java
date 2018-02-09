import javax.imageio.ImageIO;
import javax.swing.*;
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
        frame.setSize(1000, 770);
        frame.setTitle("Cluedo");
        frame.setVisible(true);

    }

    //Methods to move the characters 1 space.
    //For now these move both players and weapons. The input is the name of what you want to move and direction to move.
    public void Movement(String direction, String name){test.Movement(direction,name)}
    //Method to set the weapons somewhere. Unused for assignment 1.
    public void setWeaponTile(String name, int newRow, int newColumn){test.setWeaponTile(name,newRow,newColumn);}

    public void DrawMainPanel(){
        JPanel bottomContainerPanel = new JPanel();
        JPanel containerPanel = new JPanel();
        JPanel InformationPanel = new JPanel();

        containerPanel.setPreferredSize(new Dimension(1000, 700));
        InformationPanel.setPreferredSize(new Dimension(300, 700));
        test.setPreferredSize(new Dimension(600, 700));
        bottomContainerPanel.setPreferredSize(new Dimension(1000, 100));

        containerPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Cluedo"));
        InformationPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Information Panel"));




        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));

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

        } catch (IOException ex) {
            //ex.printStackTrace();
            System.out.println("Unable to locate file(s)");
        }

        JPanel inputPanel = new JPanel();
        JTextField inputText = new JTextField(".start", 30);
        JButton submit = new JButton("Submit");
        inputPanel.add(inputText);
        inputPanel.add(submit);
        bottomContainerPanel.add(inputPanel);

        bottomContainerPanel.add(new Label("Inputs available: \n.start .suggest .roll .quit"));

        JTextArea infArea = new JTextArea("", 33, 26);
        infArea.setLineWrap(true);
        infArea.setEditable(false);
        infArea.setMaximumSize(infArea.getPreferredSize());
        JScrollPane scrollPane = new JScrollPane(infArea);
        InformationPanel.add(scrollPane);
        InformationPanel.add(new JLabel("Your Hand:"));
        try {
            BufferedImage myPicture = ImageIO.read(new File("pplumm.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            InformationPanel.add(picLabel);

            myPicture = ImageIO.read(new File("Dagger.png"));
            picLabel = new JLabel(new ImageIcon(myPicture));
            InformationPanel.add(picLabel);
            	/*
            myPicture = ImageIO.read(new File("ballroom.png"));
            picLabel = new JLabel(new ImageIcon(myPicture));
            InformationPanel.add(picLabel);*/

        } catch (IOException ex) {
            //ex.printStackTrace();
            System.out.println("Unable to locate image(s)");
        }

        submit.addActionListener(e -> infArea.insert("Mrs.White: " + inputText.getText() + "\n", 0));
        submit.addActionListener(e -> Movement("down", "White")); //For testing purposes. Press the submit button and see what happens :)
    }
}
