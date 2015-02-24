package SensorWrappers;

import lejos.nxt.MotorPort;
import SensorWrapperInterface.MovementLejosInterface;

public class MyMovement implements MovementLejosInterface{
	private MotorPort motor;
	public MyMovement(MotorPort port){
		motor = port;
	}
	@Override
	public void controlMotor(int power, int mode) {
		motor.controlMotor(power, mode);
	}
}
