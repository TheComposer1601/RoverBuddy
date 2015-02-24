package drivers;

import lejos.nxt.MotorPort;
import lejos.nxt.SensorPort;
import SensorWrappers.MyLight;
import SensorWrappers.MyMovement;
import SensorWrappers.MyTouch;
import SensorWrappers.MyUltraSonic;
import Sensors.DisplaySystem;
import Sensors.LightSystem;
import Sensors.MovementSystem;
import Sensors.SoundSystem;
import Sensors.TouchSystem;
import Sensors.VisionSystem;
import Systems.CanDetection;
import Systems.CanDetection.CanDetectionListener;
import Systems.CanRemoval;
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
			this.OutputSuccess();
			finished = true;
		}
		
		public void OutputSuccess(){
			display.Display("Finished in " + timer.timeElapsed() + " seconds");
			move.MoveForward();
			canDet.pause();
			canRemove.pause();
			sound.PlaySuccess();		
		}

		@Override
		public void NotifyFailure() {
			display.Display("We failed. Sorry :(");
			sound.PlayFailure();
			finished = true;
		}
	};
	
	private CanDetectionListener canDetListen = new CanDetectionListener(){

		@Override
		public void NotifyDetected() {
			canDet.pause();
			canRemove.resume();
		}
		
	};
	
	private CanRemovalListener removeListen = new CanRemovalListener(){

		@Override
		public void NotifyFinishedAndRemoved() {
			canRemove.pause();
			canDet.resume();
			cansRemoved ++;
		}

		@Override
		public void NotifyFinishedNotRemoved() {
			canRemove.pause();
			canDet.resume();
		}
		
	};
	
	public TimeSystemListener timeListen = new TimeSystemListener(){
		@Override
		public double NotifyTimeFinished() {
			canDet.pause();
			canRemove.pause();
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
	
	private VisionSystem vision;
	private TouchSystem touch;
	private MovementSystem move;
	private LightSystem light;
	private DisplaySystem display;
	private SoundSystem sound;
	private CanRemoval canRemove;
	
	public RoverBuddy(){
		vision = new VisionSystem(new MyUltraSonic(SensorPort.S3));
		move = new MovementSystem(new MyMovement(MotorPort.A), new MyMovement(MotorPort.B));
		light = new LightSystem(new MyLight(SensorPort.S4));
		touch = new TouchSystem(new MyTouch(SensorPort.S1), new MyTouch(SensorPort.S2));
		display = new DisplaySystem();
		sound = new SoundSystem();
		
		timer = new TimeSystem();
		timer.AddListener(timeListen);
		timer.start();
		
		task = new TaskStatus(timer, this);
		task.AddListener(taskListen);
		task.start();

		canRemove = new CanRemoval(move, light, sound, touch);
		canRemove.addListener(removeListen);
		canRemove.start();
		canRemove.pause();
		
		canDet = new CanDetection(vision, move);
		canDet.AddListener(canDetListen);
		canDet.start();

		
	}

	private boolean finished = false;
	public void run(){
		while(!finished){
			Thread.yield();
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public int canRemovedCount(){
		return cansRemoved;
	}
	
	public void RemovedCan(){
		cansRemoved++;
	}
	
	public boolean objectiveMet() {
		return (canRemovedCount() == 3);
	}
	
	
}
