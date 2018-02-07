import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class CluedoUI extends JFrame {


    JPanel mainPanel = new JPanel();

    public CluedoUI() {

        JFrame frame = new JFrame();
        DrawMainPanel();

        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
       // frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setSize(1000, 770);
        frame.setTitle("Cluedo");
        frame.setVisible(true);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        //test.movementTest();
        //test.repaint();

        System.out.println("Do we get this far?");
    }

    public void DrawMainPanel(){
        JPanel bottomContainerPanel = new JPanel();
        JPanel containerPanel = new JPanel();
        BoardPanel test = new BoardPanel();
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
            BufferedImage myPicture = ImageIO.read(this.getClass().getResource("mrsPlumPlayer.png"));
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
    }
}