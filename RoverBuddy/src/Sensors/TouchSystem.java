package Sensors;

import Interfaces.TouchInterface;
import SensorWrappers.MyTouch;

public class TouchSystem implements TouchInterface{
	private MyTouch touchSensor1;
	private MyTouch touchSensor2;
	public boolean DetectTouch(){
		return touchSensor1.isTouched() || touchSensor2.isTouched();
	}
}
