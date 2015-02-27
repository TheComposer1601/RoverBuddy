package TestCases;

import SensorWrapperInterface.UltraSonicInterface;

public class UltraSonicFalseTest implements UltraSonicInterface{

	@Override
	public boolean GetDistance() {
		return false;
	}

	@Override
	public void ping() {
	}
}
