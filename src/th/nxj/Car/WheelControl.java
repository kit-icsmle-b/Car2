package th.nxj.Car;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public class WheelControl
{
	
	private static NXTRegulatedMotor LWheel = Motor.C; //Left
	private static NXTRegulatedMotor RWheel = Motor.A; //Right
	private static int speed;
	
	public WheelControl()
	{
		speed = 50;
		LWheel.setSpeed( speed );
		RWheel.setSpeed( speed );
	}
	
	public WheelControl( int speed )
	{
		this.speed = speed;
		LWheel.setSpeed( speed );
		RWheel.setSpeed( speed );
	}
	
	public void setSpeed( int speed )
	{
		this.speed = speed;
		LWheel.setSpeed( speed );
		RWheel.setSpeed( speed );
	}
	
	public void TurnLeft()
	{
		RWheel.forward();
		LWheel.stop(true);
	}
	
	public void TurnRight()
	{
		LWheel.forward();
		RWheel.stop(true);
	}
	
	public void forward()
	{
		LWheel.forward();
		RWheel.forward();
	}
	
	public void TurnRight2()
	{
		LWheel.setSpeed( 50 );
		RWheel.setSpeed( 20 );
		LWheel.forward();
		RWheel.forward();
	}
	
	public void stop()
	{
		LWheel.stop( true );
		RWheel.stop( true );
	}
	
}