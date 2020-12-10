import java.util.*;
import java.awt.*;

import javax.swing.*;

public class Items extends Canvas {

	private int[] itemLocations;
	private Random rand;
	private int randx, randy;
	private int t;
	
	public Items(Character player, Grid board) {
		
		rand = new Random();
		itemLocations = new int[6];
		t = 4;
		
		generateCoords(board);
		placeOnBoard(board);
	}
	
	public int[] generateCoords(Grid b) {
		for (int i = 0; i < itemLocations.length; i++) {	
			if(i%2 == 0) {	
				randx = rand.nextInt(28) + 1;
				randy = rand.nextInt(28) + 1;
				while(b.getGridObject(randx,randy) != 0) {
					randx = rand.nextInt(28) + 1;
					randy = rand.nextInt(28) + 1;
				}
				itemLocations[i] = randx;
			}
			else {
				itemLocations[i] = randy;
			}	
		}
		return itemLocations;
	}
	
	public void placeOnBoard(Grid b) {
		t = rand.nextInt(2) + 4;
		b.setGridObject(itemLocations[0],itemLocations[1],t);
		t = rand.nextInt(2) + 4;
		b.setGridObject(itemLocations[2],itemLocations[3],t);
		t = rand.nextInt(2) + 4;
		b.setGridObject(itemLocations[4],itemLocations[5],t);
	}
}