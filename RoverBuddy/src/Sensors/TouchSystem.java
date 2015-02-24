package Sensors;

import Interfaces.TouchInterface;
import SensorWrapperInterface.TouchSensorInterface;

public class TouchSystem implements TouchInterface{
	private TouchSensorInterface touchSensor1;
	private TouchSensorInterface touchSensor2;
	public TouchSystem(TouchSensorInterface t1, TouchSensorInterface t2){
		touchSensor1 = t1;
		touchSensor2 = t2;
	}
	public boolean DetectTouch(){
		return touchSensor1.isTouched() || touchSensor2.isTouched();
	}
}
