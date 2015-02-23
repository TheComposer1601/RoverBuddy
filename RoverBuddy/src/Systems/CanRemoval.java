package Systems;

import java.util.ArrayList;

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
	MovementInterface move;
	LightInterface light;
	SoundSystem sound;
	TouchInterface touch;
	private ArrayList<CanRemovalListener> listener = new ArrayList<>();
	
	public CanRemoval(MovementInterface move, LightInterface light, SoundSystem sound, TouchInterface touch){
		this.move = move;
		this.light = light;
		this.sound = sound;
		this.touch = touch;
	}
	
	//TODO find out if InterruptedException is bad or not.
	public void RemoveCan() throws InterruptedException{
		move.MoveForward();
		boolean foundCan = false;
		while(light.InBounds()){
			if(!foundCan && touch.DetectTouch()){
				foundCan = true;
			}
		}
		move.Backup();
		Thread.sleep(1000L);
		move.Stop();
		if(foundCan)
			NotifyRemoved();
	}
	
	public void NotifyRemoved(){
		for(CanRemovalListener listen: listener){
			listen.NotifyFinishedAndRemoved();
		}
	}
	
	@Override
	public void run(){
		try {
			RemoveCan();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addListener(CanRemovalListener listen){
		this.listener.add(listen);
	}

	public interface CanRemovalListener {
		public void NotifyFinishedAndRemoved();
		public void NotifyFinishedNotRemoved();
	}
}
