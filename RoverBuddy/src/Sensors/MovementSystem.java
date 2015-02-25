package Sensors;

import Interfaces.MovementInterface;
import SensorWrapperInterface.MovementLejosInterface;

public class MovementSystem implements MovementInterface{
	private MovementLejosInterface motor1;
	private MovementLejosInterface motor2;
	public MovementSystem(MovementLejosInterface m1, MovementLejosInterface m2){
		motor1 = m1;
		motor2 = m2;
	}
	public void Rotate(){
		motor1.controlMotor(65, 1);
		motor2.controlMotor(65, 2);
	}
	
	public void Stop(){
		motor1.controlMotor(0, 3);
		motor2.controlMotor(0, 3);
	}
	
	public void MoveForward(){
		motor1.controlMotor(80, 1);
		motor2.controlMotor(80, 1);
	}
	
	public void Backup(){
		motor1.controlMotor(90, 2);
		motor2.controlMotor(90, 2);
	}
}
