package CanRemovalTestCases;

import static org.junit.Assert.*;
import Systems.CanRemoval;
import Systems.CanRemoval.CanRemovalListener;

import org.junit.Test;

public class CanRemovalTest {
	private MoveTestClass move;
	private LightTestClass light;
	private TouchTestClass touch;
	private boolean canRemoved;
	private boolean canTouched;
	private boolean finished;
	private static final int THREAD_WAIT_TIME = 10;
	private CanRemovalListener listener = new CanRemovalListener(){
		@Override
		public void NotifyFinishedAndRemoved() {
			canRemoved = true;
			finished = true;
		}
		@Override
		public void NotifyFinishedNotRemoved() {
			canRemoved = false;
			finished = true;
		}
		@Override
		public void NotifyCanTouching() {
			canTouched = true;
		}
		@Override
		public void NotifyCanNotTouching() {
			canTouched = false;
		}
	};
	@Test
	public void canRemoveTest() {
		finished = false;
		move = new MoveTestClass();
		light = new LightTestClass();
		touch = new TouchTestClass();
		CanRemoval canRem = new CanRemoval(move, light, touch);
		assertFalse(canRemoved);
		canRem.addListener(listener);
		canRem.start();
		canRem.resumeThread();
		touch.setTouch(true);
		pause();
		light.setInBounds(false);		
		while(!finished){
			pause();
		}
		canRem.Stop();
		assertTrue(canRemoved);
	}
	@Test
	public void canNotRemoveTest(){
		finished = false;
		canRemoved = true;
		move = new MoveTestClass();
		light = new LightTestClass();
		touch = new TouchTestClass();
		assertTrue(canRemoved);
		CanRemoval canRem = new CanRemoval(move, light, touch);
		canRem.addListener(listener);
		canRem.start();
		canRem.resumeThread();
		touch.setTouch(false);
		pause();
		light.setInBounds(false);	
		while(!finished){
			pause();
		}
		canRem.Stop();
		assertFalse(canRemoved);
	}
	@Test
	public void canTouchTest(){
		move = new MoveTestClass();
		light = new LightTestClass();
		touch = new TouchTestClass();
		assertFalse(canTouched);
		CanRemoval canRem = new CanRemoval(move, light, touch);
		canRem.addListener(listener);
		canRem.start();
		canRem.resumeThread();
		touch.setTouch(true);
		pause();
		assertTrue(canTouched);
	}
	@Test
	public void canNotTouchTest(){
		canTouched = true;
		move = new MoveTestClass();
		light = new LightTestClass();
		touch = new TouchTestClass();
		assertTrue(canTouched);
		CanRemoval canRem = new CanRemoval(move, light, touch);
		canRem.addListener(listener);
		canRem.start();
		canRem.resumeThread();
		touch.setTouch(false);
		pause();
		assertFalse(canTouched);
	}
	@Test
	public void nullListenerTest(){
		move = new MoveTestClass();
		light = new LightTestClass();
		touch = new TouchTestClass();
		CanRemoval canRem = new CanRemoval(move, light, touch);
		try{
			canRem.addListener(null);
			fail();
		} catch(NullPointerException e){
			assertTrue(true);
		}
	}
	@Test
	public void nullSystemTest(){
		move = new MoveTestClass();
		light = new LightTestClass();
		touch = new TouchTestClass();
		try{
		CanRemoval canRem = new CanRemoval(null, null, null);
		fail();
		} catch(NullPointerException e){
			assertTrue(true);
		}
	}
	public void pause(){
		try {
			Thread.sleep(THREAD_WAIT_TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
