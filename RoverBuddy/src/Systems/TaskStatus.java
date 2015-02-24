package Systems;

import java.util.ArrayList;

import Interfaces.TimeInterface;
import drivers.RoverBuddy;


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
	TimeInterface timer;
	RoverBuddy buddy;
	private ArrayList<TaskStatusListener> listener = new ArrayList<TaskStatusListener>();
	
	public TaskStatus(TimeInterface timer, RoverBuddy buddy){
		this.timer = timer;
		this.buddy = buddy;
	}
	
	public boolean isFinished(){
		return taskComplete;
	}
	
	@Override
	public void run(){
		while(!timer.TimeOver() && !taskComplete){
			DetermineComplete();
		}
		if(taskComplete){
			NotifySuccess();
		}
		else{
			NotifyFailure();
		}
	}
	
	public void DetermineComplete(){
		if(buddy.objectiveMet()){
			taskComplete = true;
		}
		else if(timer.TimeOver()){
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
