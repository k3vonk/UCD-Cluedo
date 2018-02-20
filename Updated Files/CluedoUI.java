import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class CluedoUI {

	private static final int FRAME_WIDTH = 1020;
	private static final int FRAME_HEIGHT = 820;
	
	private BoardPanel board;
	private InformationPanel info;
	private CommandPanel command;
	
	public CluedoUI(Players players, Weapons weapons) {
		JFrame frame = new JFrame();
		JPanel mainPanel = new JPanel();
		board = new BoardPanel(players, weapons);
		command = new CommandPanel();
		info = new InformationPanel();
		
		mainPanel.add(board);
		mainPanel.add(info);
		// Set up the main panel to look good, this panel contains all other panels.
        mainPanel.setPreferredSize(new Dimension(1000, 750));
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cluedo"));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		frame.add(mainPanel);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Cluedo");
		frame.add(mainPanel, BorderLayout.LINE_START);
	    frame.add(command,BorderLayout.PAGE_END);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	 public String getCommand() {
	        return command.getCommand();
	    }

	    public void display() {
	        board.repaint();
	    }

	    public void displayString(String string) {
	       info.updateContent(string);
	    }
	
	
}
