package th.nxj.Car;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class Car
{
	
	static DriveMode dmode = new DriveMode();
	
	public static void main( String args[] )
	{
		
		int linestate = 0;
		
		LCD.drawString( "Car system standby..." , 0 , 0 );
		Button.waitForAnyPress();
		LCD.drawString( "GO" , 0 , 3 );
		for(;;)
		{
			if( linestate == 0 ) 
				linestate = dmode.InLineDrive();
			else if( linestate == 1 )
				linestate = dmode.SearchLine();
			else if( linestate == 2 )
				linestate = dmode.OutLineDrive();
		}
		
	}
	
}