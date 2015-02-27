package TestCases;

import SensorWrapperInterface.UltraSonicInterface;

public class UltraSonicTrueTest implements UltraSonicInterface{

	@Override
	public boolean GetDistance() {
		return true;
	}

	@Override
	public void ping() {
	}
}
