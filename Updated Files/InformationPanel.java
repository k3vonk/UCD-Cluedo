
import java.awt.Dimension;


import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InformationPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 34;
	private static final int WIDTH = 26;

	private JTextArea infoArea = new JTextArea("", HEIGHT, WIDTH);
	
	public InformationPanel() {
		JScrollPane scroll = new JScrollPane(infoArea);
		setPreferredSize(new Dimension(300, 700));
		
		//border style
		setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Information Panel"));
		
		//Ensures textarea doesnt expand to the right, but pushes text downwards
		infoArea.setLineWrap(true);
		infoArea.setEditable(false); //Non editable, so players can't text here
		infoArea.setMaximumSize(infoArea.getPreferredSize());
		add(scroll);
		add(new JLabel("Your Hand:"));
		
	}
	
	/**
	 * A method that updates the content on the information panel that is displayed to everyone
	 * 
	 * @param value - The string of text to be displayed
	 */
	public void updateContent(String value) {
			infoArea.append(value + "\n");
	}
	


}
