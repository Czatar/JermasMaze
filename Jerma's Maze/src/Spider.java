public class Spider {

	private Grid board;
	private int health;
	
	private int xpos;
	private int ypos;
	
	private int direction;
	
	public Spider(Grid grid, int room) {
		board = grid;
		health = 30;
		direction = 4;
		
		placeSpider(room);
	}
	
	public void updateRoom(Grid grid) {
		board = grid;
	}
	
	public void moveCloser(int[][] smell) {
		double upDifference = smell[xpos][ypos];
		double downDifference = smell[xpos][ypos];
		double leftDifference = smell[xpos][ypos];
		double rightDifference = smell[xpos][ypos];
		
		if (ypos != 0 || board.getGridObject(xpos, ypos - 1) != 1)
			upDifference -= smell[xpos][ypos - 1];
		else
			upDifference = 1000;
		
		if (ypos != smell.length - 1 || board.getGridObject(xpos, ypos + 1) != 1)
			downDifference -= smell[xpos][ypos + 1];
		else
			downDifference = 1000;
		
		if (xpos != 0 || board.getGridObject(xpos - 1, ypos) != 1)
			leftDifference -= smell[xpos - 1][ypos];
		else 
			leftDifference = 1000;
		
		if (xpos != smell.length - 1 || board.getGridObject(xpos + 1, ypos) != 1)
			rightDifference -= smell[xpos + 1][ypos];
		else
			rightDifference = 1000;
		
		
		switch (findDirection(upDifference, downDifference, leftDifference, rightDifference)) {
		case 0:
			ypos -= 1;
			break;
		case 1:
			xpos += 1;
			break;
		case 2:
			ypos += 1;
			break;
		case 3:
			xpos -= 1;
			break;
		default:
			break;
		}
	}
	
	public int findDirection(double up, double down, double left, double right) {
		direction = 4;
		if (up <= down && up <= left && up <= right)
			direction = 0;
		if (down <= up && down <= left && down <= right)
			direction = 2;
		if (right <= up && right <= down && right <= left)
			direction = 1;
		if (left <= up && left <= down && left <= right)
			direction = 3;
		
		return direction;
	}
	
	public void placeSpider(int r) {
		
		switch(r) {
		case 1:
			xpos = 28;
			ypos = 3;
			break;
		case 2:
			xpos = 12;
			ypos = 18;
			break;
		case 3:
			xpos = 6;
			ypos = 21;
			break;
		case 4:
			xpos = 28;
			ypos = 7;
			break;
		case 5:
			xpos = 21;
			ypos = 9;
			break;
		case 6:
			xpos = 15;
			ypos = 17;
			break;
		case 7:
			xpos = 21;
			ypos = 23;
			break;
		case 8:
			xpos = 7;
			ypos = 10;
			break;
		case 9:
			xpos = 7;
			ypos = 7;
			break;
		}
		
		board.setGridObject(xpos, ypos, 3);
	}
	
	public void setHealth(int h) {
		health = h;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setXPos(int x) {
		xpos = x;
	}
	
	public void setYPos(int y) {
		ypos = y;
	}
	
	public int getXPos() {
		return xpos;
	}
	
	public int getYPos() {
		return ypos;
	}
	
}