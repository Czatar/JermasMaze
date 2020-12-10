import java.io.IOException;
import javax.swing.JFrame;

public class Runner
{
	
	//starts the game
	public static void main(String args[]) throws InterruptedException, IOException
	{
		JFrame frame = new JFrame("Jerma's Maze");
		frame.setSize(605,700);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Audio radio = new Audio();
		 
		new Mainmenu(frame, radio);
	}  
}