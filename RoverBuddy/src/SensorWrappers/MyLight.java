package SensorWrappers;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import SensorWrapperInterface.LightSensorInterface;

public class MyLight implements LightSensorInterface{
	private LightSensor sensor;
	public MyLight(SensorPort port){
		sensor = new LightSensor(port);
		sensor.setHigh(500);
	}
	@Override
	public int readValue() {
		return sensor.readValue();
	}
}
