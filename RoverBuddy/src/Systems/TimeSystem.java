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
	private boolean finishThread = false;
	private static final double TIME_LIMIT = 60000.00;
	
	@Override
	public void run(){
		time = 0;
		lastTimeSeen = System.currentTimeMillis();
		while(stillRunning()){
			time += System.currentTimeMillis() - lastTimeSeen;
			lastTimeSeen = System.currentTimeMillis();
		}
		NotifyTimeFinished();
	}
	
	public void Stop(){
		finishThread = true;
	}

	private void NotifyTimeFinished(){
		for(TimeSystemListener listen: listeners){
			listen.NotifyTimeFinished();
		}
	}
	
	public void AddListener(TimeSystemListener listen){
		listeners.add(listen);
	}
	
	private boolean stillRunning(){
		return time < TIME_LIMIT && !finishThread;
	}
	
	public boolean TimeOver(){
		boolean over = false;
		if (time >= TIME_LIMIT){
			over = true;
		}
		return over;
	}
	
	public double timeElapsed(){
		return time/1000;
	}

	public interface TimeSystemListener {
		public void NotifyTimeFinished();
	}
}
