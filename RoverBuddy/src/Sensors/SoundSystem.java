package Sensors;

import lejos.nxt.Sound;
import Interfaces.SoundInterface;

public class SoundSystem implements SoundInterface{
	public void PlayTouch(){
		Sound.buzz();
	}
	public void PlaySuccess(){
		for(int i = 0; i < 3; i++){
			Sound.playTone(5000, 15);
			try {
				Thread.sleep(350);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void PlayFailure(){
		Sound.playTone(10000, 100);
	}
	public void PlayBackup(){
		Sound.beep();
	}
}
