import javax.swing.*;


public class testMain extends JFrame{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BoardPanel test = new BoardPanel();
		JFrame frame = new JFrame();
		frame.setSize(660, 700);
		frame.setTitle("Cluedo");

		frame.add(test);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		test.movementTest();
		test.repaint();

		System.out.println("Do we get this far?");
	}


}
