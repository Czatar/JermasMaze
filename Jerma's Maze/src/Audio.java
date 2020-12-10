import java.io.File;
import java.net.URL;

import javax.sound.sampled.*;

public class Audio {

	private AudioInputStream audioInput;
	private AudioInputStream mainAudioInput;
	private Clip clip;
	private Clip mainClip;
	private URL mainTheme;
	private Boolean[] muted;
	
	public Audio() {
		mainTheme = getClass().getResource("sounds/enemyTheme.wav");
		try { 
			mainAudioInput = AudioSystem.getAudioInputStream(mainTheme); 
			mainClip = AudioSystem.getClip();
			mainClip.open(mainAudioInput);
		} catch(Exception e) {}
		
		
		muted = new Boolean[5];
		for (int i = 0; i < muted.length; i++)
			muted[i] = false;
	}
	
	public void stopAudio() {
		clip.stop();
	}
	
	public void stopMainAudio() {
		mainClip.stop();
	}
	
	public void addMutedClip(String clip) {
		switch (clip) {
		case "enemyTheme.wav": 
			muted[0] = true;
			break;
		case "shootSound.wav":
			muted[1] = true;
			break;
		}
	}
	
	public void removeMutedClip(String clip) {
		switch (clip) {
		case "enemyTheme.wav": 
			muted[0] = false;
			break;
		case "shootSound.wav":
			muted[1] = false;
			break;
		}
	}
	
	public boolean isMuted(String clip) {
		switch(clip) {
		case "enemyTheme.wav":
			return muted[0];
		case "shootSound.wav":
			return muted[1];
		default:
			return false;
		}
	}
	
	public void setAudio(String location) {
		
	}
	
	public void playAudio(String location, boolean loop) {
		try {
			if (!isMuted(location)) {
				URL music = getClass().getResource("sounds/" + location);
				audioInput = AudioSystem.getAudioInputStream(music);
				clip = AudioSystem.getClip();
				clip.open(audioInput);
			
				//plays the thing
				if (location.equals("enemyTheme.wav") && loop) {
					mainClip.setLoopPoints(49000,-1);
					mainClip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				else {
					clip.start();
				}
			}
		}
		catch (Exception e) {
			System.out.println("Something went wrong playing the audio. Make sure the file is in the wav format.");
		}
	}
}