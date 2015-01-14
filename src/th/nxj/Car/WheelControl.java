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
	
	/**�@�z�C�[����]���x��200�ɐݒ肷��@*/
	public WheelControl()
	{
		speed = 200; // 50
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
	
	public static int getSpeed()
	{
		return speed;
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
	 * �ɂ₩�ɍ�����
	 */
	public void TurnLeft2()
	{
		LWheel.setSpeed( 50 );
		RWheel.setSpeed( 250 );//200
		RWheel.forward();
		LWheel.forward();
	}
	
	/**
	 * �ɂ₩�ɉE����
	 */
	public void TurnRight2()
	{
		LWheel.setSpeed( 250 );//200
		RWheel.setSpeed( 50 );
		LWheel.forward();
		RWheel.forward();
	}
	
	/**
	 * ���̏�ō�����(�}�V���̒��S���͂���Ȃ�)
	 */
	public void RollLeft()
	{
		LWheel.backward();
		RWheel.forward();
	}
	
	/**
	 * ���̏�ŉE����(�}�V���̒��S���͂���Ȃ�)
	 */
	public void RollRight()
	{
		LWheel.forward();
		RWheel.backward();
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
	 * ��~
	 */
	public void stop()
	{
		LWheel.stop( true );
		RWheel.stop( true );
	}
	
}