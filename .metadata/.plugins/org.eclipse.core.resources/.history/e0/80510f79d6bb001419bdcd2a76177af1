package SensorWrappers;

import java.util.HashMap;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import SensorWrapperInterface.UltraSonicInterface;

public class MyUltraSonic extends UltraSonicInterface{
	private static HashMap<Integer, SensorPort> ports = new HashMap<>();
	static{
		ports.put(1, SensorPort.S1);
		ports.put(2, SensorPort.S2);
		ports.put(3, SensorPort.S3);
		ports.put(4, SensorPort.S4);
	}
	
	public UltrasonicSensor sense;
	
	MyUltraSonic(int port) {
		super(port);
		sense = new UltrasonicSensor(ports.get(port));
	}
	
	private void ping(){
		sense.ping();
	}
	
	@Override
	public boolean GetDistance() {
		boolean inRange = false;
		if(sense.getDistance() < 70){
			inRange = true;
		}
		return inRange;
	}

}
