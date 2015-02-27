package Sensors;

import Interfaces.MovementInterface;

public class EmptyMotorSystem implements MovementInterface {

	@Override
	public void Rotate() { }

	@Override
	public void Stop() { }

	@Override
	public void MoveForward() { }

	@Override
	public void Backup() { }

}
