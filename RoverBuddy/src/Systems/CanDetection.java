package Systems;

import java.util.HashSet;
import java.util.Set;

import Interfaces.MovementInterface;
import Interfaces.VisionInterface;

/*
 * DetectCan:
 * if VisionSystem detects can in front
 * CanDectionSystem notifys detected
 * Pause thread till CanDetected
 * 
 * Resume
 * runs the thread
 * 
 * NotifyDetected:
 * Notify all CanDetectListeners that can was detected
 * 
 * Run:
 * Set can count to 0
 * Motor System rotates rover
 * while can count is less than 3
 * Detect Can
 * 
 * AddListener:
 * Add passed in object to list of CanDetectionListeners
 */
public class CanDetection extends Thread {
	VisionInterface vision;
	MovementInterface motor;
	Set<CanDetectionListener> listeners = new HashSet<>();
	int canCount = 0;
	
	public CanDetection(VisionInterface vision, MovementInterface motor){
		this.vision = vision;
		this.motor = motor;
	}
	
	public void DetectCan(){
		if(vision.DetectCan()){
			canCount++;
			NotifyDetected();
			this.pause();
		}
	}
	
	public void NotifyDetected(){
		for(CanDetectionListener listen : listeners){
			listen.NotifyDetected();
		}
	}
	
	public void pause(){
		while(true){
			Thread.yield();
		}
	}
	
	public void resume(){
		this.run();
	}

	@Override
	public void run(){
		motor.Rotate();
		while(canCount < 3){
			DetectCan();
		}
	}
	
	public void AddListener(CanDetectionListener listen){
		listeners.add(listen);
	}

	public interface CanDetectionListener {
	
		public void NotifyDetected();
		
	}
}
