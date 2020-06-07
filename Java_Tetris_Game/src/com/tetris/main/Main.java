package com.tetris.main;

import com.tetris.view.StartMenuPopup;
import java.io.File;

import javax.sound.sampled.AudioSystem;

import javax.sound.sampled.Clip;


public class Main {

	/**

	 * @param args

	 */

	public static void main(String[] args) {

		new StartMenuPopup().setVisible(true);

		File Clap =new File("res/tetris_background_music.wav");

		PlaySound(Clap);
		
		

		

	}

	

	static void PlaySound(File Sound)

	{

		try{

			Clip clip = AudioSystem.getClip();

			clip.open(AudioSystem.getAudioInputStream(Sound));

			clip.start();

		

			Thread.sleep(clip.getMicrosecondLength()/1000);

		}

		catch(Exception e)

		{

			

		}

	}


					
		
	
	
}