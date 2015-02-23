package SensorWrapperInterface;

import SensorWrappers.MySensorPort;

public abstract class UltraSonicInterface {
	
	public UltraSonicInterface(int port) {}
	
	public abstract boolean GetDistance();

}
