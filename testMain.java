import javax.swing.*;


public class testMain extends JFrame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BoardPanel test = new BoardPanel();
		JFrame frame = new JFrame();
		frame.setSize(660, 700);
		frame.setTitle("An Empty frame");
		
		
		frame.add(test);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
