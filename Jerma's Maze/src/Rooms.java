import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Rooms {

	private int[][] room1, room2, room3, room4, room5, room6, room7, room8, room9;
	ArrayList<int[][]> rooms;
	
	public Rooms() throws IOException {
		room1 = new int[30][30];
		room2 = new int[30][30];
		room3 = new int[30][30];
		room4 = new int[30][30];
		room5 = new int[30][30];
		room6 = new int[30][30];
		room7 = new int[30][30];
		room8 = new int[30][30];
		room9 = new int[30][30];
		rooms = new ArrayList<int[][]>(9);
		
		InputStream inputStream = Rooms.class.getResourceAsStream("/text/theRooms.txt");
		InputStreamReader streamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(streamReader);
		String line = "";
		
		//for each chunk in the file, it's copied onto 9 rooms.
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 30; j++) {
				line = reader.readLine();
				switch (i) {
				case 0: room1[j] = createRooms(line, i, j); break;
				case 1: room2[j] = createRooms(line, i, j); break;
				case 2: room3[j] = createRooms(line, i, j); break;
				case 3: room4[j] = createRooms(line, i, j); break;
				case 4: room5[j] = createRooms(line, i, j); break;
				case 5: room6[j] = createRooms(line, i, j); break;
				case 6: room7[j] = createRooms(line, i, j); break;
				case 7: room8[j] = createRooms(line, i, j); break;
				case 8: room9[j] = createRooms(line, i, j); break;
				}
			}
			line = reader.readLine();		
		}
		addAllRooms();
		reader.close();
	}
	
	public int[] createRooms(String nums, int roomNumber, int roomRow) {
		int[] tempRoom = new int[30];
		for (int i = 0; i < nums.length(); i++) {
			
			if (i == nums.length() - 1) {
				tempRoom[i] = Integer.parseInt(nums.substring(i));
			}
			else {
				tempRoom[i] = Integer.parseInt(nums.substring(i, i + 1));
			}
		}
		
		return tempRoom;
	}
	
	public void addAllRooms() {
		rooms.add(room1);
		rooms.add(room2);
		rooms.add(room3);
		rooms.add(room4);
		rooms.add(room5);
		rooms.add(room6);
		rooms.add(room7);
		rooms.add(room8);
		rooms.add(room9);
	}
	
	public int[][] getRoom(int n) {
		return rooms.get(n - 1);
	}
	
	public void printRoom(int[][] temp) {
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				System.out.print(temp[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}