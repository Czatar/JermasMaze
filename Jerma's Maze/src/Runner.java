import javax.swing.*;
import java.io.*;

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
		 
		Mainmenu screen = new Mainmenu(frame, radio);	
	}  
}