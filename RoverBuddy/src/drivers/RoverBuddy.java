package drivers;

import SensorWrappers.MyUltraSonic;
import Sensors.DisplaySystem;
import Sensors.LightSystem;
import Sensors.MovementSystem;
import Sensors.SoundSystem;
import Sensors.TouchSystem;
import Sensors.VisionSystem;
import Systems.CanDetection;
import Systems.CanDetection.CanDetectionListener;
import Systems.CanRemoval.CanRemovalListener;
import Systems.TaskStatus;
import Systems.TaskStatus.TaskStatusListener;
import Systems.TimeSystem;
import Systems.TimeSystem.TimeSystemListener;

public class RoverBuddy {
	
	private int cansRemoved;
	TaskStatus task;
	TimeSystem timer;
	CanDetection canDet;
	
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
	
	/*
	 * Setup:
	 * Start CanDetection System Thread
	 * Add RoverBuddy�s CanDetectionListener to CanDetection�s Listeners
	 * Start TaskStatus System Thread
	 * Add RoverBuddy�s TaskStatusListener to TaskStatus� Listeners
	 * Start Time System thread
	 * Add RoverBuddy to Time Systems Listeners
	 * Run:
	 * While TaskStatus System is still running�
	 * Wait
	 */
	

/*
 * Port 1 & 2 touch
 * Port 3 sonic, sight
 * port 4 light
 */
	
	VisionSystem vision;
	TouchSystem touch;
	MovementSystem move;
	LightSystem light;
	DisplaySystem display;
	SoundSystem sound;
	
	public RoverBuddy(){
		vision = new VisionSystem(new MyUltraSonic(3));
		move = new MovementSystem();
		timer = new TimeSystem();
		task = new TaskStatus(timer, this);
		task.AddListener(taskListen);
		timer.start();
		task.start();
		canDet = new CanDetection(vision, move);
	}
	
	public void run(){
		boolean finished = false;
		while(!finished){
			Thread.yield();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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


	public boolean objectiveMet() {
		return (canRemovedCount() == 3);
	}
	
	
}
