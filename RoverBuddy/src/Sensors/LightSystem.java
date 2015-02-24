package Sensors;

import Interfaces.LightInterface;
import SensorWrappers.MyLight;

public class LightSystem extends Thread implements LightInterface{
	private MyLight lightSensor;
	public LightSystem(MyLight light){
		lightSensor = light;
	}
	public boolean InBounds(){
		return lightSensor.readValue() > 50;
	}
}
