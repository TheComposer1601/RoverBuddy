package SensorWrappers;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import SensorWrapperInterface.UltraSonicInterface;
import Sensors.DisplaySystem;

public class MyUltraSonic implements UltraSonicInterface{
	public UltrasonicSensor sense;
	
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
		if(sense.getDistance() < 70){
			inRange = true;
		}
		return inRange;
	}
}
