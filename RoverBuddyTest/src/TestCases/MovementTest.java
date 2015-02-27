package TestCases;

import Interfaces.MovementInterface;

public class MovementTest implements MovementInterface{
	CanDetectionTest test;
	
	public MovementTest(CanDetectionTest test){
		this.test = test;
	}

	@Override
	public void Rotate() {
		test.rotates();
	}

	@Override
	public void Stop() {
	}

	@Override
	public void MoveForward() {
	}

	@Override
	public void Backup() {
	}
}
