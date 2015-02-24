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
	
	public void startLooking(){
		sense.continuous();
	}
	
	@Override
	public boolean GetDistance() {
		boolean inRange = false;
		int distance = sense.getDistance();
		if(sense.getDistance() < 70){
			inRange = true;
		}
		return inRange;
	}

}
