import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Maze extends Canvas implements KeyListener, Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//game variables
	private boolean[] arrow;
	private boolean shoot;
	public boolean drawTextNeeded;
	private int boardSize;
	private int direction;
	private int stamina;
	private Character player;
	private Grid board;
	private Rooms room;
	private Items item;
	private Audio speaker;
	private Spider enemy;
	
	//the images
	private ImageIcon floor;
	private ImageIcon wall;
	private ImageIcon giantEnemySpider;
	private ImageIcon health;
	private ImageIcon ammo;
	private ImageIcon verticalBullet;
	private ImageIcon verticalBulletRefill;
	private ImageIcon horizontalBullet;
	private ImageIcon horizontalBulletRefill;
	private ImageIcon you;

	private JFrame border;
	
	//constructor
	public Maze(JFrame frame, Audio radio) throws IOException
	{
		//assigns the images to the sprite objects
		initImages();
		
		//Gives this class access to the others
		boardSize = 30;
		room = new Rooms();
		arrow = new boolean[4];
		player = new Character(2, 2, 10);
		board = new Grid(boardSize, player.getXPos(), player.getYPos(), player.getLocation(), room);
		item = new Items(player, board);
		enemy = new Spider(board, player.getLocation());
		speaker = radio;
		drawTextNeeded = true;
		stamina = 30;
		border = frame;
		speaker.playAudio("enemyTheme.wav", true);
		
		new Thread(this).start();
		addKeyListener(this);	
	}
	
	   private void initImages() {
		    floor = new ImageIcon(getClass().getClassLoader().getResource("images/floorTile.png"));
			wall = new ImageIcon(getClass().getClassLoader().getResource("images/wallTile.png"));
			giantEnemySpider = new ImageIcon(getClass().getClassLoader().getResource("images/smallEnemySpider.png"));
			health = new ImageIcon(getClass().getClassLoader().getResource("images/healthTile.png"));
			ammo = new ImageIcon(getClass().getClassLoader().getResource("images/ammoTile.png"));
			verticalBullet = new ImageIcon(getClass().getClassLoader().getResource("images/verticalBullet.png"));
			verticalBulletRefill = new ImageIcon(getClass().getClassLoader().getResource("images/verticalBulletRefill.png"));
			horizontalBullet = new ImageIcon(getClass().getClassLoader().getResource("images/horizontalBullet.png"));
			horizontalBulletRefill = new ImageIcon(getClass().getClassLoader().getResource("images/horizontalBulletRefill.png"));
			you = new ImageIcon(getClass().getClassLoader().getResource("images/playerTile.png"));
	   }
	
       //graphics stuff
	   public void paint(Graphics g)
	   {
		  //draws the grid as it is
		  drawGrid(g);
		  //draws the text under the maze
		  drawText(g);
		  //shoots the bullet
		  if (shoot && player.getAmmo() != 0) {
			  Graphics2D g2 = (Graphics2D)g;
			  speaker.playAudio("shootSound.wav", false);
			  g.setColor(Color.BLUE);
			  board.setGridObject(enemy.getXPos(), enemy.getYPos(), 0);
			  enemy.moveCloser(board.getSmell());
			  board.setGridObject(enemy.getXPos(), enemy.getYPos(), 3);
			  
			  int i = playerWallDistance(direction);
			  if (i > 0) {
				  switch(direction) {
				  case 0: 
					  for (int j = 0; j < i * 2; j++) {
						  verticalBullet.paintIcon(this, g2, player.getXPos() * 20, ((player.getYPos() - 1) * 20) + 10 - (10 * j));
						  try { Thread.currentThread(); Thread.sleep(50); } catch (Exception e) {}
						  verticalBulletRefill.paintIcon(this, g2, player.getXPos() * 20, ((player.getYPos() - 1) * 20) + 10 - (10 * j));
					  }
					  break;
				  case 1: 
					  for (int j = 0; j < i * 2; j++) {
						  verticalBullet.paintIcon(this, g2, player.getXPos() * 20, ((player.getYPos() + 1) * 20) + 10 + (10 * j));
						  try { Thread.currentThread(); Thread.sleep(50); } catch (Exception e) {}
						  verticalBulletRefill.paintIcon(this, g2, player.getXPos() * 20, ((player.getYPos() + 1) * 20) + 10 + (10 * j));
					  }
					  break;
				  case 2: 
					  for (int j = 0; j < i * 2; j++) {
						  horizontalBullet.paintIcon(this, g2, ((player.getXPos() - 1) * 20) + 10 - (10 * j), player.getYPos() * 20);
						  try { Thread.currentThread(); Thread.sleep(50); } catch (Exception e) {}
						  horizontalBulletRefill.paintIcon(this, g2, ((player.getXPos() - 1) * 20) + 10 - (10 * j), player.getYPos() * 20);
					  }
					  break;
				  case 3: 
					  for (int j = 0; j < i * 2; j++) {
						  horizontalBullet.paintIcon(this, g2, ((player.getXPos() + 1) * 20) + 10 + (10 * j), player.getYPos() * 20);
						  try { Thread.currentThread(); Thread.sleep(50); } catch (Exception e) {}
						  horizontalBulletRefill.paintIcon(this, g2, ((player.getXPos() + 1) * 20) + 10 + (10 * j), player.getYPos() * 20);
					  }
					  break;
				  }
			  }
			 
			  player.setAmmo(player.getAmmo() - 1);
			  drawTextNeeded = true;
			  shoot = false;
		  }
	   }

	   //Checks to see how far the player is from a wall or the enemy based on the direction its facing.
	   public int playerWallDistance(int d) {
		   boolean hit = false;
		   int distance = 0;
		   int x = player.getXPos();
		   int y = player.getYPos();
		   switch (d) {
		   case 0:
			   while (y != 0 && board.getGridObject(x, y) != 1 && board.getGridObject(x, y) != 3)
			   {
				   distance += 1;
				   y -= 1;
				   if (board.getGridObject(x, y) == 3)
					   hit = true;
			   }
			   if (hit) {
				   enemy.setHealth(enemy.getHealth() - 1);
				   drawTextNeeded = true;
			   }
			   break;
		   case 1: 
			   while (x != board.getGridSize() - 1 && board.getGridObject(x, y) != 1 && board.getGridObject(x, y) != 3)
			   {
				   distance += 1;
				   y += 1;
				   if (board.getGridObject(x, y) == 3)
					   hit = true;
			   }
			   if (hit) {
				   enemy.setHealth(enemy.getHealth() - 1);
				   drawTextNeeded = true;
			   }
			   break;
		   case 2: 
			   while (x != 0 && board.getGridObject(x, y) != 1 && board.getGridObject(x, y) != 3)
			   {
				   distance += 1;
				   x -= 1;
				   if (board.getGridObject(x, y) == 3)
					   hit = true;
			   }
			   if (hit) {
				   enemy.setHealth(enemy.getHealth() - 1);
				   drawTextNeeded = true;
			   }
			   break;
		   case 3: 
			   while (x != board.getGridSize() - 1 && board.getGridObject(x, y) != 1 && board.getGridObject(x, y) != 3)
			   {
				   distance += 1;
				   x += 1;
				   if (board.getGridObject(x, y) == 3)
					   hit = true;
			   }
			   if (hit) {
				   enemy.setHealth(enemy.getHealth() - 1);
				   drawTextNeeded = true;
			   }
			   break;
		   }
		   
		   return distance;
	   }
	   
	   //only what changes is updated to avoid flickering.
	   public void update(Graphics g) {
		   paint(g);
		   drawGrid(g);
		   move();
	   }
	   
	   //reads the current state of the board and prints anything that is new.
	   public void drawGrid(Graphics g) {
		   Graphics2D g2 = (Graphics2D)g;
			  for (int i = 0; i < board.getGridSize(); i++) {
				  for (int j = 0; j < board.getGridSize(); j++) {
					  switch (board.getGridObject(i, j)) {
					  case 0: 
						  floor.paintIcon(this, g2, 20*i, 20*j);
						  break;
					  case 1:
						  wall.paintIcon(this, g2, 20*i, 20*j);
						  break;
					  case 2:
						  you.paintIcon(this, g2, 20*i, 20*j);
						  break;
					  case 3:
						  giantEnemySpider.paintIcon(this, g2, 20*i, 20*j);
						  break;
					  case 4:
						  health.paintIcon(this, g2, 20*i, 20*j);
						  break;
					  case 5:
						  ammo.paintIcon(this, g2, 20*i, 20*j);
						  break;
					  }
				  }
			  }
	   }
	   
	   //displays the player's information.
	   public void drawText(Graphics g) {
		   g.setColor(Color.BLACK);
		   g.fillRect(0, 600, 600, 70);
		   g.setColor(Color.WHITE);
		   g.drawString("Health: " + player.getHealth(), 10, 630);
		   g.drawString("Ammo: " + player.getAmmo(), 210, 630);
		   g.drawString("Enemy Spider Health: " + enemy.getHealth(), 10, 645);
		   g.setColor(Color.BLUE);
		   g.drawString("Press 'r'  to restart", 310, 630);
		   g.drawString("Press 'm'  to return to the main menu", 310, 645);
		   if (player.getHealth() == 0) {
			   g.setColor(Color.RED);
			   g.drawString("Game Over!", 210, 645);
		   }
		   else if (enemy.getHealth() == 0) {
			   g.setColor(Color.GREEN);
			   g.drawString("You Win!", 210, 645);
		   }
		   drawTextNeeded = false;
	   }

	   //based on which key was pressed, moves the character and everything else necessary when the player moves anywhere.
	   public void move() {
		   
		   if (arrow[0]) {
			 //If the player enters a new room
			   if (player.getYPos() == 0) {
				   player.setYLocation(player.getYLocation() - 1);
				   board.setGridObject(player.getXPos(), player.getYPos(), 0);
				   board.cleanGrid();
				   
				   board.updateGrid(player.getLocation(), room);
				   board.setGridObject(player.getXPos(), 29, 2);
				   player.setYPos(29);
				   arrow[0] = false;
				   item.generateCoords(board);
				   item.placeOnBoard(board);
				   enemy.placeSpider(player.getLocation());
				   enemy.updateRoom(board);
				   direction = 0;
				   stamina -= 1;
				   if (stamina == 0) {
					   stamina = 30;
					   player.setHealth(player.getHealth() - 1);
					   drawTextNeeded = true;
				   }
			   }
			 //If the player moves to a space that isn't a wall
			   else if (board.getGridObject(player.getXPos(), player.getYPos() - 1) != 1) 
			   {
				   if (board.getGridObject(player.getXPos(), player.getYPos() - 1) == 4) {
					   if (!player.hasFullHealth(player.getHealth())) {
						   player.setHealth(player.getHealth() + 5);
						   drawTextNeeded = true;
					   }
				   }
				   else if (board.getGridObject(player.getXPos(), player.getYPos() - 1) == 5) {
					   if (!player.hasFullAmmo(player.getAmmo())) {
						   player.setAmmo(player.getAmmo() + 5);
						   drawTextNeeded = true;
					   }
				   }
				   else if (board.getGridObject(player.getXPos(), player.getYPos() - 1) == 3) {
					   player.setHealth(0);
					   drawTextNeeded = true;
				   }
				   board.setGridObject(player.getXPos(), player.getYPos(), 0);
				   board.setGridObject(player.getXPos(), player.getYPos() - 1, 2);
				   player.setYPos(player.getYPos() - 1);
				   board.updateSmell(player.getXPos(), player.getYPos());
				   if (player.getXPos() != 0 
						   && player.getXPos() != board.getGridSize() - 1 
						   && player.getYPos() != 0 
						   && player.getYPos() != board.getGridSize() - 1) 
				   {
					   board.setGridObject(enemy.getXPos(), enemy.getYPos(), 0);
					   enemy.moveCloser(board.getSmell());
					   board.setGridObject(enemy.getXPos(), enemy.getYPos(), 3);
				   }
				   direction = 0;
				   stamina -= 1;
				   if (stamina == 0) {
					   stamina = 30;
					   player.setHealth(player.getHealth() - 1);
					   drawTextNeeded = true;
				   }
				   arrow[0] = false;
			   }
		   }
		   
		   if (arrow[1]) {
			 //If the player enters a new room
			   if (player.getYPos() == 29) {
				   player.setYLocation(player.getYLocation() + 1);
				   board.setGridObject(player.getXPos(), player.getYPos(), 0);
				   board.cleanGrid();
				   
				   board.updateGrid(player.getLocation(), room);
				   board.setGridObject(player.getXPos(), 0, 2);
				   player.setYPos(0);
				   arrow[1] = false;
				   item.generateCoords(board);
				   item.placeOnBoard(board);
				   enemy.placeSpider(player.getLocation());
				   enemy.updateRoom(board);
				   direction = 1;
				   stamina -= 1;
				   if (stamina == 0) {
					   stamina = 30;
					   player.setHealth(player.getHealth() - 1);
					   drawTextNeeded = true;
				   }
			   }
			 //If the player moves to a space that isn't a wall
			   else if (board.getGridObject(player.getXPos(), player.getYPos() + 1) != 1)
			   {
				   if (board.getGridObject(player.getXPos(), player.getYPos() + 1) == 4) {
					   if (!player.hasFullHealth(player.getHealth())) {
						   player.setHealth(player.getHealth() + 5);
						   drawTextNeeded = true;
					   }
				   }
				   else if (board.getGridObject(player.getXPos(), player.getYPos() + 1) == 5) {
					   if (!player.hasFullAmmo(player.getAmmo())) {
						   player.setAmmo(player.getAmmo() + 5);
						   drawTextNeeded = true;
					   }
				   }
				   else if (board.getGridObject(player.getXPos(), player.getYPos() + 1) == 3) {
					   player.setHealth(0);
					   drawTextNeeded = true;
				   }
				   board.setGridObject(player.getXPos(), player.getYPos(),0);
		   	   	   board.setGridObject(player.getXPos(), player.getYPos() + 1,2);
				   player.setYPos(player.getYPos() + 1);
				   board.updateSmell(player.getXPos(), player.getYPos());
				   if (player.getXPos() != 0 
						   && player.getXPos() != board.getGridSize() - 1 
						   && player.getYPos() != 0 
						   && player.getYPos() != board.getGridSize() - 1) 
				   {
					   board.setGridObject(enemy.getXPos(), enemy.getYPos(), 0);
					   enemy.moveCloser(board.getSmell());
					   board.setGridObject(enemy.getXPos(), enemy.getYPos(), 3);
				   }
				   direction = 1;
				   stamina -= 1;
				   if (stamina == 0) {
					   stamina = 30;
					   player.setHealth(player.getHealth() - 1);
					   drawTextNeeded = true;
				   }
				   arrow[1] = false;
			   }
		   }
		   
		   if (arrow[2]) {
			 //If the player enters a new room
			   if (player.getXPos() == 0) {
				   player.setXLocation(player.getXLocation() - 1);
				   board.setGridObject(player.getXPos(), player.getYPos(), 0);
				   board.cleanGrid();
				   
				   board.updateGrid(player.getLocation(), room);
				   board.setGridObject(29, player.getYPos(), 2);
				   player.setXPos(29);
				   arrow[2] = false;
				   item.generateCoords(board);
				   item.placeOnBoard(board);
				   enemy.placeSpider(player.getLocation());
				   enemy.updateRoom(board);
				   direction = 2;
				   stamina -= 1;
				   if (stamina == 0) {
					   stamina = 30;
					   player.setHealth(player.getHealth() - 1);
					   drawTextNeeded = true;
				   }
			   }
			 //If the player moves to a space that isn't a wall
			   else if (board.getGridObject(player.getXPos() - 1, player.getYPos()) != 1)
			   {
				   if (board.getGridObject(player.getXPos() - 1, player.getYPos()) == 4) {
					   if (!player.hasFullHealth(player.getHealth())) {
						   player.setHealth(player.getHealth() + 5);
						   drawTextNeeded = true;
					   }
				   }
				   else if (board.getGridObject(player.getXPos() - 1, player.getYPos()) == 5) {
					   if (!player.hasFullAmmo(player.getAmmo())) {
						   player.setAmmo(player.getAmmo() + 5);
						   drawTextNeeded = true;
					   }
				   }
				   else if (board.getGridObject(player.getXPos() - 1, player.getYPos()) == 3) {
					   player.setHealth(0);
					   drawTextNeeded = true;
				   }
				   board.setGridObject(player.getXPos(), player.getYPos(),0);
		   	   	   board.setGridObject(player.getXPos() - 1, player.getYPos(),2);
				   player.setXPos(player.getXPos() - 1);
				   board.updateSmell(player.getXPos(), player.getYPos());
				   if (player.getXPos() != 0 
						   && player.getXPos() != board.getGridSize() - 1 
						   && player.getYPos() != 0 
						   && player.getYPos() != board.getGridSize() - 1) 
				   {
					   board.setGridObject(enemy.getXPos(), enemy.getYPos(), 0);
					   enemy.moveCloser(board.getSmell());
					   board.setGridObject(enemy.getXPos(), enemy.getYPos(), 3);
				   }
				   direction = 2;
				   stamina -= 1;
				   if (stamina == 0) {
					   stamina = 30;
					   player.setHealth(player.getHealth() - 1);
					   drawTextNeeded = true;
				   }
				   arrow[2] = false;
			   }
		   }
		   
		   if (arrow[3]) {
			   //If the player enters a new room
			   if (player.getXPos() == 29) {
				   player.setXLocation(player.getXLocation() + 1);
				   board.setGridObject(player.getXPos(), player.getYPos(), 0);
				   board.cleanGrid();
				   
				   board.updateGrid(player.getLocation(), room);
				   board.setGridObject(0, player.getYPos(), 2);
				   player.setXPos(0);
				   arrow[3] = false;
				   item.generateCoords(board);
				   item.placeOnBoard(board);
				   enemy.placeSpider(player.getLocation());
				   enemy.updateRoom(board);
				   direction = 3;
				   stamina -= 1;
				   if (stamina == 0) {
					   stamina = 30;
					   player.setHealth(player.getHealth() - 1);
					   drawTextNeeded = true;
				   }
			   }
			   //If the player moves to a space that isn't a wall
			   else if (board.getGridObject(player.getXPos() + 1, player.getYPos()) != 1)
			   {
				   if (board.getGridObject(player.getXPos() + 1, player.getYPos()) == 4) {
					   if (!player.hasFullHealth(player.getHealth())) {
						   player.setHealth(player.getHealth() + 5);
						   drawTextNeeded = true;
					   }
				   }
				   else if (board.getGridObject(player.getXPos() + 1, player.getYPos()) == 5) {
					   if (!player.hasFullAmmo(player.getAmmo())) {
						   player.setAmmo(player.getAmmo() + 5);
						   drawTextNeeded = true;
					   }
				   }
				   else if (board.getGridObject(player.getXPos() + 1, player.getYPos()) == 3) {
					   player.setHealth(0);
					   drawTextNeeded = true;
				   }
				   
				   board.setGridObject(player.getXPos(), player.getYPos(),0);
		   	   	   board.setGridObject(player.getXPos() + 1, player.getYPos(),2);
				   player.setXPos(player.getXPos() + 1);
				   board.updateSmell(player.getXPos(), player.getYPos());
				   if (player.getXPos() != 0 
						   && player.getXPos() != board.getGridSize() - 1 
						   && player.getYPos() != 0 
						   && player.getYPos() != board.getGridSize() - 1) 
				   {
					   board.setGridObject(enemy.getXPos(), enemy.getYPos(), 0);
					   enemy.moveCloser(board.getSmell());
					   board.setGridObject(enemy.getXPos(), enemy.getYPos(), 3);
				   }
				   direction = 3;
				   stamina -= 1;
				   if (stamina == 0) {
					   stamina = 30;
					   player.setHealth(player.getHealth() - 1);
					   drawTextNeeded = true;
				   }
				   arrow[3] = false;
			   }
		   }
		   if (enemy.getXPos() == player.getXPos() && enemy.getYPos() == player.getYPos()) {
			   player.setHealth(0);
			   drawTextNeeded = true;
		   }
	   }
	   
	   //print the board for debugging.
	   public void printBoard() {
		   for (int i = 0; i < board.getGridSize(); i++) {
				  for (int j = 0; j < board.getGridSize(); j++) { 
					  System.out.print(board.getGridObject(j, i) + " ");
					  if (j == boardSize - 1)
						  System.out.println();
				  }
	       }
	   }
	   
	   //pressing arrow key events.
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_UP : arrow[0]=true; break;
				case KeyEvent.VK_DOWN : arrow[1]=true; break;
				case KeyEvent.VK_LEFT : arrow[2]=true; break;
				case KeyEvent.VK_RIGHT : arrow[3]=true; break;
				case KeyEvent.VK_W : arrow[0]=true; break;
				case KeyEvent.VK_S : arrow[1]=true; break;
				case KeyEvent.VK_A : arrow[2]=true; break;
				case KeyEvent.VK_D : arrow[3]=true; break;
				case KeyEvent.VK_SPACE : shoot = true; break;
				case KeyEvent.VK_M : 
					try { speaker.stopMainAudio(); } catch (Exception f) {}
					Audio speaker1 = new Audio();
					new Mainmenu(border, speaker1);
					break;
				case KeyEvent.VK_R :
					try { speaker.stopMainAudio(); } catch (Exception f) {}
					Audio speaker2 = new Audio();
					Mainmenu restart1 = new Mainmenu(border, speaker2);
					try { restart1.startGame(); } catch (Exception f) {}
					break;
			}
		}

		public void keyReleased(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_UP : arrow[0]=false; break;
				case KeyEvent.VK_DOWN : arrow[1]=false; break;
				case KeyEvent.VK_LEFT : arrow[2]=false; break;
				case KeyEvent.VK_RIGHT : arrow[3]=false; break;
				case KeyEvent.VK_W : arrow[0]=false; break;
				case KeyEvent.VK_S : arrow[1]=false; break;
				case KeyEvent.VK_A : arrow[2]=false; break;
				case KeyEvent.VK_D : arrow[3]=false; break;
				case KeyEvent.VK_SPACE : shoot = false; break;
			}
		}

		public void keyTyped(KeyEvent e){
			
		}
		
		//Runs the program and allows it to repaint over time.
		public void run() {
			try
		   	{
		   		while(player.getHealth() > 0 && enemy.getHealth() > 0)
		   		{
		   		   Thread.currentThread();
		   		   Thread.sleep(20);
		           repaint();
		         }
		   		try { speaker.stopMainAudio(); } catch(Exception e) {}
		    }
			catch(Exception e) {}
		}
}