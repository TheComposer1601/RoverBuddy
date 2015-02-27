package CanRemovalTestCases;

import Interfaces.TouchInterface;

public class TouchTestClass implements TouchInterface{
	boolean touch = false;
	@Override
	public boolean DetectTouch() {
		return touch;
	}
	public void setTouch(boolean touch){
		this.touch = touch;
	}

}
