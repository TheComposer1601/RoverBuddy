package Sensors;

import lejos.nxt.Sound;
import Interfaces.SoundInterface;

public class SoundSystem implements SoundInterface{
	private static final int SUCCESS_FREQUENCY = 7500;
	private static final int SUCCESS_INTERVAL = 350;
	private static final int SUCCESS_DURATION = 100;
	private static final int SUCCESS_BEEP_AMOUNT = 3;
	
	private static final int FAILURE_FREQUENCY = 10000;
	private static final int FAILURE_DURATION = 2500;
	public void PlayTouch(){
		Sound.buzz();
	}
	public void PlaySuccess(){
		for(int i = 0; i < SUCCESS_BEEP_AMOUNT; i++){
			Sound.playTone(SUCCESS_FREQUENCY, SUCCESS_DURATION);
			try {
				Thread.sleep(SUCCESS_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void PlayFailure(){
		Sound.playTone(FAILURE_FREQUENCY, FAILURE_DURATION);
	}
	public void PlayBackup(){
		Sound.beep();
	}
}
