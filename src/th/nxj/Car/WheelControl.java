package th.nxj.Car;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;

/*-------------------------------------------------------------------/
/	NXJ Mobius Line Tracer Program	 (C)BONN, 2014
/--------------------------------------------------------------------/
/
/ Copyright (C) 2014 BONN.
/ All Rights Reserved.
/
/-------------------------------------------------------------------*/

/**
 * �z�C�[���̓���̊Ǘ��E������s���N���X
 * @author BONN
 *
 */
public class WheelControl
{
	private static NXTRegulatedMotor LWheel = Motor.C; // Left
	private static NXTRegulatedMotor RWheel = Motor.A; // Right
	private static int speed;
	
	/**�@�z�C�[����]���x��50�ɐݒ肷��@*/
	public WheelControl()
	{
		speed = 50;
		LWheel.setSpeed( speed );
		RWheel.setSpeed( speed );
	}
	
	/**
	 * �z�C�[���̉�]���x��speed�̒l�ɐݒ肷��
	 * @param speed �z�C�[���̉�]���x
	 */
	public WheelControl( int speed )
	{
		this.speed = speed;
		LWheel.setSpeed( speed );
		RWheel.setSpeed( speed );
	}
	
	/**
	 * �z�C�[���̉�]���x��ύX����
	 * @param speed	�z�C�[���̉�]���x
	 */
	public void setSpeed( int speed )
	{
		this.speed = speed;
		LWheel.setSpeed( speed );
		RWheel.setSpeed( speed );
	}
	
	/**
	 * ���ɐ���
	 */
	public void TurnLeft()
	{
		RWheel.forward();
		LWheel.stop(true);
	}
	
	/**
	 * �E�ɐ���
	 */
	public void TurnRight()
	{
		LWheel.forward();
		RWheel.stop(true);
	}
	
	/**
	 * �O�i
	 */
	public void forward()
	{
		LWheel.forward();
		RWheel.forward();
	}
	
	/**
	 * �ɂ₩�ɉE����
	 */
	public void TurnRight2()
	{
		LWheel.setSpeed( 50 );
		RWheel.setSpeed( 20 );
		LWheel.forward();
		RWheel.forward();
	}
	
	/**
	 * ��~
	 */
	public void stop()
	{
		LWheel.stop( true );
		RWheel.stop( true );
	}
	
}