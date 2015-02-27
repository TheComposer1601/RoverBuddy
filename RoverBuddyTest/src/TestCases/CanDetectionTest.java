package TestCases;

import static org.junit.Assert.*;

import org.junit.Test;

import Interfaces.MovementInterface;
import Interfaces.VisionInterface;
import Sensors.VisionSystem;
import Systems.CanDetection;
import Systems.CanDetection.CanDetectionListener;

public class CanDetectionTest {
	MovementInterface move = new MovementTest(this);
	VisionInterface vision = new VisionSystem(new UltraSonicTrueTest());
	CanDetection canDet;
	private boolean foundCan;
	private boolean foundRotate;
	CanDetectionListener listen = new CanDetectionListener(){
		@Override
		public void NotifyDetected() {
			foundCan = true;
		}
	};

	@Test
	public void testDetectCan() throws InterruptedException {
		canDet = new CanDetection(vision, move);
		foundCan = false;
		canDet.AddListener(listen);
		canDet.start();
		while(!foundCan){
		}
		canDet.Stop();
		assertTrue(foundCan);
	}

	@Test
	public void testPause() throws InterruptedException{
		canDet = new CanDetection(vision, move);
		foundCan = false;
		canDet.AddListener(listen);
		canDet.pause();
		canDet.start();
		canDet.Stop();
		assertFalse(foundCan);
	}

	@Test
	public void testResume() throws InterruptedException{
		canDet = new CanDetection(vision, move);
		foundCan = false;
		
		canDet.AddListener(listen);
		canDet.pause();
		canDet.start();
		assertFalse(foundCan);
		canDet.resumeMyThread();
		while(!foundCan){}
		canDet.Stop();
		assertTrue(foundCan);
	}

	@Test
	public void testRun() throws InterruptedException{
		canDet = new CanDetection(vision, move);
		foundCan = false;
		foundRotate = false;
		canDet.AddListener(listen);
		canDet.start();
		while(!foundCan){}
		canDet.Stop();
		assertTrue(foundRotate);
	}

	@Test
	public void testStop() throws InterruptedException{
		canDet = new CanDetection(vision, move);
		foundCan = false;
		foundRotate = false;
		canDet.AddListener(listen);
		canDet.start();
		while(!foundCan){}
		canDet.Stop();
		foundCan = false;
		Thread.yield();
		assertFalse(foundCan);
	}

	@Test
	public void testAddListener(){
		canDet = new CanDetection(vision, move);
		foundCan = false;
		foundRotate = false;
		canDet.AddListener(new CanDetectionListener(){
			@Override
			public void NotifyDetected() {
				foundCan = true;
			}
		});
		canDet.start();
		while(!foundCan){ }
		assertTrue(foundCan);
	}
	
	@Test
	public void testNullVisionAndMovement(){
		try{
			//Vision and Movement
			canDet = new CanDetection(null, move);
			canDet.AddListener(listen);
			canDet.start();
			foundCan = true;
			while(!foundCan){}
			canDet.Stop();

			canDet = new CanDetection(vision, null);
			canDet.AddListener(listen);
			canDet.start();
			foundCan = true;
			while(!foundCan){}
			canDet.Stop();
			
			canDet = new CanDetection(null, null);
			canDet.AddListener(listen);
			canDet.start();
			foundCan = true;
			while(!foundCan){}
			canDet.Stop();


			fail();
		} catch(NullPointerException e){
			assertTrue(true);
		}
	}
	
	@Test
	public void testNullListener(){
		try{
			canDet = new CanDetection(vision, move);
			canDet.AddListener(null);
			canDet.start();
			foundCan = true;
			while(!foundCan);
			canDet.Stop();
			fail();
		} catch(NullPointerException e){
			assertTrue(true);
		}
		
		try{
			canDet = new CanDetection(null, null);
			canDet.AddListener(null);
			canDet.start();
			foundCan = true;
			while(!foundCan);
			canDet.Stop();
			fail();
		} catch(NullPointerException e){
			assertTrue(true);
		}
	}
	
	public void rotates() {
		foundRotate = true;
	}
}
