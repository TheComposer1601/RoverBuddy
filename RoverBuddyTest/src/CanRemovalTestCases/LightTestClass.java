package CanRemovalTestCases;

import Interfaces.LightInterface;

public class LightTestClass implements LightInterface{
	boolean InBounds = true;
	@Override
	public boolean InBounds() {
		return InBounds;
	}
	public void setInBounds(boolean in){
		InBounds = in;
	}
}
