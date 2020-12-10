import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Mainmenu extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Maze game;
	
	private JLayeredPane mainmenu;
	private JLayeredPane theGame;
	private JPanel gameInfo;
	private JFrame border;
	
	private JLabel background;
	private JLabel startButton;
	private JLabel playAudio1;
	private JLabel playAudio2;
	private JLabel muteAudio1;
	private JLabel muteAudio2;
	private JLabel unmuteAudio1;
	private JLabel unmuteAudio2;
	
	private JButton start;
	private JButton muteTheme;
	private JButton playTheme;
	private JButton muteShoot;
	private JButton playShoot;
	
	private Press click;
	
	private Audio radio;
	
	private ImageIcon ses;
	
	public Mainmenu(JFrame frame, Audio radio) {
		background = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/enemyTitleScreen.png")));
		startButton = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/startGame.png")));
		playAudio1 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/playAudio.png")));
		playAudio2 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/playAudio.png")));
		muteAudio1 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/muteAudio.png")));
		muteAudio2 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/muteAudio.png")));
		unmuteAudio1 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/unmuteAudio.png")));
		unmuteAudio2 = new JLabel(new ImageIcon(getClass().getClassLoader().getResource("images/unmuteAudio.png")));
		
		ses = new ImageIcon(getClass().getClassLoader().getResource("images/smallEnemySpider.png"));
		
		mainmenu = new JLayeredPane();
		theGame = new JLayeredPane();
		gameInfo = new JPanel();
		gameInfo.setLayout(null);
		mainmenu.setLayout(null);
		theGame.setLayout(null);
		click = new Press();
		border = frame;
		border.getContentPane().removeAll();
		border.setIconImage(ses.getImage());
		
		this.radio = radio;
		makeButtons();
		makeMenu();
		
		border.add(mainmenu);
		border.setVisible(true);
	}
	
	private void makeButtons() {
		start = new JButton();
		start.addActionListener(click);
		muteTheme = new JButton("Music On");
		muteTheme.addActionListener(click);
		playTheme = new JButton();
		playTheme.addActionListener(click);
		muteShoot = new JButton("Music On");
		muteShoot.addActionListener(click);
		playShoot = new JButton();
		playShoot.addActionListener(click);
	}
	
	private void makeMenu() {
		mainmenu.add(background, JLayeredPane.DEFAULT_LAYER);
		background.setSize(605,700);
		background.setLocation(0,0);
		
		mainmenu.add(start);
		start.setSize(200,75);
		start.setLocation(400,300);
	
		mainmenu.add(startButton, JLayeredPane.POPUP_LAYER);
		startButton.setSize(200,75);
		startButton.setLocation(400,300);
		
		mainmenu.add(playTheme);
		playTheme.setSize(50,50);
		playTheme.setLocation(400, 400);
		
		mainmenu.add(playAudio1, JLayeredPane.POPUP_LAYER);
		playAudio1.setSize(50,50);
		playAudio1.setLocation(400,400);
		
		mainmenu.add(muteTheme);
		muteTheme.setSize(150,50);
		muteTheme.setLocation(450,400);
		
		mainmenu.add(muteAudio1, JLayeredPane.POPUP_LAYER);
		muteAudio1.setSize(150,50);
		muteAudio1.setLocation(450,400);
		
		mainmenu.add(playShoot);
		playShoot.setSize(50,50);
		playShoot.setLocation(400,475);
		
		mainmenu.add(playAudio2, JLayeredPane.POPUP_LAYER);
		playAudio2.setSize(50,50);
		playAudio2.setLocation(400,475);
		
		mainmenu.add(muteShoot);
		muteShoot.setSize(150,50);
		muteShoot.setLocation(450,475);
		
		mainmenu.add(muteAudio2, JLayeredPane.POPUP_LAYER);
		muteAudio2.setSize(150,50);
		muteAudio2.setLocation(450,475);
		
		mainmenu.setFocusable(true);
	}
	
	public void startGame() throws IOException {
		try {radio.stopAudio(); } catch (Exception e) {}
		border.getContentPane().removeAll();
		
		game = new Maze(border, radio);
		border.add(game);
		game.setBounds(0,0,600,670);
		game.requestFocus();
		border.setVisible(true);
	}
	
	private class Press implements ActionListener {
		
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == start) {
				try { startGame(); } catch(Exception f) {}
			}
			
			else if (e.getSource() == playTheme) {
				try { radio.stopAudio(); } catch(Exception f) {}
				radio.playAudio("enemyTheme.wav", false);
			}
			
			else if (e.getSource() == muteTheme) {
				if (muteTheme.getText().equals("Music On")) {
					muteTheme.setText("Music Off"); 
					radio.addMutedClip("enemyTheme.wav");
					try { radio.stopAudio(); } catch(Exception f) {}
					mainmenu.remove(muteAudio1);
					mainmenu.add(unmuteAudio1, JLayeredPane.POPUP_LAYER);
					unmuteAudio1.setSize(150,50);
					unmuteAudio1.setLocation(450,400);
				}
				else {
					muteTheme.setText("Music On"); 
					radio.removeMutedClip("enemyTheme.wav");
					mainmenu.remove(unmuteAudio1);
					mainmenu.add(muteAudio1, JLayeredPane.POPUP_LAYER);
					muteAudio1.setSize(150,50);
					muteAudio1.setLocation(450,400);
				}
			}
			
			else if (e.getSource() == playShoot) {
				try { radio.stopAudio(); } catch(Exception f) {}
				radio.playAudio("shootSound.wav", false);
			}
			
			else if (e.getSource() == muteShoot) {
				if (muteShoot.getText().equals("Music On")) {
					muteShoot.setText("Music Off"); 
					radio.addMutedClip("shootSound.wav");
					try { radio.stopAudio(); } catch(Exception f) {}
					mainmenu.remove(muteAudio2);
					mainmenu.add(unmuteAudio2, JLayeredPane.POPUP_LAYER);
					unmuteAudio2.setSize(150,50);
					unmuteAudio2.setLocation(450,475);
				}
				else {
					muteShoot.setText("Music On"); 
					radio.removeMutedClip("shootSound.wav");
					mainmenu.remove(unmuteAudio2);
					mainmenu.add(muteAudio2, JLayeredPane.POPUP_LAYER);
					muteAudio2.setSize(150,50);
					muteAudio2.setLocation(450,475);
				}
			}
			
		}
	}
}