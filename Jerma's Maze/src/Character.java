public class Character {
	
	//stores grid positions
	private int xpos, ypos;
	private int xroom, yroom;
	//stores the state of the player - to be changed
	private int health, ammo;
	private int[][] location;
	
		public Character(int xp, int yp, int ph) {
			location = new int[3][3];
			setLocations();
			xpos = xp;
			ypos = yp;
			health = ph;
			ammo = 0;
			xroom = 1;
			yroom = 1;
		}
		
		public void setXPos(int x) {
			xpos = x;
		}
		
		public void setYPos(int y) {
			ypos = y;
		}
		
		public void setHealth(int life) {
			health = life;
			if (life > 30)
				health = 30;
		}
		
		public void setAmmo(int a) {
			ammo = a;
			if (a > 50)
				ammo = 50;
		}
		
		public int getXPos() {
			return xpos;
		}
		
		public int getYPos() {
			return ypos;
		}
		
		public int getHealth() {
			return health;
		}
		
		public int getAmmo() {
			return ammo;
		}
		
		public boolean hasFullHealth(int h) {
			if (h == 20)
				return true;
			return false;
		}
		
		public boolean hasFullAmmo(int a) {
			if (a == 50)
				return true;
			return false;
		}
		
		public void setLocations() {
			location[0][0] = 7;
			location[0][1] = 8;
			location[0][2] = 9;
			location[1][0] = 4;
			location[1][1] = 5;
			location[1][2] = 6;
			location[2][0] = 1;
			location[2][1] = 2;
			location[2][2] = 3;
		}
		
		public int getLocation() {
			return location[xroom][yroom];
		}
		
		public void setXLocation(int x) {
			xroom = x;
		}
		
		public void setYLocation(int y) {
			yroom = y;
		}
		
		public int getXLocation() {
			return xroom;
		}
		
		public int getYLocation() {
			return yroom;
		}
}