package SensorWrappers;

import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import SensorWrapperInterface.UltraSonicInterface;

public class MyUltraSonic extends UltraSonicInterface{
	public UltrasonicSensor sense;
	
	public MyUltraSonic(SensorPort port) {
		sense = new UltrasonicSensor(port);
	}
	
	private void ping(){
		sense.ping();
	}
	
	@Override
	public boolean GetDistance() {
		boolean inRange = false;
		ping();
		if(sense.getDistance() < 70){
			inRange = true;
		}
		return inRange;
	}

}
