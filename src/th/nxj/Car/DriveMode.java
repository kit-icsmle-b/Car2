package th.nxj.Car;

import lejos.util.Delay;


public class DriveMode
{
	
	public static final int COLOR_ID_BLACK = 7;
	public static final int COLOR_ID_WHITE = 6;
	public static final int COLOR_ID_GREEN = 1;
	
	private static int state;
	private static int line_lost_time;
	WheelControl wheel = new WheelControl();
	LineSensor sensor = new LineSensor();
	
	public DriveMode()
	{
		state = 0;
		line_lost_time = 0;
	}
	
	public int InLineDrive()
	{
		int sstate = 0;
		
		switch( sensor.getState() )
		{
		case COLOR_ID_BLACK:	// Black
			wheel.TurnLeft();
			line_lost_time = 0;
			break;
		case COLOR_ID_WHITE:	// White
			wheel.TurnRight();
			line_lost_time++;
			break;
		case COLOR_ID_GREEN:
			DriveOnTheGreenInside();
			sstate = 2;
			break;
		/*default:
			wheel.TurnLeft();
			line_lost_time++;
			break;*/
		}
		
		if( line_lost_time > 5000 )
			sstate = 1;
		
		return sstate;
	}
	
	
	private void DriveOnTheGreenInside()
	{
		for(;;)
		{
			switch( sensor.getState() )
			{
			case COLOR_ID_BLACK:	// Black
				return;
			case COLOR_ID_WHITE:	// White
				wheel.TurnLeft();
				//line_lost_time++;
				break;
			case COLOR_ID_GREEN:
				wheel.TurnRight();
				break;
			/*default:
				wheel.TurnLeft();
				line_lost_time++;
				break;*/
			}
		}
	}
	
	
	private void DriveOnTheGreenOutside()
	{
		for(;;)
		{
			switch( sensor.getState() )
			{
			case COLOR_ID_BLACK:	// Black
				return;
			case COLOR_ID_WHITE:	// White
				wheel.TurnRight();
				//line_lost_time++;
				break;
			case COLOR_ID_GREEN:
				wheel.TurnLeft();
				break;
			/*default:
				wheel.TurnLeft();
				line_lost_time++;
				break;*/
			}
		}
	}
	
	
	public int OutLineDrive()
	{
		int sstate = 2;
		
		switch( sensor.getState() )
		{
		case COLOR_ID_BLACK:	// Black
			wheel.TurnRight();
			line_lost_time = 0;
			break;
		case COLOR_ID_WHITE:	// White
			wheel.TurnLeft();
			line_lost_time++;
			break;
		case COLOR_ID_GREEN:
			DriveOnTheGreenOutside();
			sstate = 0;
			break;
		/*default:
			wheel.TurnLeft();
			line_lost_time++;
			break;*/
		}
		
		if( line_lost_time > 5000 )
			sstate = 1;
		
		return sstate;
	}
	
	
	public int SearchLine()
	{
		int sstate = 0;
		//int count = 0;
		if( sensor.getState() != COLOR_ID_BLACK )
		{
			wheel.TurnLeft();
			Delay.msDelay( 200 );
			wheel.stop();
			Delay.msDelay( 500 );
			sstate = 1;
		}
		else
			sstate = 0;
		
		return sstate;
	}
	
}