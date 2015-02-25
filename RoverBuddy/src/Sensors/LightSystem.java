package Sensors;

import Interfaces.LightInterface;
import SensorWrapperInterface.LightSensorInterface;

public class LightSystem extends Thread implements LightInterface{
	private LightSensorInterface lightSensor;
	public LightSystem(LightSensorInterface light){
		lightSensor = light;
		
	}
	public boolean InBounds(){
		int value = lightSensor.readValue();
		System.out.println(value);
		return value > 105;
	}
}
