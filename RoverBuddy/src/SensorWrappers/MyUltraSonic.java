package SensorWrappers;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import SensorWrapperInterface.UltraSonicInterface;
import Sensors.DisplaySystem;

public class MyUltraSonic implements UltraSonicInterface{
	public UltrasonicSensor sense;
	private static final int MAX_DISTANCE = 70;
	public MyUltraSonic(SensorPort port) {
		sense = new UltrasonicSensor(port);
		sense.continuous();
	}
	
	public void ping(){
		sense.ping();
	}
	
	@Override
	public boolean GetDistance() {
		boolean inRange = false;
		this.ping();
		int distance = sense.getDistance();
		System.out.println(distance);
		if(distance < MAX_DISTANCE){
			inRange = true;
		}
		return inRange;
	}
}
