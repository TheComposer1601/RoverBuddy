package SensorWrappers;

import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import SensorWrapperInterface.TouchSensorInterface;

public class MyTouch implements TouchSensorInterface {
	private TouchSensor sensor;
	public MyTouch(SensorPort port){
		sensor = new TouchSensor(port);
	}
	@Override
	public boolean isTouched() {
		return sensor.isPressed();
	}
}
