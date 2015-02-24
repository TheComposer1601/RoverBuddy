package Sensors;

import SensorWrappers.MyMovement;
import Interfaces.MovementInterface;

public class MovementSystem implements MovementInterface{
	private MyMovement motor1;
	private MyMovement motor2;
	public void Rotate(){
		motor1.controlMotor(65, 1);
		motor2.controlMotor(65, 2);
	}
	
	public void Stop(){
		motor1.controlMotor(0, 3);
		motor2.controlMotor(0, 3);
	}
	
	public void MoveForward(){
		motor1.controlMotor(85, 1);
		motor2.controlMotor(85, 1);
	}
	
	public void Backup(){
		motor1.controlMotor(65, 2);
		motor2.controlMotor(65, 2);
	}
}
