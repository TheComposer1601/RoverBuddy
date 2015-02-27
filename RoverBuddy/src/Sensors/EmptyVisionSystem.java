package Sensors;

import Interfaces.VisionInterface;

public class EmptyVisionSystem implements VisionInterface{
	@Override
	public boolean DetectCan() {
		return false;
	}
}
