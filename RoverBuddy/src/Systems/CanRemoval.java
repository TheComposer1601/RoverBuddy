package Systems;

public class CanRemoval extends Thread {
	
	public void RemoveCan(){
		
	}
	
	public void NotifyRemoved(){
		
	}
	
	@Override
	public void run(){
		
	}

	public interface CanRemovalListener {

		public void NotifyFinishedAndRemoved();
		public void NotifyFinishedNotRemoved();
		
	}
}
