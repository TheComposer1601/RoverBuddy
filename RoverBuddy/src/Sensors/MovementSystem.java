package Sensors;

import Interfaces.MovementInterface;
import SensorWrapperInterface.MovementLejosInterface;

public class MovementSystem implements MovementInterface{
	private MovementLejosInterface motor1;
	private MovementLejosInterface motor2;
	private static final int STOP_SPEED = 0;
	private static final int ROTATE_SPEED = 65;
	private static final int FORWARD_SPEED = 80;
	private static final int BACKUP_SPEED = 90;
	
	private static final int FORWARD_MODE = 1;
	private static final int BACKWARD_MODE = 2;
	private static final int STOP_MODE = 3;
	public MovementSystem(MovementLejosInterface m1, MovementLejosInterface m2){
		motor1 = m1;
		motor2 = m2;
	}
	public void Rotate(){
		motor1.controlMotor(ROTATE_SPEED, FORWARD_MODE);
		motor2.controlMotor(ROTATE_SPEED, BACKWARD_MODE);
	}
	
	public void Stop(){
		motor1.controlMotor(STOP_SPEED, STOP_MODE);
		motor2.controlMotor(STOP_SPEED, STOP_MODE);
	}
	
	public void MoveForward(){
		motor1.controlMotor(FORWARD_SPEED, FORWARD_MODE);
		motor2.controlMotor(FORWARD_SPEED, FORWARD_MODE);
	}
	
	public void Backup(){
		motor1.controlMotor(BACKUP_SPEED, BACKWARD_MODE);
		motor2.controlMotor(BACKUP_SPEED, BACKWARD_MODE);
	}
}
