package Systems;

import java.util.ArrayList;

import lejos.util.Delay;
import Interfaces.LightInterface;
import Interfaces.MovementInterface;
import Interfaces.TouchInterface;
import Sensors.SoundSystem;

/*
RemoveCan:
	MovementSystem moves forward
	While LightSystem determines that RoverBuddy is still in bounds...
		if TouchDetection detects a touch and TouchDetection has not detected a touch yet then store that a can was touched
	MovementSystem stops if TouchDetection detected can notify Rover that can was removed

NotifyRemoved:
	Notify all CanRemovalListeners that can was removed.
 */
public class CanRemoval extends Thread {
	private boolean paused;
	private MovementInterface move;
	private LightInterface light;
	private SoundSystem sound;
	private TouchInterface touch;
	private ArrayList<CanRemovalListener> listener = new ArrayList<>();
	private boolean finished = false;
	private boolean foundCan = false;
	
	public CanRemoval(MovementInterface move, LightInterface light, SoundSystem sound, TouchInterface touch){
		this.move = move;
		this.light = light;
		this.sound = sound;
		this.touch = touch;
		paused = true;
	}
	
	//TODO find out if InterruptedException is bad or not.
	//MEH
	public void RemoveCan() throws InterruptedException{
		move.MoveForward();
		while(light.InBounds()){
			if(touch.DetectTouch()){
				sound.PlayTouch();
				if(!foundCan){
					System.out.println("FoundCan true");
					foundCan = true;
				}
			}
		}
		this.NotifyStartBackup();
	}
	
	private void NotifyStartBackup(){
		for(CanRemovalListener listen : listener){
			listen.NotifyBackup(foundCan);
		}
		this.pause();
	}
	
	private void NotifyFailed(){
		for(CanRemovalListener listen: listener){
			listen.NotifyFinishedNotRemoved();
		}
		this.pause();
	}
	
	public void NotifyRemoved(){
		for(CanRemovalListener listen: listener){
			listen.NotifyFinishedAndRemoved();
		}
		this.pause();
	}
	
	@Override
	public void run(){
		while(!finished){
			if(paused)
				Thread.yield();
			else{
				try {
					RemoveCan();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				paused = true;
			}
		}
	}
	
	public void addListener(CanRemovalListener listen){
		this.listener.add(listen);
	}

	public interface CanRemovalListener {
		public void NotifyFinishedAndRemoved();
		public void NotifyBackup(boolean foundCan);
		public void NotifyFinishedNotRemoved();
	}

	public void pause() {
		paused = true;
	}
	
	public void resume(){
		paused = false;
	}
	
	public void Stop(){
		finished = true;
	}

	public void finishBackup() {
		if(foundCan){
			foundCan = false;
			System.out.println("Removed");
			NotifyRemoved();
		}
		else{
			System.out.println("Failed");
			NotifyFailed();
		}
	}
}
