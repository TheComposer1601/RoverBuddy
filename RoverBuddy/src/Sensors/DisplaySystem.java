package Sensors;

import Interfaces.DisplayInterface;

public class DisplaySystem implements DisplayInterface{
	public void Display(String message){
		System.out.println(message);
	}
}
