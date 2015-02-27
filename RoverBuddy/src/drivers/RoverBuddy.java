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
import States.State;
import Systems.CanDetection;
import Systems.CanDetection.CanDetectionListener;
import Systems.CanRemoval;
import Systems.CanRemoval.CanRemovalListener;
import Systems.TaskStatus;
import Systems.TaskStatus.TaskStatusListener;
import Systems.TimeSystem;
import Systems.TimeSystem.TimeSystemListener;

public class RoverBuddy {
	private TaskStatus task;
	private TimeSystem timer;
	private CanDetection canDet;
	private static final long BACKUP_TIME = 3000;
	private static final long BACKUP_BEEP_INTERVAL = 500;
	private static final long ESCAPE_TIME = 6000;
	private static final long WAIT_TIME = 3000;
	
	private VisionSystem vision;
	private TouchSystem touch;
	private MovementSystem move;
	private LightSystem light;
	private DisplaySystem display;
	private SoundSystem sound;
	private CanRemoval canRemove;
	private State currentState = State.DETECTING;
	
	private TaskStatusListener taskListen = new TaskStatusListener(){
		@Override
		public void NotifySuccess() {
			currentState = State.SUCCESS;
		}

		@Override
		public void NotifyFailure() {
			currentState = State.FAILURE;
		}
	};
	
	private CanDetectionListener canDetListen = new CanDetectionListener(){
		@Override
		public void NotifyDetected() {
			currentState = State.DETECTED;
		}
	};
	
	private CanRemovalListener removeListen = new CanRemovalListener(){
		@Override
		public void NotifyFinishedAndRemoved() {
			currentState = State.REMOVED;
		}

		@Override
		public void NotifyFinishedNotRemoved() {
			currentState = State.NOT_REMOVED;
		}
		@Override
		public void NotifyCanTouching(){
			currentState = State.TOUCHING;
		}
		@Override
		public void NotifyCanNotTouching(){
			currentState = State.DETECTING;
		}
	};
	
	public TimeSystemListener timeListen = new TimeSystemListener(){
		@Override
		public void NotifyTimeFinished() {
			currentState = State.TIME_OVER;
		}
	};
	
	
	
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
		
		task = new TaskStatus();
		task.AddListener(taskListen);
		task.start();

		canRemove = new CanRemoval(move, light, touch);
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
			switch(currentState){
			case DETECTING:
				break;
			case DETECTED:
				CanDetected();
				break;
			case REMOVING:
				break;
			case REMOVED:
				CanRemoved();
				break;
			case NOT_REMOVED:
				CanNotRemoved();
				break;
			case TOUCHING:
				CanTouching();
				break;
			case TIME_OVER:
				TimeFinished();
				break;
			case SUCCESS:
				try {
					OutputSuccess();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				EndAllThreads();
				break;
			case FAILURE:
				try {
					OutputFailure();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				EndAllThreads();
				break;
			}
		}
		EndAllThreads();
	}
	
	public void OutputSuccess() throws InterruptedException{
		canDet.pause();
		canRemove.pause();
		sound.PlaySuccess();
		display.Display("Finished in " + timer.timeElapsed() + " seconds");
		move.MoveForward();	
		Thread.sleep(ESCAPE_TIME);
		move.Stop();
		Thread.sleep(WAIT_TIME);
		finished = true;
	}
	
	public void OutputFailure() throws InterruptedException{
		sound.PlayFailure();
		move.Stop();
		display.Display("We failed. Sorry :(");
		Thread.sleep(WAIT_TIME);
		finished = true;
	}
	
	public void CanDetected(){
		currentState = State.REMOVING;
		System.out.println("Found Can");
		canDet.pause();
		System.out.println("Remove starting");
		canRemove.resume();	
	}
	
	public void CanRemoved(){
		currentState = State.DETECTING;
		canRemove.pause();
		task.removedCan();
		BackUp();
		canDet.resumeMyThread();
		
	}
	
	public void CanNotRemoved(){
		currentState = State.DETECTING;
		canRemove.pause();
		BackUp();
		canDet.resumeMyThread();		
	}
	
	public void BackUp(){
		try{
			move.Backup();
			double time = 0;
			double currentTime = System.currentTimeMillis();
			while(time < BACKUP_TIME){
				sound.PlayBackup();
				Thread.sleep(BACKUP_BEEP_INTERVAL);
				time += System.currentTimeMillis() - currentTime;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void CanTouching(){
		sound.PlayTouch();
	}
	
	public void TimeFinished(){
		task.outOfTime();
		canDet.pause();
		canRemove.pause();
	}
	
	private void EndAllThreads(){
		canDet.Stop();
		canRemove.Stop();
		timer.Stop();
		task.Stop();
	}
}
