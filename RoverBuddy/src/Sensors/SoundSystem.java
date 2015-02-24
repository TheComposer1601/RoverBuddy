package Sensors;

import lejos.nxt.Sound;
import Interfaces.SoundInterface;

public class SoundSystem implements SoundInterface{
	public void PlayTouch(){
		Sound.buzz();
	}
	public void PlaySuccess(){
		for(int i = 0; i < 3; i++){
			Sound.playTone(4000, 15);
			try {
				wait(150);
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
