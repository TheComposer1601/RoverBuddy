package Sensors;

import Interfaces.LightInterface;
import SensorWrapperInterface.LightSensorInterface;

public class LightSystem extends Thread implements LightInterface{
	private static final int BLACK_LINE = 97;
	private LightSensorInterface lightSensor;
	public LightSystem(LightSensorInterface light){
		lightSensor = light;
		
	}
	public boolean InBounds(){
		int value = lightSensor.readValue();
		return value > BLACK_LINE;
	}
}
