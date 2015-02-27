package Systems;

import java.util.ArrayList;

import Interfaces.TimeInterface;


/*
Run:
	while TimeSystem has not gone over time and task is incomplete…
		TaskStatus determines if task is complete
	if task was completed notify Rover of success,
	otherwise if task was not completed notify Rover of failure
	
DetermineComplete:
	if Rover’s CanRemovedCount is equal to three then notify task complete,
	otherwise if Rover’s CanRemovedCount is not equal to three then notify task incomplete
	
NotifySuccess:
	Notify all TaskStatusListeners that task was a success
	
NotifyFailure:
	Notify all TaskStatusListeners that task was a failure
	
Start:
	Start TimeSystem
	Run TaskStatus
	
AddListener:
	Add passed in object to list of TaskStatusListeners
 */

public class TaskStatus extends Thread {
	boolean taskComplete = false;
	boolean timeOver = false;
	private ArrayList<TaskStatusListener> listener = new ArrayList<TaskStatusListener>();
	private static final int NUM_CAN_GOAL = 3;
	private int cansRemoved;
	private boolean finished = false;
	
	
	public TaskStatus(){
	}
	
	public boolean isFinished(){
		return taskComplete;
	}
	
	public void outOfTime(){
		timeOver = true;
	}
	
	@Override
	public void run(){
		while(stillRunning()){
			DetermineComplete();
			//System.out.println("Cans Removed: " + cansRemoved);
		}
		if(taskComplete){
			NotifySuccess();
		}
		else{
			NotifyFailure();
		}
	}
	
	private boolean stillRunning(){
		return !timeOver && !taskComplete && !finished;
	}
	
	public void Stop(){
		finished = true;
	}
	
	public boolean objectiveMet() {
		return (cansRemoved == NUM_CAN_GOAL);
	}
	
	public void removedCan(){
		cansRemoved++;
	}
	
	public void DetermineComplete(){
		if(objectiveMet()){
			taskComplete = true;
		}
		else{
			taskComplete = false;
		}
	}
	
	public void NotifySuccess(){
		for(TaskStatusListener listen : listener){
			listen.NotifySuccess();
		}
	}
	
	public void NotifyFailure(){		
		for(TaskStatusListener listen : listener){
			listen.NotifyFailure();
		}
	}
	
	public void AddListener(TaskStatusListener listener){
		this.listener.add(listener);
	}

	public static interface TaskStatusListener {
		public void NotifySuccess();
		public void NotifyFailure();
	}
}
