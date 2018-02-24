import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * This UI combines three main panel concepts together onto one frame in which 
 * users can interact with.
 * 
 * @Team MAGA
 * @Author Gajun Young - 16440714
 * @Author Royal Thomas - 16326926
 * @Author Richard  Otroshchenko
 */
public class CluedoUI {

	//Size of the frame
	private static final int FRAME_WIDTH = 1020;
	private static final int FRAME_HEIGHT = 820;
	
	private BoardPanel board;
	private InformationPanel info;
	private CommandPanel command;
	
	//Constructor
	public CluedoUI(Players players, Weapons weapons) {
		JFrame frame = new JFrame();
		JPanel mainPanel = new JPanel();
		board = new BoardPanel(players, weapons);
		command = new CommandPanel();
		info = new InformationPanel();
	
		// Set up the main panel to look good, this panel contains two panels.
		mainPanel.add(board);
		mainPanel.add(info);
        mainPanel.setPreferredSize(new Dimension(1000, 750));
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Cluedo"));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		//Position all the panels into their correct places
		frame.add(mainPanel);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Cluedo");
		frame.add(mainPanel, BorderLayout.LINE_START);
	    frame.add(command,BorderLayout.PAGE_END);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 * Sets the board with weapons[default] and tokens which depends on the user inputs
	 * @param tokens
	 * @param weapons
	 */
	public void setBoard(Players players, Weapons weapons) {
			board.set(players, weapons);
	}
	
	/**
	 * @return A String that the user types
	 */
	public String getCommand() {
	        return command.getCommand();
	}

	/**
	 * Repaint the board to show updated tokens
	 */
	public void display() {
	        board.repaint();
	}

	/**
	 * Takes a string in which it gets updated onto the information panel
	 * @param string contains information of a tokens turn
	 */
	public void displayString(String string) {
	       info.updateContent(string);
	}
	
	
}
