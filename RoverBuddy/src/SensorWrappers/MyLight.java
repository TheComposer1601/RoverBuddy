package SensorWrappers;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import SensorWrapperInterface.LightSensorInterface;

public class MyLight implements LightSensorInterface{
	private LightSensor sensor;
	private static final int SENSOR_HIGH = 500;
	public MyLight(SensorPort port){
		sensor = new LightSensor(port);
		sensor.setHigh(SENSOR_HIGH);
	}
	@Override
	public int readValue() {
		return sensor.readValue();
	}
}
