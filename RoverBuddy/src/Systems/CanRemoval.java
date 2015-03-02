package Systems;

import java.util.ArrayList;

import lejos.util.Delay;
import Interfaces.LightInterface;
import Interfaces.MovementInterface;
import Interfaces.TouchInterface;
import Sensors.SoundSystem;

/*
 RemoveCan:
 MovementSystem moves forward
 While LightSystem determines that RoverBuddy is still in bounds...
 if TouchDetection detects a touch and TouchDetection has not detected a touch yet then store that a can was touched
 MovementSystem stops if TouchDetection detected can notify Rover that can was removed

 NotifyRemoved:
 Notify all CanRemovalListeners that can was removed.
 */
public class CanRemoval extends Thread {
	private boolean paused;
	private MovementInterface move;
	private LightInterface light;
	private TouchInterface touch;
	private ArrayList<CanRemovalListener> listener = new ArrayList<>();
	private boolean finished = false;
	private static final int EXTRA_PUSH_TIME = 250;
	private static final int NOT_PUSHED_COUNTDOWN = 1000;

	public CanRemoval(MovementInterface move, LightInterface light,
			TouchInterface touch) throws NullPointerException{
		if(NullSystem(move, light, touch)){
			throw new NullPointerException();
		}
		this.move = move;
		this.light = light;
		this.touch = touch;
		paused = true;
	}

	private boolean NullSystem(MovementInterface move, LightInterface light,
			TouchInterface touch) {
		return move == null || light == null || touch == null;
	}

	public void RemoveCan() {
		boolean foundCan = false;
		move.MoveForward();
		int countdown = 0;
		while (light.InBounds()) {
			if (touch.DetectTouch()) {
				NotifyTouching();
				if(countdown != 0){
					countdown = 0;
				}
				if (!foundCan) {
					foundCan = true;
				}
			} else {
				NotifyNotTouching();
				countdown++;
				if(countdown > NOT_PUSHED_COUNTDOWN){
					foundCan = false;
					countdown = 0;
				}
			}
		}
		if (foundCan) {
			try {
				Thread.sleep(EXTRA_PUSH_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			NotifyRemoved();
		} else {
			NotifyFailed();
		}
		this.pause();
	}

	private void NotifyFailed() {
		for (CanRemovalListener listen : listener) {
			listen.NotifyFinishedNotRemoved();
		}
	}

	public void NotifyRemoved() {
		for (CanRemovalListener listen : listener) {
			listen.NotifyFinishedAndRemoved();
		}
	}

	public void NotifyTouching() {
		for (CanRemovalListener listen : listener) {
			listen.NotifyCanTouching();
		}
	}

	public void NotifyNotTouching() {
		for (CanRemovalListener listen : listener) {
			listen.NotifyCanNotTouching();
		}
	}

	@Override
	public void run() {
		while (!finished) {
			if (paused)
				Thread.yield();
			else {
				RemoveCan();
			}
		}
	}

	public void addListener(CanRemovalListener listen) {
		if(listen != null) listener.add(listen);
		else throw new NullPointerException();
	}

	public interface CanRemovalListener {
		public void NotifyFinishedAndRemoved();

		public void NotifyFinishedNotRemoved();

		public void NotifyCanTouching();

		public void NotifyCanNotTouching();
	}

	public void pause() {
		paused = true;
	}

	public void resumeThread() {
		paused = false;
	}

	public void Stop() {
		finished = true;
	}
}
