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
	private TouchInterface touch;
	private ArrayList<CanRemovalListener> listener = new ArrayList<>();
	private boolean finished = false;
	
	public CanRemoval(MovementInterface move, LightInterface light, TouchInterface touch){
		this.move = move;
		this.light = light;
		this.touch = touch;
		paused = true;
	}
	
	//TODO find out if InterruptedException is bad or not.
	//MEH
	public void RemoveCan() throws InterruptedException{
		boolean foundCan = false;
		move.MoveForward();
		while(light.InBounds()){
			if(touch.DetectTouch()){
				NotifyTouching();
				if(!foundCan){
					foundCan = true;
				}
			}
			else{
				NotifyNotTouching();
			}
		}
		if(foundCan){
			NotifyRemoved();
		}
		else{
			NotifyFailed();
		}
		this.pause();
	}

	
	private void NotifyFailed(){
		for(CanRemovalListener listen: listener){
			listen.NotifyFinishedNotRemoved();
		}
	}
	
	public void NotifyRemoved(){
		for(CanRemovalListener listen: listener){
			listen.NotifyFinishedAndRemoved();
		}
	}
	
	public void NotifyTouching(){
		for(CanRemovalListener listen: listener){
			listen.NotifyCanTouching();
		}
	}
	
	public void NotifyNotTouching(){
		for(CanRemovalListener listen: listener){
			listen.NotifyCanNotTouching();
		}
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
			}
		}
	}
	
	public void addListener(CanRemovalListener listen){
		this.listener.add(listen);
	}

	public interface CanRemovalListener {
		public void NotifyFinishedAndRemoved();
		public void NotifyFinishedNotRemoved();
		public void NotifyCanTouching();
		public void NotifyCanNotTouching();
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
}
