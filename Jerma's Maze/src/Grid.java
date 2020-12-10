public class Grid {
	//0 = nothing
	//1 = wall
	//2 = player
	//3 = THE ENEMY SPIDER
	//4 = ammo
	//5 = health
	//6 = bullet
	
	private int[][] grid;
	private int[][] smell;
	
	public Grid(int x, int px, int py, int pr, Rooms room) {
		grid = new int[x][x];
		updateGrid(pr,room);
		grid[px][py] = 2;
		smell = new int[x][x];
		updateSmell(px, py);
	}
	//sets an object at location x y
	public void setGridObject(int x, int y, int type) {
		grid[x][y] = type;
	}
	
	//returns the object at location x y on the current grid
	public int getGridObject(int x, int y) {
		return grid[x][y];
	}
	
	//returns the grid size
	public int getGridSize() {
		return grid.length;
	}
	
	//generates pre-made walls
	public void updateGrid(int index, Rooms room) {
		grid = room.getRoom(index);
	}
	
	public void cleanGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == 4 || grid[i][j] == 5 || grid[i][j] == 3) {
					setGridObject(i,j,0);
				}
			}
		}
	}
	
	//print the smell array for debugging
	public void printsmell() {
		for (int i = 0; i < smell.length; i++) {
			for (int j = 0; j < smell.length; j++) {
				if (smell[i][j] == 1000)
					System.out.print("111" + " ");
				else if (smell[i][j] == 0)
					System.out.print("000 ");
				else
					System.out.print(smell[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public int[][] getSmell() {
		return smell;
	}
	
	public void updateSmell(int x, int y) {
		for (int i = 0; i < smell.length; i++) {
			for (int j = 0; j < smell.length; j++) {
				if (grid[i][j] == 1)
					smell[i][j] = 0;
				else
					smell[i][j] = 1;
			}
		}
		
		int n = 0;
		
		if (y != 0) {
			if (smell[x][y - 1] > 0)
				spreadUp(x,y - 1, n + 1);
		}
		if (y != grid.length - 1) {
			if (smell[x][y + 1] > 0)
				spreadDown(x,y + 1, n + 1);
		}
		if (x != 0) {
			if (smell[x - 1][y] > 0)
				spreadLeft(x - 1,y, n + 1);
		}
		if (x != grid.length - 1) {
			if (smell[x + 1][y] > 0)
				spreadRight(x + 1,y, n + 1);
		}
		
		smell[x][y] = 1000;
	}
	
	public void spreadUp(int x, int y, int n) {
		smell[x][y] = 1000 - n;
		
		if (y != 0) {
			if (smell[x][y - 1] > 0 && 1000 - n - 1 > smell[x][y - 1])
				spreadUp(x, y - 1, n + 1);
		}
		if (x != 0) {
			if (smell[x - 1][y] > 0 && 1000 - n - 1 > smell[x - 1][y])
				spreadLeft(x - 1, y, n + 1);
		}
		if (x != smell.length - 1) {
			if (smell[x + 1][y] > 0 && 1000 - n - 1 > smell[x + 1][y])
				spreadRight(x + 1, y, n + 1);
		}
	}
	
	public void spreadDown(int x, int y, int n) {
		smell[x][y] = 1000 - n;
		
		if (y != smell.length - 1) {
			if (smell[x][y + 1] > 0 && 1000 - n - 1 > smell[x][y + 1])
				spreadDown(x, y + 1, n + 1);
		}
		if (x != 0) {
			if (smell[x - 1][y] > 0 && 1000 - n - 1 > smell[x - 1][y])
				spreadLeft(x - 1, y, n + 1);
		}
		if (x != smell.length - 1) {
			if (smell[x + 1][y] > 0 && 1000 - n - 1 > smell[x + 1][y])
				spreadRight(x + 1, y, n + 1);
		}
	}
	
	public void spreadLeft(int x, int y, int n) {
		smell[x][y] = 1000 - n;
		
		if (y != 0) {
			if (smell[x][y - 1] > 0 && 1000 - n - 1 > smell[x][y - 1])
				spreadUp(x, y - 1, n + 1);
		}
		if (y != smell.length - 1) {
			if (smell[x][y + 1] > 0 && 1000 - n - 1 > smell[x][y + 1])
				spreadDown(x, y + 1, n + 1);
		}
		if (x != 0) {
			if (smell[x - 1][y] > 0 && 1000 - n - 1 > smell[x - 1][y])
				spreadLeft(x - 1, y, n + 1);
		}
	}
	
	public void spreadRight(int x, int y, int n) {
		smell[x][y] = 1000 - n;
		
		if (y != 0) {
			if (smell[x][y - 1] > 0 && 1000 - n - 1 > smell[x][y - 1])
				spreadUp(x, y - 1, n + 1);
		}
		if (y != smell.length - 1) {
			if (smell[x][y + 1] > 0 && 1000 - n - 1 > smell[x][y + 1])
				spreadDown(x, y + 1, n + 1);
		}
		if (x != smell.length - 1) {
			if (smell[x + 1][y] > 0 && 1000 - n - 1 > smell[x + 1][y])
				spreadRight(x + 1, y, n + 1);
		}
	}
	
}