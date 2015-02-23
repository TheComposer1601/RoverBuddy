package drivers;

import Systems.CanDetection.CanDetectionListener;
import Systems.CanRemoval.CanRemovalListener;
import Systems.TaskStatus;
import Systems.TaskStatus.TaskStatusListener;
import Systems.TimeSystem;
import Systems.TimeSystem.TimeSystemListener;

/*
 * Port 1 & 2 touch
 * Port 3 sonic, sight
 * port 4 light
 */
public class RoverBuddy {
	
	private int cansRemoved;
	private TaskStatusListener taskListen = new TaskStatusListener(){

		@Override
		public void NotifySuccess() {
			
		}

		@Override
		public void NotifyFailure() {
			
		}
		
	};
	
	private CanDetectionListener canDetListen = new CanDetectionListener(){

		@Override
		public void NotifyDetected() {
			
		}
		
	};
	
	private CanRemovalListener removeListen = new CanRemovalListener(){

		@Override
		public void NotifyFinishedAndRemoved() {
			
		}

		@Override
		public void NotifyFinishedNotRemoved() {
			
		}
		
	};
	
	public TimeSystemListener timeListen = new TimeSystemListener(){
		@Override
		public double NotifyTimeFinished() {
			return 0;
		}
	};
	
	public RoverBuddy(){
		TimeSystem timer = new TimeSystem();
		TaskStatus task = new TaskStatus(timer, this);
		task.AddListener(taskListen);
		timer.start();
		task.start();
	}
	
	public int canRemovedCount(){
		return cansRemoved;
	}
	
	public void RemovedCan(){
		cansRemoved++;
	}
	
	public void Setup(){
		
	}
	
	public void OutputSuccess(){
		
	}
	
	public void OutputFailure(){
		
	}
	
	public void Start(){
		
	}
	
	public void run(){
		
	}
	
	public void NotifySuccess(){
		
	}
	
	public void NotifyFailure(){
		
	}
	
	public void NotifyDetected(){
		
	}
	
	public void NotifyFinishedAndRemoved(){
		
	}
	
	public void NotifyFinishedNotRemoved(){
		
	}
	
	
}
