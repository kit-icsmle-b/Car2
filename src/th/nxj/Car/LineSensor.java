package th.nxj.Car;

import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;
import lejos.util.Delay;


/*-------------------------------------------------------------------/
/	NXJ Mobius Line Tracer Program	 (C)BONN, 2014
/--------------------------------------------------------------------/
/
/ Copyright (C) 2014 BONN.
/ All Rights Reserved.
/
/-------------------------------------------------------------------*/


public class LineSensor extends Thread
{
	
	public static final String[] colorNames = {"Red", "Green", "Blue", "Yellow", "Magenta", "Orange",
            "White", "Black", "Pink", "Gray", "Light gray", "Dark Gray", "Cyan"	};
	public static final int COLOR_ID_BLACK = 7;
	public static final int COLOR_ID_WHITE = 6;
	private final int DELAY_VALUE = 200;
	
	//public static int Red , Green , Blue;
	
	private static int state = 0;
	private static ColorHTSensor CSensor = new ColorHTSensor( SensorPort.S1 );
	
	public LineSensor()
	{
		start();
	}
	
	public void run()
	{
		for(;;)
		{
			state = CSensor.getColorID();
			LCD.drawInt( state , 0, 0, 4);
			Delay.msDelay( DELAY_VALUE );
		}
	}
	
	public int getState()
	{
		return state;
	}
	
}