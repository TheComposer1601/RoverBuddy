package Systems;

import java.util.ArrayList;
import java.util.List;

import Interfaces.TimeInterface;


/*
 * Start:
 * Run TimeSystem
 * 
 * Run:
 * while time is less than one minute
 * increment time
 * Time system finishes
 * 
 * Finish
 * notify all listeners
 * 
 * AddListeners:
 * add TimeSystemListener to listeners
 * 
 * TimeOver
 * if Time is over time limit return true
 * else if time is under time limit return false
 * 
 * TimeElapsed
 * Return time currently elapsed
 */
public class TimeSystem extends Thread implements TimeInterface{
	private double time;
	private double lastTimeSeen;
	private List<TimeSystemListener> listeners = new ArrayList<>();
	private double timeLimit = 1.00;
	
	@Override
	public void run(){
		time = 0;
		lastTimeSeen = System.currentTimeMillis();
		while(time < 1){
			time = System.currentTimeMillis() - lastTimeSeen;
			lastTimeSeen = System.currentTimeMillis();
		}
		
		FinishTime();
	}
	
	public void FinishTime(){
		for(TimeSystemListener listen: listeners){
			listen.NotifyTimeFinished();
		}
	}
	
	public void AddListener(TimeSystemListener listen){
		listeners.add(listen);
	}
	
	public boolean TimeOver(){
		boolean over = false;
		if (time >= timeLimit){
			over = true;
		}
		return over;
	}
	
	public double timeElapsed(){
		return time;
	}

	public interface TimeSystemListener {
		public double NotifyTimeFinished();
	}
}
