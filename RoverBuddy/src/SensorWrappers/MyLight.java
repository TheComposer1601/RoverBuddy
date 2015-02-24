package SensorWrappers;

import java.util.HashMap;

import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import SensorWrapperInterface.LightSensorInterface;

public class MyLight implements LightSensorInterface{
	private static HashMap<Integer, SensorPort> ports = new HashMap<>();
	static{
		ports.put(1, SensorPort.S1);
		ports.put(2, SensorPort.S2);
		ports.put(3, SensorPort.S3);
		ports.put(4, SensorPort.S4);
	}
	private LightSensor sensor;
	public MyLight(int port){
		sensor = new LightSensor(ports.get(port));
	}
	@Override
	public int readValue() {
		return sensor.readValue();
	}
}
